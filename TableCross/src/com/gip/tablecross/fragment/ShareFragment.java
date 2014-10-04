package com.gip.tablecross.fragment;

import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.PacketUtility;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.common.WebServiceConfig;
import com.gip.tablecross.facebook.DialogError;
import com.gip.tablecross.facebook.Facebook;
import com.gip.tablecross.facebook.Facebook.DialogListener;
import com.gip.tablecross.facebook.FacebookConstant;
import com.gip.tablecross.facebook.FacebookError;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.util.Logger;
import com.gip.tablecross.util.StringUtil;

public class ShareFragment extends BaseFragment implements OnClickListener {
	private View btnShareFacebook, btnShareTwitter, btnShareLine, btnShareSms, btnShareEmail;
	private String contentShare;
	public Facebook facebook;
	private SocialAuthAdapter adapter;

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
			try {
				contentShare = getString(R.string.shareDownload1) + "\n" + getMainActivity().user.getShareLink() + "\n"
						+ getString(R.string.shareDownload2);
			} catch (Exception e) {
				contentShare = "";
			}
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
		showDialogShare(R.string.shareOnFacebook);
	}

	private void startShareOnFacebook() {
		if (StringUtil.isEmpty(getMainActivity().accessToken)) {
			if (facebook == null) {
				facebook = new Facebook(FacebookConstant.FACEBOOK_APPID, getActivity());
			}
			facebook.authorize(getActivity(), FacebookConstant.FACEBOOK_PERMISSION, new DialogListener() {
				@Override
				public void onFacebookError(FacebookError e) {
				}

				@Override
				public void onError(DialogError e) {
				}

				@Override
				public void onComplete(Bundle values) {
					getMainActivity().accessToken = values.getString("access_token");
					shareOnFacebook();
				}

				@Override
				public void onCancel() {
				}
			});

		} else {
			shareOnFacebook();
		}
	}

	public void shareOnFacebook() {
		getBaseActivity().showLoading();
		GlobalValue.modelManager.shareOnFacebook(getMainActivity().accessToken, contentShare,
				getMainActivity().currentRestaurant, new ModelManagerListener() {
					@Override
					public void onSuccess(Object object, SimpleResponse simpleResponse) {
						showToast((Integer) object);
						getBaseActivity().hideLoading();
					}

					@Override
					public void onError(String message) {
						getBaseActivity().hideLoading();
						showToast(message);
					}
				});
	}

	private void onClickShareTwitter() {
		showDialogShare(R.string.shareOnTwitter);
	}

	private void startShareTwitter() {
		if (adapter == null) {
			adapter = new SocialAuthAdapter(new ResponseListener());
		}
		adapter.authorize(getActivity(), Provider.TWITTER);
	}

	private void onClickShareLine() {
		showDialogShare(R.string.shareViaLineApp);
	}

	private void startShareLine() {
		if (PacketUtility.isPackageExisted(getActivity(), GlobalValue.LINE_PACKET)) {
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, contentShare);
			sendIntent.setType("text/plain");
			sendIntent.setPackage(GlobalValue.LINE_PACKET);
			startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
		} else {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebServiceConfig.URL_SHARE_LINE));
			startActivity(browserIntent);
		}
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

	@SuppressLint("InflateParams")
	private void showDialogShare(final int idShareOn) {
		View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_share, null);
		final TextView lblMessage = (TextView) dialogView.findViewById(R.id.lblMessage);
		final EditText txtContentShare = (EditText) dialogView.findViewById(R.id.txtContentShare);
		lblMessage.setText(idShareOn);
		txtContentShare.setText(contentShare);
		new AlertDialog.Builder(getActivity()).setView(dialogView)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (idShareOn == R.string.shareOnFacebook) {
							startShareOnFacebook();
						} else if (idShareOn == R.string.shareOnTwitter) {
							startShareTwitter();
						} else {
							startShareLine();
						}
					}
				}).setNegativeButton(R.string.cancel, null).create().show();
	}

	private final class ResponseListener implements org.brickred.socialauth.android.DialogListener {
		@Override
		public void onComplete(Bundle values) {
			Logger.d("ShareButton", "Authentication Successful");
			adapter.updateStatus(contentShare, new SocialAuthListener<Integer>() {
				@Override
				public void onError(SocialAuthError arg0) {
				}

				@Override
				public void onExecute(String provider, Integer t) {
					Integer status = t;
					if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204) {
						showToast("Message posted on " + provider);
					} else {
						showToast("Message not posted on ");
					}
				}
			}, false);
		}

		@Override
		public void onError(SocialAuthError error) {
			Logger.d("ShareButton", "Authentication Error: " + error.getMessage());
		}

		@Override
		public void onCancel() {
			Logger.d("ShareButton", "Authentication Cancelled");
		}

		@Override
		public void onBack() {
			Logger.d("Share-Button", "Dialog Closed by pressing Back Key");
		}
	}
}
