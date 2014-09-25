package com.bamboo.tablecross.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.bamboo.tablecross.BaseFragment;
import com.bamboo.tablecross.R;
import com.bamboo.tablecross.common.GlobalValue;
import com.bamboo.tablecross.modelmanager.ModelManagerListener;
import com.bamboo.tablecross.object.SimpleResponse;
import com.bamboo.tablecross.twitter.TwitterApp;

public class ShareFragment extends BaseFragment implements OnClickListener {
	private View btnShareFacebook, btnShareTwitter, btnShareGooglePlus, btnShareSms, btnShareEmail;
	private String contentShare = "Table Cross app https://play.google.com/store/apps/details?id=com.gip.tablecross";

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
		btnShareGooglePlus = view.findViewById(R.id.btnShareGooglePlus);
		btnShareSms = view.findViewById(R.id.btnShareSms);
		btnShareEmail = view.findViewById(R.id.btnShareEmail);// Shared
																// Preferences
	}

	private void initControl() {
		btnShareFacebook.setOnClickListener(this);
		btnShareTwitter.setOnClickListener(this);
		btnShareGooglePlus.setOnClickListener(this);
		btnShareSms.setOnClickListener(this);
		btnShareEmail.setOnClickListener(this);
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

		case R.id.btnShareGooglePlus:
			onClickShareGooglePlus();
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
		GlobalValue.modelManager.postMessage(GlobalValue.accessTokenFacebook, "test", new ModelManagerListener() {

			@Override
			public void onSuccess(Object object, SimpleResponse simpleResponse) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(String message) {
				// TODO Auto-generated method stub

			}
		});
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

	private void onClickShareGooglePlus() {
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
