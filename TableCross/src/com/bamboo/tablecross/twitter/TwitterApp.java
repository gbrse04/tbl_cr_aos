package com.bamboo.tablecross.twitter;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.bamboo.tablecross.util.Logger;

/**
 * Twitter main class.
 * 
 * 
 */
public class TwitterApp {
	private final String TAG = "TwitterApp";

	private Twitter mTwitter;
	private TwitterSession mSession;
	private AccessToken mAccessToken;
	private CommonsHttpOAuthConsumer mHttpOauthConsumer;
	private OAuthProvider mHttpOauthprovider;

	private ProgressDialog mProgressDlg;
	private TwDialogListener mListener;
	private int flag = 0;
	private Context context;
	private String contentShare;

	public final TwDialogListener mTwLoginDialogListener = new TwDialogListener() {
		@Override
		public void onComplete(String value) {
			String userName = getUsername();
			String loginUserId = getUserId();
			Logger.e("", "userName: " + userName);
			Logger.e("", "loginUserId: " + loginUserId);
			updateStatus(contentShare);
		}

		@Override
		public void onError(String value) {
			Logger.d("Error Info ", value);
		}

		@Override
		public void onCancel() {
			Logger.d("Twitter-authorize", "Login canceled");
			mListener.onCancel();
		}
	};

	public TwitterApp(Context context) {
		this.context = context;
		mTwitter = new TwitterFactory().getInstance();
		mSession = new TwitterSession(context);
		mProgressDlg = new ProgressDialog(context);

		mProgressDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

		initOauthConsumer();

		mHttpOauthprovider = new DefaultOAuthProvider(TwitterConstant.REQUEST_TOKEN_URL,
				TwitterConstant.ACCESS_TOKEN_URL, TwitterConstant.AUTHORIZE_URL);

		mAccessToken = mSession.getAccessToken();

		setListener(mTwLoginDialogListener);
		configureToken();
	}

	public void setContentShare(String contentShare) {
		this.contentShare = contentShare;
	}

	public void initOauthConsumer() {
		mHttpOauthConsumer = new CommonsHttpOAuthConsumer(TwitterConstant.CONSUMER_KEY, TwitterConstant.CONSUMER_SECRET);
	}

	public void setListener(TwDialogListener listener) {
		mListener = listener;
	}

	public void configureToken() {
		if (mAccessToken != null) {
			mAccessToken.getUserId();
			if (flag == 0) {
				mTwitter.setOAuthConsumer(TwitterConstant.CONSUMER_KEY, TwitterConstant.CONSUMER_SECRET);
				mTwitter.setOAuthAccessToken(mAccessToken);
				flag++;
			} else {
				mTwitter = new TwitterFactory().getInstance();
				mTwitter.setOAuthConsumer(TwitterConstant.CONSUMER_KEY, TwitterConstant.CONSUMER_SECRET);
				mTwitter.setOAuthAccessToken(mAccessToken);
			}
		}
	}

	public boolean hasAccessToken() {
		return (mAccessToken == null) ? false : true;
	}

	public void resetAccessToken() {
		mSession.resetAccessToken();
		mAccessToken = null;
		mHttpOauthConsumer = new CommonsHttpOAuthConsumer(TwitterConstant.CONSUMER_KEY, TwitterConstant.CONSUMER_SECRET);
		mHttpOauthprovider = new DefaultOAuthProvider(TwitterConstant.REQUEST_TOKEN_URL,
				TwitterConstant.ACCESS_TOKEN_URL, TwitterConstant.AUTHORIZE_URL);
	}

	public void login() {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		if (hasAccessToken()) {
			resetAccessToken();
			authorize();
		} else {
			authorize();
		}
	}

	public void logout() {
		resetAccessToken();
	}

	public String getUsername() {
		return mSession.getUsername();
	}

	public String getUserId() {
		return mSession.getUserId();
	}

	public void updateStatus(String status) {
		try {
			mTwitter.updateStatus(status);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public void updateStatus(String status, String imagePath) {
		try {
			mTwitter.updateStatus(new StatusUpdate(status).media(new File(imagePath)));
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public void updateStatus(String status, File image) {
		try {
			mTwitter.updateStatus(new StatusUpdate(status).media(image));
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public void authorize() {
		mProgressDlg.setMessage("Initializing...");
		mProgressDlg.show();

		new Thread() {
			@Override
			public void run() {
				String authUrl = "";
				int what = 1;

				try {
					authUrl = mHttpOauthprovider.retrieveRequestToken(mHttpOauthConsumer, TwitterConstant.CALLBACK_URL);

					what = 0;

					Logger.d("Request token url ", "" + authUrl);
				} catch (Exception e) {
					Logger.d(TAG, "Failed to get request token");
					e.printStackTrace();
				}
				mHandler.sendMessage(mHandler.obtainMessage(what, 1, 0, authUrl));
			}
		}.start();
	}

	public void processToken(String callbackUrl) {
		mProgressDlg.setMessage("Finalizing...");
		mProgressDlg.show();

		final String verifier = getVerifier(callbackUrl);

		new Thread() {
			@Override
			public void run() {
				int what = 1;

				try {
					mHttpOauthprovider.retrieveAccessToken(mHttpOauthConsumer, verifier);
					String token = mHttpOauthConsumer.getToken();
					String tkSecret = mHttpOauthConsumer.getTokenSecret();
					mAccessToken = new AccessToken(token, tkSecret);
					configureToken();
					User user = mTwitter.verifyCredentials();
					mSession.storeAccessToken(mAccessToken, user.getName(), "" + user.getId());
					Logger.d(TAG, "store ok, name: " + user.getName());
					Logger.d(TAG, "store ok, token: " + mAccessToken.getToken());
					Logger.d(TAG, "store ok, token secret: " + mAccessToken.getTokenSecret());

					what = 0;
				} catch (Exception e) {
					Logger.d(TAG, "Error getting access token");
					// mainActivity.toggleButtonShareTwitter.setChecked(false);
					e.printStackTrace();
				}
				mHandler.sendMessage(mHandler.obtainMessage(what, 2, 0));
			}
		}.start();
	}

	public String getVerifier(String callbackUrl) {
		String verifier = "";
		try {
			callbackUrl = callbackUrl.replace("twitterapp", "http");

			URL url = new URL(callbackUrl);
			String query = url.getQuery();

			String array[] = query.split("&");

			for (String parameter : array) {
				String v[] = parameter.split("=");
				String s1 = decode(v[0]);
				String s2 = oauth.signpost.OAuth.OAUTH_VERIFIER;
				if (s1.equalsIgnoreCase(s2)) {
					verifier = decode(v[1]);
					break;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return verifier;
	}

	private static String decode(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	private void showLoginDialog(String url) {
		final TwDialogListener listener = new TwDialogListener() {
			@Override
			public void onComplete(String value) {
				processToken(value);
			}

			@Override
			public void onError(String value) {
				Logger.d("Error", "=_=");
				// mainActivity.toggleButtonShareTwitter.setChecked(false);
				mListener.onError("Failed opening authorization page");
			}

			@Override
			public void onCancel() {
				Logger.d("Twitter-authorize", "Login canceled");
				mListener.onCancel();
				// mainActivity.toggleButtonShareTwitter.setChecked(false);
			}
		};

		new TwitterDialog(context, url, listener).show();
	}

	@SuppressLint("HandlerLeak")
	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgressDlg.dismiss();

			if (msg.what == 1) {
				if (msg.arg1 == 1) {
					mListener.onError("Error getting request token");
				} else {
					mListener.onError("Error getting access token");
				}
			} else {
				if (msg.arg1 == 1) {
					showLoginDialog((String) msg.obj);
				} else {
					mListener.onComplete("");
				}
			}
		}
	};

	public interface TwDialogListener {

		public void onComplete(String value);

		public void onError(String value);

		public void onCancel();
	}
}