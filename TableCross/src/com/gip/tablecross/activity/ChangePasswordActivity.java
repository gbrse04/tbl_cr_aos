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
		
	}

	private void onClickBack() {
		onBackPressed();
	}
	
	private void requestChangePass() {
		String oldPassword = txtOldPass.getText().toString();
		String newPassword = txtOldPass.getText().toString();
		String newPasswordAgain = txtOldPass.getText().toString();
GlobalValue.modelManager.changePassword(oldPassword, newPassword, new ModelManagerListener() {
	
	@Override
	public void onSuccess(Object object, SimpleResponse simpleResponse) {
	}
	
	@Override
	public void onError(String message) {
	}
});
	}
}
