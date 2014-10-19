package com.gip.tablecross.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.activity.ChangePasswordActivity;
import com.gip.tablecross.activity.SigninActivity;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.listener.DialogListener;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.object.User;
import com.gip.tablecross.util.NetworkUtil;
import com.gip.tablecross.util.StringUtil;

public class SettingFragment extends BaseFragment implements OnClickListener {
	private View btnSave, btnChangePass, btnLogout;
	private EditText txtIdentity, txtEmail, txtPhone;
	private TextView lblBirthday;
	private CheckBox chkNotice, chkAcknowledgment, chkStoreNameNotify;
	private String y, m, d;
	private User tempUser;

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
		if (GlobalValue.prefs.getUserLoginType() == SigninActivity.ACCOUNT_FACEBOOK) {
			txtIdentity.setText(String.valueOf(getMainActivity().user.getNameKanji()));
		} else {
			txtIdentity.setText(String.valueOf(getMainActivity().user.getUserId()));
		}
		txtEmail.setText(getMainActivity().user.getEmail());
		txtPhone.setText(getMainActivity().user.getMobile());
		lblBirthday.setText(getMainActivity().user.getBirthdayJapanesse(y, m, d));
	}

	private void initUI(View view) {
		btnSave = view.findViewById(R.id.btnSave);
		btnChangePass = view.findViewById(R.id.btnChangePass);
		btnLogout = view.findViewById(R.id.btnLogout);
		txtIdentity = (EditText) view.findViewById(R.id.txtIdentity);
		txtEmail = (EditText) view.findViewById(R.id.txtEmail);
		txtPhone = (EditText) view.findViewById(R.id.txtPhone);
		lblBirthday = (TextView) view.findViewById(R.id.lblBirthday);
		chkNotice = (CheckBox) view.findViewById(R.id.chkNotice);
		chkAcknowledgment = (CheckBox) view.findViewById(R.id.chkAcknowledgment);
		chkStoreNameNotify = (CheckBox) view.findViewById(R.id.chkStoreNameNotify);
	}

	private void initControl() {
		y = getString(R.string.year);
		m = getString(R.string.month);
		d = getString(R.string.day);
		tempUser = new User();

		btnSave.setOnClickListener(this);
		btnChangePass.setOnClickListener(this);
		btnLogout.setOnClickListener(this);
		lblBirthday.setOnClickListener(this);
		if (GlobalValue.prefs.getUserLoginType() == SigninActivity.ACCOUNT_REGISTER) {
			btnChangePass.setEnabled(true);
		} else {
			btnChangePass.setEnabled(false);
		}

		chkNotice.setChecked(true);
		chkAcknowledgment.setChecked(true);
		chkStoreNameNotify.setChecked(true);
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
		if (StringUtil.isEmpty(txtEmail)) {
			txtEmail.setError(getString(R.string.emailEmpty));
			showToast(R.string.emailEmpty);
		} else if (!StringUtil.checkEmail(txtEmail)) {
			txtEmail.setError(getString(R.string.emailInvalid));
			showToast(R.string.emailInvalid);
		} else {
			if (!NetworkUtil.isNetworkAvailable(this)) {
				showDialogNoNetwork();
			} else {
				String nameTemp = txtIdentity.getText().toString();
				String email = txtEmail.getText().toString();
				String phone = txtPhone.getText().toString();
				try {
					Integer.parseInt(nameTemp);
					nameTemp = "";
				} catch (Exception e) {
				}
				final String name = nameTemp;

				getBaseActivity().showLoading();
				GlobalValue.modelManager.updateUser(name, email, phone, tempUser.getBirthday(),
						new ModelManagerListener() {
							@Override
							public void onSuccess(Object object, SimpleResponse simpleResponse) {
								getBaseActivity().showAlertDialog(simpleResponse.getErrorMess());
								getBaseActivity().hideLoading();
								GlobalValue.prefs.putUserName(name);
							}

							@Override
							public void onError(String message) {
								getBaseActivity().hideLoading();
							}
						});
			}
		}
	}

	private void onClickChangePass() {
		startActivity(ChangePasswordActivity.class);
	}

	private void onClickLogout() {
		getBaseActivity().showQuestionDialog(getString(R.string.confirmLogout), new DialogListener() {
			@Override
			public void onOk(Object object) {
				logout();
			}

			@Override
			public void onCancel(Object object) {
			}
		});
	}

	private void logout() {
		if (!NetworkUtil.isNetworkAvailable(this)) {
			showDialogNoNetwork();
		} else {
			getBaseActivity().showLoading();
			GlobalValue.modelManager.logout(new ModelManagerListener() {
				@Override
				public void onSuccess(Object object, SimpleResponse simpleResponse) {
					if (simpleResponse.isSuccess()) {
						startActivity(SigninActivity.class);
						getMainActivity().finish();
						GlobalValue.prefs.clearUser();
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
	}

	private void onClickBirthday() {
		showChooseBirthdayDialog();
	}

	@SuppressLint("InflateParams")
	private void showChooseBirthdayDialog() {
		View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_choose_birthday, null);
		((TextView) dialogView.findViewById(R.id.lblChooseBirthday)).setSelected(true);
		final DatePicker picker = (DatePicker) dialogView.findViewById(R.id.datePicker);
		final AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(dialogView)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						lblBirthday.setText(picker.getYear() + y + (picker.getMonth() + 1) + m + picker.getDayOfMonth()
								+ d);
						tempUser.setBirthday(picker.getYear(), picker.getMonth() + 1, picker.getDayOfMonth());
					}
				}).setNegativeButton(R.string.cancel, null).create();

		dialog.show();
	}
}