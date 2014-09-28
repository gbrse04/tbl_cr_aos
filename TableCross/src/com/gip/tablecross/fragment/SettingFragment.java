package com.gip.tablecross.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.activity.ChangePasswordActivity;
import com.gip.tablecross.activity.SigninActivity;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.util.Logger;

public class SettingFragment extends BaseFragment implements OnClickListener {
	private View btnSave, btnChangePass, btnLogout;
	private EditText txtIdentity, txtEmail, txtPhone;
	private TextView lblBirthday;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, container, false);

		initUI(view);
		initControl();
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
		}
	}

	public void setDataUser() {
		txtIdentity.setText(GlobalValue.user.getUserId());
		txtEmail.setText(GlobalValue.user.getEmail());
		txtPhone.setText(GlobalValue.user.getMobile());
		lblBirthday.setText("");

		Logger.e("", "user: " + GlobalValue.user);
	}

	private void initUI(View view) {
		btnSave = view.findViewById(R.id.btnSave);
		btnChangePass = view.findViewById(R.id.btnChangePass);
		btnLogout = view.findViewById(R.id.btnLogout);
		txtIdentity = (EditText) view.findViewById(R.id.txtIdentity);
		txtEmail = (EditText) view.findViewById(R.id.txtEmail);
		txtPhone = (EditText) view.findViewById(R.id.txtPhone);
		lblBirthday = (TextView) view.findViewById(R.id.lblBirthday);
	}

	private void initControl() {
		btnSave.setOnClickListener(this);
		btnChangePass.setOnClickListener(this);
		btnLogout.setOnClickListener(this);
		lblBirthday.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSave:
			onClickSave();
			break;

		case R.id.btnChangePass:
			onClickChangePass();
			break;

		case R.id.btnLogout:
			onClickLogout();
			break;

		case R.id.lblBirthday:
			onClickBirthday();
			break;
		}
	}

	private void onClickSave() {
	}

	private void onClickChangePass() {
		startActivity(ChangePasswordActivity.class);
	}

	private void onClickLogout() {
		getBaseActivity().showLoading();
		GlobalValue.modelManager.logout(new ModelManagerListener() {

			@Override
			public void onSuccess(Object object, SimpleResponse simpleResponse) {
				if (simpleResponse.isSuccess()) {
					startActivity(SigninActivity.class);
					getMainActivity().finish();
					showToast(simpleResponse.getErrorMess());
				}
				getBaseActivity().hideLoading();
			}

			@Override
			public void onError(String message) {
				getBaseActivity().hideLoading();
			}
		});
	}

	private void onClickBirthday() {
	}
}