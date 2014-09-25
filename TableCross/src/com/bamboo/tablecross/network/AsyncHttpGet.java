package com.bamboo.tablecross.network;

import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

/**
 * AsyncHttpGet makes http get request based on AsyncTask
 * 
 * @author Lemon
 */
public class AsyncHttpGet extends AsyncHttpBase {
	private static final String TAG = "AsyncHttpGet";

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param process
	 * @param parameters
	 */
	public AsyncHttpGet(Context context, AsyncHttpResponseProcess process, List<NameValuePair> parameters) {
		super(context, process, parameters);
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param listener
	 * @param parameters
	 */
	public AsyncHttpGet(Context context, AsyncHttpResponseListener listener, List<NameValuePair> parameters,
			boolean isShowWaitingDialog) {
		super(context, listener, parameters, isShowWaitingDialog);
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param process
	 * @param isShowWaitingDialog
	 */
	public AsyncHttpGet(Context context, AsyncHttpResponseProcess process) {
		super(context, process);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fgsecure.meyclub.app.network.AsyncHttpBase#request(java.lang.String)
	 */
	@Override
	protected String request(String url) {
		try {
			this.url = url;
			HttpParams params = new BasicHttpParams();
			// Lemon commented 19/04/2012
			// if (parameters != null) {
			// Iterator<NameValuePair> it = parameters.iterator();
			// while (it.hasNext()) {
			// NameValuePair nv = it.next();
			// params.setParameter(nv.getName(), nv.getValue());
			// }
			// }

			// Bind param direct to URL

			String combinedParams = "";
			if (!parameters.isEmpty()) {
				combinedParams += "?";
				for (NameValuePair p : parameters) {
					String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
					if (combinedParams.length() > 1) {
						combinedParams += "&" + paramString;
					} else {
						combinedParams += paramString;
					}
				}
				Log.i(TAG, "CombineParams : " + combinedParams);
			}
			HttpConnectionParams.setConnectionTimeout(params, Config.NETWORK_TIME_OUT);
			HttpConnectionParams.setSoTimeout(params, Config.NETWORK_TIME_OUT);
			// Lemon commented 19/04/2012
			HttpClient httpclient = createHttpClient(url, params);
			// DefaultHttpClient httpclient = new DefaultHttpClient();
			// HttpGet httpget = new HttpGet(url + combinedParams);
			Log.e(TAG, "GET URL executed  : " + url + combinedParams);
			HttpGet httpget1 = new HttpGet(url + combinedParams);
			response = EntityUtils.toString(httpclient.execute(httpget1).getEntity(), HTTP.UTF_8);
			// Lemon added
			// httpclient.getConnectionManager().shutdown();
			statusCode = NETWORK_STATUS_OK;
		} catch (Exception e) {
			statusCode = NETWORK_STATUS_ERROR;
			e.printStackTrace();
		}
		return null;
	}
}
