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
import com.gip.tablecross.util.NetworkUtil;
import com.gip.tablecross.util.StringUtil;
import com.gip.tablecross.widget.AutoBgButton;

public class ChangePasswordActivity extends BaseActivity implements OnClickListener {
	private AutoBgButton btnChange;
	private EditText txtOldPass, txtNewPass, txtNewPassAgain;
	private ImageView btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		initUI();
		initControl();
	}

	private void initUI() {
		btnChange = (AutoBgButton) findViewById(R.id.btnChange);
		txtOldPass = (EditText) findViewById(R.id.txtOldPass);
		txtNewPass = (EditText) findViewById(R.id.txtNewPass);
		txtNewPassAgain = (EditText) findViewById(R.id.txtNewPassAgain);
		btnBack = (ImageView) findViewById(R.id.btnBack);
	}

	private void initControl() {
		btnChange.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnChange:
			onClickChange();
			break;

		case R.id.btnBack:
			onClickBack();
			break;
		}
	}

	private void onClickChange() {
		requestChangePass();
	}

	private void onClickBack() {
		onBackPressed();
	}

	private void requestChangePass() {
		if (StringUtil.isEmpty(txtOldPass)) {
			txtOldPass.setError(getString(R.string.passwordEmpty));
			showToast(R.string.passwordEmpty);
		} else if (StringUtil.isEmpty(txtNewPass)) {
			txtNewPass.setError(getString(R.string.passwordEmpty));
			showToast(R.string.passwordEmpty);
		} else if (!StringUtil.checkMatch(txtNewPass, txtNewPassAgain)) {
			txtNewPassAgain.setError(getString(R.string.passwordNotMatch));
			showToast(R.string.passwordNotMatch);
		} else {
			if (!NetworkUtil.isNetworkAvailable(this)) {
				showDialogNoNetwork();
			} else {
				String oldPassword = txtOldPass.getText().toString();
				final String newPassword = txtNewPass.getText().toString();
				showLoading();
				GlobalValue.modelManager.changePassword(oldPassword, newPassword, new ModelManagerListener() {
					@Override
					public void onSuccess(Object object, SimpleResponse simpleResponse) {
						showAlertDialog(simpleResponse.getErrorMess());
						if (simpleResponse.isSuccess()) {
							GlobalValue.prefs.putUserPasword(newPassword);
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
	}
}
