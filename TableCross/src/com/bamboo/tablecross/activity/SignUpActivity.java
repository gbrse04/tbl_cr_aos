package com.bamboo.tablecross.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.bamboo.tablecross.BaseActivity;
import com.bamboo.tablecross.R;
import com.bamboo.tablecross.common.GlobalValue;
import com.bamboo.tablecross.modelmanager.ModelManagerListener;
import com.bamboo.tablecross.object.SimpleResponse;

public class SignUpActivity extends BaseActivity implements OnClickListener {
	private View btnSignUp, btnNoLogin;
	private EditText txtEmail, txtPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		initUI();
		initControl();
	}

	private void initUI() {
		btnSignUp = findViewById(R.id.btnSignUp);
		btnNoLogin = findViewById(R.id.btnNoLogin);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
	}

	private void initControl() {
		btnSignUp.setOnClickListener(this);
		btnNoLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSignUp:
			onClickSignUp();
			break;

		case R.id.btnNoLogin:
			onClickNoLogin();
			break;

		}
	}

	private void onClickSignUp() {
		String email = txtEmail.getText().toString();
		String password = txtPassword.getText().toString();
		showLoading();
		GlobalValue.modelManager.register(email, password, "", GlobalValue.area.getAreaId(),
				new ModelManagerListener() {
					@Override
					public void onSuccess(Object object, SimpleResponse simpleResponse) {
						showAlertDialog(simpleResponse.getErrorMess());
						hideLoading();
					}

					@Override
					public void onError(String message) {
						hideLoading();
					}
				});
	}

	private void onClickNoLogin() {
	}

}
