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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
	private EditText txtName, txtEmail, txtPhone;
	private TextView lblBirthday;
	private CheckBox chkNotifyOrder, chkNotifyBeforeDate, chkNotifyRestaurant;
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
			setDataUser();
		}
	}

	public void setDataUser() {
		txtName.setText(getMainActivity().user.getNameKanji());
		txtEmail.setText(getMainActivity().user.getEmail());
		txtPhone.setText(getMainActivity().user.getMobile());
		lblBirthday.setText(getMainActivity().user.getBirthdayJapanesse(y, m, d));

		tempUser = getMainActivity().user;

		if (GlobalValue.prefs.getUserLoginType() == SigninActivity.ACCOUNT_REGISTER) {
			btnChangePass.setEnabled(true);
		} else {
			btnChangePass.setEnabled(false);
		}

		chkNotifyOrder.setChecked(getMainActivity().user.getNotifyOrderBool());
		chkNotifyBeforeDate.setChecked(getMainActivity().user.getNotifyBeforeDateBool());
		chkNotifyRestaurant.setChecked(getMainActivity().user.getNotifyRestaurantBool());
	}

	private void initUI(View view) {
		btnSave = view.findViewById(R.id.btnSave);
		btnChangePass = view.findViewById(R.id.btnChangePass);
		btnLogout = view.findViewById(R.id.btnLogout);
		txtName = (EditText) view.findViewById(R.id.txtName);
		txtEmail = (EditText) view.findViewById(R.id.txtEmail);
		txtPhone = (EditText) view.findViewById(R.id.txtPhone);
		lblBirthday = (TextView) view.findViewById(R.id.lblBirthday);
		chkNotifyOrder = (CheckBox) view.findViewById(R.id.chkNotice);
		chkNotifyBeforeDate = (CheckBox) view.findViewById(R.id.chkAcknowledgment);
		chkNotifyRestaurant = (CheckBox) view.findViewById(R.id.chkStoreNameNotify);

		y = getString(R.string.year);
		m = getString(R.string.month);
		d = getString(R.string.day);
	}

	private void initControl() {
		btnSave.setOnClickListener(this);
		btnChangePass.setOnClickListener(this);
		btnLogout.setOnClickListener(this);
		lblBirthday.setOnClickListener(this);

		chkNotifyOrder.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			}
		});
		chkNotifyBeforeDate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

			}
		});
		chkNotifyRestaurant.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

			}
		});
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
				final String name = txtName.getText().toString();
				final String email = txtEmail.getText().toString();
				final String phone = txtPhone.getText().toString();
				final String birthday = tempUser.getBirthday();
				final int notifyOrder = getIntCheckbox(chkNotifyOrder);
				final int notifyBeforeDate = getIntCheckbox(chkNotifyBeforeDate);
				final int notifyRestaurant = getIntCheckbox(chkNotifyRestaurant);

				getBaseActivity().showLoading();
				GlobalValue.modelManager.updateUser(name, email, phone, birthday, notifyOrder, notifyBeforeDate,
						notifyRestaurant, new ModelManagerListener() {
							@Override
							public void onSuccess(Object object, SimpleResponse simpleResponse) {
								getBaseActivity().showAlertDialog(simpleResponse.getErrorMess());
								getBaseActivity().hideLoading();
								getMainActivity().user.updateSetting(name, email, phone, birthday, notifyOrder,
										notifyBeforeDate, notifyRestaurant);
							}

							@Override
							public void onError(String message) {
								getBaseActivity().hideLoading();
							}
						});
			}
		}
	}

	private int getIntCheckbox(CheckBox checkBox) {
		if (checkBox.isChecked()) {
			return 1;
		}
		return 0;
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
		if (StringUtil.isEmpty(tempUser.getBirthday())) {
			picker.updateDate(getMainActivity().user.getBirthdayYear(), getMainActivity().user.getBirthdayMonth(),
					getMainActivity().user.getBirthdayDay());
		} else {
			picker.updateDate(tempUser.getBirthdayYear(), tempUser.getBirthdayMonth(), tempUser.getBirthdayDay());
		}

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