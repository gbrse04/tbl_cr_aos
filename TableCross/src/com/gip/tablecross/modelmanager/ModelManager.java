package com.gip.tablecross.modelmanager;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gip.tablecross.PacketUtility;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.common.WebServiceConfig;
import com.gip.tablecross.util.Logger;

/**
 * 
 * @Description : Class to manager all model object
 * 
 */
public class ModelManager {
	private final String TAG = "ModelManager";
	private RequestQueue mVolleyQueue;
	private Context context;

	public ModelManager(Context context) {
		// If you need to directly manipulate cookies later on, hold onto this
		// client object as it gives you access to the Cookie Store
		DefaultHttpClient httpclient = new DefaultHttpClient();

		CookieStore cookieStore = new BasicCookieStore();
		httpclient.setCookieStore(cookieStore);

		HttpStack httpStack = new HttpClientStack(httpclient);
		mVolleyQueue = Volley.newRequestQueue(context, httpStack);
		this.context = context;
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
						if (error instanceof NetworkError) {
						} else if (error instanceof ServerError) {
						} else if (error instanceof AuthFailureError) {
						} else if (error instanceof ParseError) {
						} else if (error instanceof NoConnectionError) {
						} else if (error instanceof TimeoutError) {
						}
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
						if (error instanceof NetworkError) {
						} else if (error instanceof ServerError) {
						} else if (error instanceof AuthFailureError) {
						} else if (error instanceof ParseError) {
						} else if (error instanceof NoConnectionError) {
						} else if (error instanceof TimeoutError) {
						}
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
						if (error instanceof NetworkError) {
						} else if (error instanceof ServerError) {
						} else if (error instanceof AuthFailureError) {
						} else if (error instanceof ParseError) {
						} else if (error instanceof NoConnectionError) {
						} else if (error instanceof TimeoutError) {
						}
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
						if (error instanceof NetworkError) {
						} else if (error instanceof ServerError) {
						} else if (error instanceof AuthFailureError) {
						} else if (error instanceof ParseError) {
						} else if (error instanceof NoConnectionError) {
						} else if (error instanceof TimeoutError) {
						}
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
						if (error instanceof NetworkError) {
						} else if (error instanceof ServerError) {
						} else if (error instanceof AuthFailureError) {
						} else if (error instanceof ParseError) {
						} else if (error instanceof NoConnectionError) {
						} else if (error instanceof TimeoutError) {
						}
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
						if (error instanceof NetworkError) {
						} else if (error instanceof ServerError) {
						} else if (error instanceof AuthFailureError) {
						} else if (error instanceof ParseError) {
						} else if (error instanceof NoConnectionError) {
						} else if (error instanceof TimeoutError) {
						}
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
						if (error instanceof NetworkError) {
						} else if (error instanceof ServerError) {
						} else if (error instanceof AuthFailureError) {
						} else if (error instanceof ParseError) {
						} else if (error instanceof NoConnectionError) {
						} else if (error instanceof TimeoutError) {
						}
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

	// public void postStatusFacebook(final String message, final
	// ModelManagerListener listener) {
	// JsonObjectRequest jsonObjRequest = new
	// JsonObjectRequest(Request.Method.POST,
	// WebServiceConfig.URL_GRAPH_FACEBOOK + "/me/feed", null, new
	// Response.Listener<JSONObject>() {
	// @Override
	// public void onResponse(JSONObject response) {
	// Logger.e("", "response: " + response);
	// // listener.onSuccess(ParserUtility.parserListNotifications(response),
	// // ParserUtility.parserSimpleResponse(response));
	// }
	// }, new Response.ErrorListener() {
	// @Override
	// public void onErrorResponse(VolleyError error) {
	// if (error instanceof NetworkError) {
	// } else if (error instanceof ServerError) {
	// } else if (error instanceof AuthFailureError) {
	// } else if (error instanceof ParseError) {
	// } else if (error instanceof NoConnectionError) {
	// } else if (error instanceof TimeoutError) {
	// }
	// listener.onError(error.getLocalizedMessage());
	// }
	//
	// }) {
	// @Override
	// protected Map<String, String> getParams() throws AuthFailureError {
	// HashMap<String, String> params = new HashMap<String, String>();
	// params.put("access_token", GlobalValue.accessTokenFacebook);
	// params.put("message", encode(message));
	// return params;
	// }
	// };
	//
	// // Set a retry policy in case of SocketTimeout & ConnectionTimeout
	// // Exceptions. Volley does retry for you if you have specified the
	// // policy.
	// jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
	// DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
	// DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	// jsonObjRequest.setTag("INIT_TAG");
	// mVolleyQueue.add(jsonObjRequest);
	// }

	// private String encode(String value) {
	// try {
	// return URLEncoder.encode(value, "UTF-8");
	// } catch (UnsupportedEncodingException e) {
	// return "";
	// }
	// }

	// public void postStatusFacebook(String message, final ModelManagerListener
	// listener) {
	// final String url = WebServiceConfig.URL_GRAPH_FACEBOOK + "/me/feed";
	// final List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	// parameters.add(new BasicNameValuePair("access_token",
	// GlobalValue.accessTokenFacebook));
	// parameters.add(new BasicNameValuePair("message", message));
	// AsyncHttpPost post = new AsyncHttpPost(context, new
	// AsyncHttpResponseProcess() {
	// @Override
	// public void processIfResponseSuccess(String response) {
	// // showResult(url, parameters, response);
	// Logger.e("", "login fb result: " + response);
	// if (response == null) {
	// listener.onError("");
	// } else {
	// int code = 0;
	// try {
	// JSONObject object = new JSONObject(response);
	// // code = object.getJSONObject("error").getInt("code");
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// if (code > 0) {
	// if (code == 506) {
	// listener.onSuccess(context.getString(R.string.shareErrorDuplicateError),
	// null);
	// } else if (code == 341) {
	// listener.onSuccess(context.getString(R.string.shareErrorPostLimit),
	// null);
	// } else {
	// listener.onSuccess(context.getString(R.string.shareError), null);
	// }
	// } else {
	// listener.onSuccess(context.getString(R.string.shareSuccess), null);
	// }
	// }
	// }
	// }, parameters, false);
	// post.execute(url);
	// }

	private String buildGetParams(String baseUrl, HashMap<String, String> params) {
		StringBuffer strBuffer = new StringBuffer(baseUrl);
		strBuffer.append("?");
		for (Entry<String, String> item : params.entrySet()) {
			strBuffer.append(item.getKey()).append("=").append(item.getValue()).append("&");
		}
		return strBuffer.toString().substring(0, strBuffer.toString().length() - 1);
	}
}