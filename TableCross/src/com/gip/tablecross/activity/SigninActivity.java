package com.gip.tablecross.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.gip.tablecross.BaseActivity;
import com.gip.tablecross.PacketUtility;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.facebook.DialogError;
import com.gip.tablecross.facebook.Facebook;
import com.gip.tablecross.facebook.Facebook.DialogListener;
import com.gip.tablecross.facebook.FacebookConstant;
import com.gip.tablecross.facebook.FacebookError;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.object.User;
import com.gip.tablecross.util.StringUtil;
import com.gip.tablecross.widget.AutoBgButton;

public class SigninActivity extends BaseActivity implements OnClickListener {
	private final int ACCOUNT_REGISTER = 0;
	private final int ACCOUNT_FACEBOOK = 1;
	private final int ACCOUNT_NULL = 2;
	private AutoBgButton btnLogin, btnLoginFacebook;
	private EditText txtEmail, txtPassword;
	private View btnGoToSignUp, lblUseAppWithouLogin;

	private String accessToken = "";
	private Facebook facebook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		initUI();
		initControl();
	}

	private void initUI() {
		btnLogin = (AutoBgButton) findViewById(R.id.btnLogin);
		btnLoginFacebook = (AutoBgButton) findViewById(R.id.btnLoginFacebook);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		btnGoToSignUp = findViewById(R.id.btnGoToSignUp);
		lblUseAppWithouLogin = findViewById(R.id.lblUseAppWithouLogin);

		if (PacketUtility.getImei(this).equals("357189059656678")) {
			txtEmail.setText("thibt@vivas.vn");
			txtPassword.setText("123456");
		}
	}

	private void initControl() {
		btnLogin.setOnClickListener(this);
		btnLoginFacebook.setOnClickListener(this);
		btnGoToSignUp.setOnClickListener(this);
		lblUseAppWithouLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:
			onClickLogin();
			break;

		case R.id.btnLoginFacebook:
			onClickLoginFacebook();
			break;

		case R.id.btnGoToSignUp:
			onClickGoToSignUp();
			break;

		case R.id.lblUseAppWithouLogin:
			onClickUseAppWithouLogin();
			break;
		}
	}

	private void onClickLogin() {
		if (StringUtil.isEmpty(txtEmail)) {
			txtEmail.setError(getString(R.string.emailEmpty));
			showToast(R.string.emailEmpty);
		} else if (!StringUtil.checkEmail(txtEmail)) {
			txtEmail.setError(getString(R.string.emailInvalid));
			showToast(R.string.emailInvalid);
		} else if (StringUtil.isEmpty(txtPassword)) {
			txtPassword.setError(getString(R.string.passwordEmpty));
			showToast(R.string.passwordEmpty);
		} else {
			String email = txtEmail.getText().toString().trim();
			String password = txtPassword.getText().toString().trim();
			showLoading();
			loginApp(email, password, ACCOUNT_REGISTER);
		}
	}

	private void onClickLoginFacebook() {
		if (facebook == null) {
			facebook = new Facebook(FacebookConstant.FACEBOOK_APPID, this);
		}
		facebook.authorize(this, FacebookConstant.FACEBOOK_PERMISSION, new DialogListener() {
			@Override
			public void onFacebookError(FacebookError e) {
			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onComplete(Bundle values) {
				accessToken = values.getString("access_token");
				getEmailFacebookUser();
			}

			@Override
			public void onCancel() {
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == Facebook.DEFAULT_AUTH_ACTIVITY_CODE) {
				facebook.authorizeCallback(this, requestCode, resultCode, data);
			}
		}
	}

	private void getEmailFacebookUser() {
		GlobalValue.modelManager.getInformationUser(accessToken, new ModelManagerListener() {
			@Override
			public void onSuccess(Object object, SimpleResponse simpleResponse) {
				User user = (User) object;
				if (StringUtil.isEmpty(user.getEmail())) {
					showAlertDialog(R.string.getEmailFacebookFailed, null);
				} else {
					loginApp(user.getEmail(), "", ACCOUNT_FACEBOOK);
				}
			}

			@Override
			public void onError(String message) {

			}
		});
	}

	private void onClickGoToSignUp() {
		startActivity(SignUpActivity.class);
	}

	private void onClickUseAppWithouLogin() {
		loginApp("", "", ACCOUNT_NULL);
	}

	private void loginApp(final String email, final String password, final int loginType) {
		if (loginType == ACCOUNT_NULL) {
			startActivity(MainActivity.class);
			finish();
		} else {
			showLoading();
			GlobalValue.modelManager.login(email, password, loginType, GlobalValue.area.getAreaId(),
					new ModelManagerListener() {
						@Override
						public void onSuccess(Object object, SimpleResponse simpleResponse) {
							if (simpleResponse.isSuccess()) {
								showToast(simpleResponse.getErrorMess());

								GlobalValue.prefs.putUserEmail(email);
								GlobalValue.prefs.putUserPasword(password);
								GlobalValue.prefs.putUserLoginType(loginType);

								Bundle bundle = new Bundle();
								bundle.putParcelable("user_login", (User) object);
								bundle.putString("access_token", accessToken);
								startActivity(MainActivity.class, bundle);
								finish();
							} else {
								showAlertDialog(simpleResponse.getErrorMess());
							}
							hideLoading();
						}

						@Override
						public void onError(String message) {
							showAlertDialog(message);
							hideLoading();
						}
					});
		}
	}
}