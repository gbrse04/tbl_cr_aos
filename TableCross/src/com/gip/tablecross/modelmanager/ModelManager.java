package com.gip.tablecross.modelmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gip.tablecross.PacketUtility;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.common.WebServiceConfig;
import com.gip.tablecross.network.AsyncHttpGet;
import com.gip.tablecross.network.AsyncHttpPost;
import com.gip.tablecross.network.AsyncHttpResponseProcess;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.util.Logger;

/**
 * 
 * @Description : Class to manager all model object
 * 
 */
public class ModelManager {
	private final String TAG = "ModelManager";
	private RequestQueue mVolleyQueue;
	private Activity context;

	public ModelManager(Activity context) {
		// If you need to directly manipulate cookies later on, hold onto this
		// client object as it gives you access to the Cookie Store
		DefaultHttpClient httpclient = new DefaultHttpClient();

		CookieStore cookieStore = new BasicCookieStore();
		httpclient.setCookieStore(cookieStore);

		HttpStack httpStack = new HttpClientStack(httpclient);
		mVolleyQueue = Volley.newRequestQueue(context, httpStack);
		this.context = context;
	}

	private void showResult(String url, List<NameValuePair> param, String response) {
		Logger.e(TAG, "-------------**start**-------------");
		Logger.d(TAG, "url: " + url);
		Logger.w(TAG, "param: " + param);
		Logger.i(TAG, "response: " + response);
		Logger.e(TAG, "--------------**end**--------------");
	}

	public void getInformationUser(String accessToken, final ModelManagerListener listener) {
		final String url = WebServiceConfig.URL_GRAPH_FACEBOOK + "/me";
		final List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("access_token", accessToken));
		AsyncHttpGet get = new AsyncHttpGet(context, new AsyncHttpResponseProcess() {
			@Override
			public void processIfResponseSuccess(String response) {
				Logger.e("", "response: " + response);
				if (response == null) {
					listener.onError("");
				} else {
					listener.onSuccess(ParserUtility.parseUserFacebook(response), null);
				}
			}
		}, parameters, false);
		get.execute(url);
	}

	public void shareOnFacebook(String accessToken, String message, Restaurant restaurant,
			final ModelManagerListener listener) {
		final String url = WebServiceConfig.URL_GRAPH_FACEBOOK + "/me/feed";
		final List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("access_token", accessToken));
		parameters.add(new BasicNameValuePair("message", message));
		// parameters.add(new BasicNameValuePair("name",
		// restaurant.getRestaurantName()));
		// parameters.add(new BasicNameValuePair("description",
		// restaurant.getDescription()));
		// parameters.add(new BasicNameValuePair("picture",
		// restaurant.getImageUrl()));
		// parameters.add(new BasicNameValuePair("link",
		// restaurant.getWebsite()));
		AsyncHttpPost post = new AsyncHttpPost(context, new AsyncHttpResponseProcess() {
			@Override
			public void processIfResponseSuccess(String response) {
				showResult(url, parameters, response);
				if (response == null) {
					listener.onError("");
				} else {
					int code = 0;
					try {
						JSONObject object = new JSONObject(response);
						code = object.getJSONObject("error").getInt("code");
					} catch (JSONException e) {
						e.printStackTrace();
					}

					if (code > 0) {
						if (code == 506) {
							listener.onSuccess(R.string.shareErrorDuplicateError, null);
						} else if (code == 341) {
							listener.onSuccess(R.string.shareErrorPostLimit, null);
						} else {
							listener.onSuccess(R.string.shareError, null);
						}
					} else {
						listener.onSuccess(R.string.shareSuccess, null);
					}
				}
			}
		}, parameters, false);
		post.execute(url);
	}

	public void register(String email, String password, String phone, String refUserId, String areaId,
			final ModelManagerListener listener) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("password", password);
		params.put("msisdn", phone);
		params.put("deviceId", PacketUtility.getImei(context));
		params.put("areaId", areaId);
		String getUrl = buildGetParams(WebServiceConfig.URL_REGISTER, params);
		Logger.d(TAG, "Get url : " + getUrl);
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						listener.onSuccess(null, ParserUtility.parserSimpleResponse(response));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error.getLocalizedMessage());
					}
				});

		// Set a retry policy in case of SocketTimeout & ConnectionTimeout
		// Exceptions. Volley does retry for you if you have specified the
		// policy.
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("INIT_TAG");
		mVolleyQueue.add(jsonObjRequest);
	}

	public void login(String email, String password, int loginType, String areaId, final ModelManagerListener listener) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("password", password);
		params.put("loginType", String.valueOf(loginType));
		params.put("areaId", areaId);
		String getUrl = buildGetParams(WebServiceConfig.URL_LOGIN, params);
		Logger.d(TAG, "Get url : " + getUrl);
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Logger.e("", "login response: " + response);
						listener.onSuccess(ParserUtility.parserUser(response),
								ParserUtility.parserSimpleResponse(response));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error.getLocalizedMessage());
					}
				});

		// Set a retry policy in case of SocketTimeout & ConnectionTimeout
		// Exceptions. Volley does retry for you if you have specified the
		// policy.
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("INIT_TAG");
		mVolleyQueue.add(jsonObjRequest);
	}

	public void getUserInfo(final ModelManagerListener listener) {
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,
				WebServiceConfig.URL_GET_USER_INFO, null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Logger.e("", "login response: " + response);
						listener.onSuccess(ParserUtility.parserUser(response),
								ParserUtility.parserSimpleResponse(response));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error.getLocalizedMessage());
					}
				});

		// Set a retry policy in case of SocketTimeout & ConnectionTimeout
		// Exceptions. Volley does retry for you if you have specified the
		// policy.
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("INIT_TAG");
		mVolleyQueue.add(jsonObjRequest);
	}

	public void logout(final ModelManagerListener listener) {
		HashMap<String, String> params = new HashMap<String, String>();
		String getUrl = buildGetParams(WebServiceConfig.URL_LOGOUT, params);
		Logger.d(TAG, "Get url : " + getUrl);
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						listener.onSuccess(null, ParserUtility.parserSimpleResponse(response));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error.getLocalizedMessage());
					}
				});

		// Set a retry policy in case of SocketTimeout & ConnectionTimeout
		// Exceptions. Volley does retry for you if you have specified the
		// policy.
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("INIT_TAG");
		mVolleyQueue.add(jsonObjRequest);
	}

	public void changePassword(String oldPassword, String newPassword, final ModelManagerListener listener) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("oldPassword", oldPassword);
		params.put("newPassword", newPassword);
		String getUrl = buildGetParams(WebServiceConfig.URL_CHANGE_PASSWORD, params);
		Logger.d(TAG, "Get url : " + getUrl);
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						listener.onSuccess(null, ParserUtility.parserSimpleResponse(response));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error.getLocalizedMessage());
					}
				});

		// Set a retry policy in case of SocketTimeout & ConnectionTimeout
		// Exceptions. Volley does retry for you if you have specified the
		// policy.
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("INIT_TAG");
		mVolleyQueue.add(jsonObjRequest);
	}

	public void updateUser(String email, String mobile, String birthday, final ModelManagerListener listener) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("mobile", mobile);
		params.put("birthday", birthday);
		String getUrl = buildGetParams(WebServiceConfig.URL_UPDATE_USER, params);
		Logger.d(TAG, "Get url : " + getUrl);
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						listener.onSuccess(null, ParserUtility.parserSimpleResponse(response));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error.getLocalizedMessage());
					}
				});

		// Set a retry policy in case of SocketTimeout & ConnectionTimeout
		// Exceptions. Volley does retry for you if you have specified the
		// policy.
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("INIT_TAG");
		mVolleyQueue.add(jsonObjRequest);
	}

	public void getRestaurantInfo(int restaurantId, final ModelManagerListener listener) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("restaurantId", String.valueOf(restaurantId));
		String getUrl = buildGetParams(WebServiceConfig.URL_GET_RESTAURANT_INFO, params);
		Logger.d(TAG, "Get url : " + getUrl);
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Logger.d(TAG, "getRestaurantInfo response: " + response);
						listener.onSuccess(ParserUtility.parserRestaurantWithKey(response),
								ParserUtility.parserSimpleResponse(response));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error.getLocalizedMessage());
					}
				});

		// Set a retry policy in case of SocketTimeout & ConnectionTimeout
		// Exceptions. Volley does retry for you if you have specified the
		// policy.
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("INIT_TAG");
		mVolleyQueue.add(jsonObjRequest);
	}

	public void getAreas(final ModelManagerListener listener) {
		HashMap<String, String> params = new HashMap<String, String>();
		String getUrl = buildGetParams(WebServiceConfig.URL_GET_AREAS, params);
		Logger.d(TAG, "Get url : " + getUrl);
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, WebServiceConfig.URL_GET_AREAS,
				null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						listener.onSuccess(ParserUtility.parserListAreas(response),
								ParserUtility.parserSimpleResponse(response));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error.getLocalizedMessage());
					}
				});

		// Set a retry policy in case of SocketTimeout & ConnectionTimeout
		// Exceptions. Volley does retry for you if you have specified the
		// policy.
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("INIT_TAG");
		mVolleyQueue.add(jsonObjRequest);
	}

	public void searchRestaurant(String searchType, String searchKey, float distance, int total,
			final ModelManagerListener listener) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("searchType", searchType);
		params.put("searchKey", searchKey);
		params.put("latitude", String.valueOf(GlobalValue.locationInfo.lastLat));
		params.put("longitude", String.valueOf(GlobalValue.locationInfo.lastLong));
		params.put("distance", String.valueOf(distance));
		params.put("total", String.valueOf(total));

		String getUrl = buildGetParams(WebServiceConfig.URL_SEARCH_RESTAURANT, params);
		Logger.d(TAG, "Get url : " + getUrl);
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Logger.e("", "response search: " + response);
						listener.onSuccess(ParserUtility.parserListRestaurants(response),
								ParserUtility.parserSimpleResponse(response));

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error.getLocalizedMessage());
					}
				});

		// Set a retry policy in case of SocketTimeout & ConnectionTimeout
		// Exceptions. Volley does retry for you if you have specified the
		// policy.
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("INIT_TAG");
		mVolleyQueue.add(jsonObjRequest);
	}

	public void getNotify(int start, int total, final ModelManagerListener listener) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("start", String.valueOf(start));
		params.put("total", String.valueOf(total));

		String getUrl = buildGetParams(WebServiceConfig.URL_GET_NOTIFY, params);
		Logger.d(TAG, "Get url : " + getUrl);
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						listener.onSuccess(ParserUtility.parserListNotifications(response),
								ParserUtility.parserSimpleResponse(response));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error.getLocalizedMessage());
					}
				});

		// Set a retry policy in case of SocketTimeout & ConnectionTimeout
		// Exceptions. Volley does retry for you if you have specified the
		// policy.
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("INIT_TAG");
		mVolleyQueue.add(jsonObjRequest);
	}

	public void order(int restaurantId, int quantity, final ModelManagerListener listener) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("restaurantId", String.valueOf(restaurantId));
		params.put("quantity", String.valueOf(quantity));

		String getUrl = buildGetParams(WebServiceConfig.URL_ORDER, params);
		Logger.d(TAG, "Get url : " + getUrl);
		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						listener.onSuccess(null, ParserUtility.parserSimpleResponse(response));
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error.getLocalizedMessage());
					}
				});

		// Set a retry policy in case of SocketTimeout & ConnectionTimeout
		// Exceptions. Volley does retry for you if you have specified the
		// policy.
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("INIT_TAG");
		mVolleyQueue.add(jsonObjRequest);
	}

	private String buildGetParams(String baseUrl, HashMap<String, String> params) {
		StringBuffer strBuffer = new StringBuffer(baseUrl);
		strBuffer.append("?");
		for (Entry<String, String> item : params.entrySet()) {
			strBuffer.append(item.getKey()).append("=").append(item.getValue()).append("&");
		}
		return strBuffer.toString().substring(0, strBuffer.toString().length() - 1);
	}
}