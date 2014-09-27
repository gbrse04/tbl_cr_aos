package com.gip.tablecross.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.common.WebServiceConfig;
import com.gip.tablecross.facebook.AsyncFacebookRunner;
import com.gip.tablecross.facebook.DialogError;
import com.gip.tablecross.facebook.Facebook;
import com.gip.tablecross.facebook.Facebook.DialogListener;
import com.gip.tablecross.facebook.FacebookConstant;
import com.gip.tablecross.facebook.FacebookError;
import com.gip.tablecross.facebook.SessionStore;
import com.gip.tablecross.facebook.UtilityFacebook;
import com.gip.tablecross.twitter.TwitterApp;
import com.gip.tablecross.util.Logger;
import com.gip.tablecross.util.StringUtil;

public class ShareFragment extends BaseFragment implements OnClickListener, FacebookConstant {
	private View btnShareFacebook, btnShareTwitter, btnShareLine, btnShareSms, btnShareEmail;
	private String contentShare = "Table Cross app https://play.google.com/store/apps/details?id=com.gip.tablecross";
	private Facebook facebook;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_share, container, false);
		initUI(view);
		initControl();
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
		}
	}

	private void initUI(View view) {
		btnShareFacebook = view.findViewById(R.id.btnShareFacebook);
		btnShareTwitter = view.findViewById(R.id.btnShareTwitter);
		btnShareLine = view.findViewById(R.id.btnShareLine);
		btnShareSms = view.findViewById(R.id.btnShareSms);
		btnShareEmail = view.findViewById(R.id.btnShareEmail);
	}

	private void initControl() {
		btnShareFacebook.setOnClickListener(this);
		btnShareTwitter.setOnClickListener(this);
		btnShareLine.setOnClickListener(this);
		btnShareSms.setOnClickListener(this);
		btnShareEmail.setOnClickListener(this);
	}

	private void initSessionFacebook() {
		facebook = new Facebook(getString(R.string.facebook_app_id), getActivity());
		UtilityFacebook.mAsyncRunner = new AsyncFacebookRunner(facebook);
		SessionStore.restore(facebook, getActivity());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnShareFacebook:
			onClickShareFacebook();
			break;

		case R.id.btnShareTwitter:
			onClickShareTwitter();
			break;

		case R.id.btnShareLine:
			onClickShareLine();
			break;

		case R.id.btnShareSms:
			onClickShareSms();
			break;

		case R.id.btnShareEmail:
			onClickShareEmail();
			break;
		}
	}

	private void onClickShareFacebook() {
		Logger.e("", "at: " + GlobalValue.accessTokenFacebook);
		if (StringUtil.isEmpty(GlobalValue.accessTokenFacebook)) {
			initSessionFacebook();
			facebook.authorize(getActivity(), FACEBOOK_PERMISSION, new DialogListener() {

				@Override
				public void onFacebookError(FacebookError e) {
					Logger.e("", "onFacebookError: " );
				}

				@Override
				public void onError(DialogError e) {
					Logger.e("", "onError: " );
				}

				@Override
				public void onComplete(Bundle values) {
					Logger.e("", "onComplete: " );
					SessionStore.save(facebook, getActivity());
					GlobalValue.accessTokenFacebook = facebook.getAccessToken();
					Logger.e("", "token: " + GlobalValue.accessTokenFacebook);
					postStatusFacebook();
				}

				@Override
				public void onCancel() {
					Logger.e("", "onCancel: " );
				}
			});
		} else {
			postStatusFacebook();
		}
	}

	public void postStatusFacebook() {
		Request request = Request.newStatusUpdateRequest(Session.getActiveSession(), contentShare, null, null,
				new Request.Callback() {
					@Override
					public void onCompleted(Response response) {
						Logger.e("", "response: " + response);
						getBaseActivity().hideLoading();
						if (response.getError() == null) {
							showToast("Sharing success on your Facebook");
						} else {
							showToast(response.getError().getErrorMessage());
						}
					}
				});
		request.executeAsync();
	}

	private void onClickShareTwitter() {
		final TwitterApp twitterApp = new TwitterApp(getActivity());
		if (twitterApp.hasAccessToken()) {
			twitterApp.updateStatus(contentShare);
		} else {
			twitterApp.setContentShare(contentShare);
			twitterApp.login();
		}
	}

	private void onClickShareLine() {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebServiceConfig.URL_SHARE_LINE));
		startActivity(browserIntent);
	}

	private void onClickShareSms() {
		Uri uri = Uri.parse("smsto:");
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		it.putExtra("sms_body", contentShare);
		startActivity(it);
	}

	private void onClickShareEmail() {
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
		email.putExtra(Intent.EXTRA_TEXT, contentShare);
		// need this to prompts email client only
		email.setType("message/rfc822");
		startActivity(Intent.createChooser(email, "Choose an Email client:"));
	}
}
