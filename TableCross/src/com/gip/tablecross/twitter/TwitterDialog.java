package com.gip.tablecross.twitter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gip.tablecross.twitter.TwitterApp.TwDialogListener;
import com.gip.tablecross.util.Logger;

/**
 * Modified from FbDialog from Facebook SDK
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 */
public class TwitterDialog extends Dialog {

	static final float[] DIMENSIONS_LANDSCAPE = { 460, 260 };
	static final float[] DIMENSIONS_PORTRAIT = { 280, 420 };
	static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT);
	static final int MARGIN = 4;
	static final int PADDING = 2;
	LinearLayout webViewContainer;

	private String mUrl;
	private TwDialogListener mListener;
	private ProgressDialog mSpinner;
	private WebView mWebView;
	private ImageView mCrossImage;
	private FrameLayout mContent;

	private final String TAG = getClass().getSimpleName();

	public TwitterDialog(Context context, String url, TwDialogListener listener) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		mUrl = url;
		mListener = listener;
		webViewContainer = new LinearLayout(getContext());
		// webViewContainer.setOrientation(LinearLayout.VERTICAL);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSpinner = new ProgressDialog(getContext());
		mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// mContent = new LinearLayout(getContext());
		//
		// mContent.setOrientation(LinearLayout.VERTICAL);
		mContent = new FrameLayout(getContext());

		// setUpTitle();
		createCrossImage();
		int crossWidth = mCrossImage.getDrawable().getIntrinsicWidth();
		setUpWebView(crossWidth / 2);

		mContent.addView(mCrossImage, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addContentView(mContent, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setUpWebView(int margin) {
		mWebView = new WebView(getContext());
		mWebView.clearSslPreferences();
		mWebView.clearCache(true);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setWebViewClient(new TwitterWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(mUrl);
		mWebView.setLayoutParams(FILL);
		webViewContainer.setPadding(margin, margin, margin, margin);
		webViewContainer.addView(mWebView);
		mContent.addView(webViewContainer);
	}

	private void createCrossImage() {
		mCrossImage = new ImageView(getContext());
		// Dismiss the dialog when user click on the 'x'
		mCrossImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		Drawable crossDrawable = getContext().getResources().getDrawable(android.R.drawable.ic_delete);
		mCrossImage.setImageDrawable(crossDrawable);
		/*
		 * 'x' should not be visible while webview is loading make it visible
		 * only after webview has fully loaded
		 */
		mCrossImage.setVisibility(View.VISIBLE);
	}

	private class TwitterWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Logger.d(TAG, "Redirecting URL " + url);

			if (url.startsWith(TwitterConstant.CALLBACK_URL)) {
				mListener.onComplete(url);

				TwitterDialog.this.dismiss();

				return true;
			} else if (url.startsWith("authorize")) {
				return false;
			}

			return true;
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			Logger.d(TAG, "Page error: " + description);

			super.onReceivedError(view, errorCode, description, failingUrl);
			mListener.onError(description);
			TwitterDialog.this.dismiss();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Logger.d(TAG, "Loading URL: " + url);
			super.onPageStarted(view, url, favicon);
			mSpinner.show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			mSpinner.dismiss();
		}
	}
}