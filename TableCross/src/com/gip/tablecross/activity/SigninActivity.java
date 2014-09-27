package com.gip.tablecross.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.gip.tablecross.BaseActivity;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.util.Logger;
import com.gip.tablecross.util.StringUtil;
import com.gip.tablecross.widget.AutoBgButton;

public class SigninActivity extends BaseActivity implements OnClickListener {
	private final String ACCOUNT_REGISTER = "0";
	private final String ACCOUNT_FACEBOOK = "1";
	private AutoBgButton btnLogin, btnLoginFacebook;
	private LoginButton btnLoginButtonFacebook;
	private EditText txtEmail, txtPassword;
	private View lblGoToSignUp;
	private boolean isClickLoginFacebook = false;

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
		btnLoginButtonFacebook = (LoginButton) findViewById(R.id.btnLoginButtonFacebook);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		lblGoToSignUp = findViewById(R.id.lblGoToSignUp);

		txtEmail.setText("thibt@vivas.vn");
		txtPassword.setText("123456");
	}

	private void initControl() {
		btnLogin.setOnClickListener(this);
		btnLoginFacebook.setOnClickListener(this);
		lblGoToSignUp.setOnClickListener(this);
		btnLoginButtonFacebook.setReadPermissions("email");
		btnLoginButtonFacebook.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				try {
					if (isClickLoginFacebook) {
						String email = (String) user.getProperty("email");
						Logger.e("", "email: " + email);
						if (StringUtil.isEmpty(email)) {
							showAlertDialog(R.string.getEmailFacebookFailed, null);
						} else {
							loginApp(email);
						}
					}
				} catch (Exception e) {
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			Logger.e("", "token: " + session.getAccessToken());
			GlobalValue.accessTokenFacebook = session.getAccessToken();
		}
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

		case R.id.lblGoToSignUp:
			onClickGoToSignUp();
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
			GlobalValue.modelManager.login(email, password, ACCOUNT_REGISTER, GlobalValue.area.getAreaId(),
					new ModelManagerListener() {
						@Override
						public void onSuccess(Object object, SimpleResponse simpleResponse) {
							if (simpleResponse.getSuccess().equals("true")) {
								showToast(simpleResponse.getErrorMess());
								startActivity(MainActivity.class);
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

	private void onClickLoginFacebook() {
		btnLoginButtonFacebook.performClick();
		if (!StringUtil.isEmpty(GlobalValue.accessTokenFacebook)) {
			btnLoginButtonFacebook.performClick();
		}
		isClickLoginFacebook = true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		}
	}

	private void onClickGoToSignUp() {
		startActivity(SignUpActivity.class);
	}

	private void loginApp(String email) {
		showLoading();
		GlobalValue.modelManager.login(email, "", ACCOUNT_FACEBOOK, GlobalValue.area.getAreaId(),
				new ModelManagerListener() {
					@Override
					public void onSuccess(Object object, SimpleResponse simpleResponse) {
						if (simpleResponse.getSuccess().equals("true")) {
							showToast(simpleResponse.getErrorMess());
							startActivity(MainActivity.class);
							finish();
						} else {
							showAlertDialog(simpleResponse.getErrorMess());
						}
						hideLoading();
					}

					@Override
					public void onError(String message) {
						hideLoading();
					}
				});
	}
}