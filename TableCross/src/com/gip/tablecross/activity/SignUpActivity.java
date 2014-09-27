package com.gip.tablecross.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.gip.tablecross.BaseActivity;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.util.StringUtil;
import com.gip.tablecross.widget.AutoBgButton;

public class SignUpActivity extends BaseActivity implements OnClickListener {
	private AutoBgButton btnRegister;
	private EditText txtEmail, txtPhone, txtPassword, txtPasswordAgain;
	private ImageView btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		initUI();
		initControl();
	}

	private void initUI() {
		btnRegister = (AutoBgButton) findViewById(R.id.btnRegister);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtPhone = (EditText) findViewById(R.id.txtPhone);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		txtPasswordAgain = (EditText) findViewById(R.id.txtPasswordAgain);
		btnBack = (ImageView) findViewById(R.id.btnBack);
	}

	private void initControl() {
		btnRegister.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnRegister:
			onClickRegister();
			break;

		case R.id.btnBack:
			onClickBack();
			break;
		}
	}

	private void onClickRegister() {
		if (StringUtil.isEmpty(txtEmail)) {
			txtEmail.setError(getString(R.string.emailEmpty));
			showToast(R.string.emailEmpty);
		} else if (!StringUtil.checkEmail(txtEmail)) {
			txtEmail.setError(getString(R.string.emailInvalid));
			showToast(R.string.emailInvalid);
		} else if (StringUtil.isEmpty(txtPassword)) {
			txtPassword.setError(getString(R.string.passwordEmpty));
			showToast(R.string.passwordEmpty);
		} else if (!StringUtil.checkMatch(txtPassword, txtPasswordAgain)) {
			txtPasswordAgain.setError(getString(R.string.passwordNotMatch));
			showToast(R.string.passwordNotMatch);
		} else {
			String email = txtEmail.getText().toString();
			String password = txtPassword.getText().toString();
			String phone = txtPhone.getText().toString();
			showLoading();
			GlobalValue.modelManager.register(email, password, phone, "", GlobalValue.area.getAreaId(),
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
	}

	private void onClickBack() {
		onBackPressed();
	}
}
