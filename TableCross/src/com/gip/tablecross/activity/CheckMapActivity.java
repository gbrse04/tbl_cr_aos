package com.gip.tablecross.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gip.tablecross.BaseActivity;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Area;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.object.User;
import com.gip.tablecross.util.Logger;
import com.gip.tablecross.util.StringUtil;

public class CheckMapActivity extends BaseActivity {
	private boolean isStart = true;
	private Spinner spnArea;
	private boolean isComBackMainActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_map);
		initUI();
		initControl();
		setData();
	}

	private void initUI() {
		spnArea = (Spinner) findViewById(R.id.spnArea);
	}

	private void initControl() {
		spnArea.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (isStart) {
					isStart = false;
				} else {
					GlobalValue.area = GlobalValue.listAreas.get(position);
					Logger.e("", "area: " + GlobalValue.area);
					GlobalValue.prefs.putArea(GlobalValue.area);
					if (isComBackMainActivity) {
						setResult(RESULT_OK);
						finish();
					} else if (StringUtil.isEmpty(GlobalValue.prefs.getUserEmail())) {
						startActivity(SigninActivity.class);
						finish();
					} else {
						autoLogin();
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	private int getPositionCurrentArea() {
		Area currentArea = GlobalValue.prefs.getArea();
		for (int i = 0; i < GlobalValue.listAreas.size(); i++) {
			if (GlobalValue.listAreas.get(i).getAreaName().equalsIgnoreCase(currentArea.getAreaName())) {
				return i;
			}
		}
		return 0;
	}

	private void setData() {
		isComBackMainActivity = getIntent().getBooleanExtra("is_come_back_main_activity", false);
		if (isComBackMainActivity) {
			setDataSpinnerArea();
			spnArea.setSelection(getPositionCurrentArea());
		} else {
			showLoading();
			GlobalValue.modelManager.getAreas(new ModelManagerListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void onSuccess(Object object, SimpleResponse simpleResponse) {
					GlobalValue.listAreas.addAll((List<Area>) object);
					setDataSpinnerArea();
					hideLoading();
				}

				@Override
				public void onError(String message) {
					hideLoading();
				}
			});
		}
	}

	private void setDataSpinnerArea() {
		List<String> list = new ArrayList<String>();
		for (Area area : GlobalValue.listAreas) {
			list.add(area.getAreaName());
		}

		Logger.d("", "area " + list.size());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item,
				list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnArea.setAdapter(adapter);
	}

	private void autoLogin() {
		showLoading(R.string.login);
		GlobalValue.modelManager.login(GlobalValue.prefs.getUserEmail(), GlobalValue.prefs.getUserPasword(),
				GlobalValue.prefs.getUserLoginType(), GlobalValue.area.getAreaId(), new ModelManagerListener() {
					@Override
					public void onSuccess(Object object, final SimpleResponse simpleResponse) {
						if (simpleResponse.isSuccess()) {
							showToast(simpleResponse.getErrorMess());

							Bundle bundle = new Bundle();
							bundle.putParcelable("user_login", (User) object);
							startActivity(MainActivity.class, bundle);
							finish();
						} else {
							showAlertDialog(simpleResponse.getErrorMess(), new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									if (simpleResponse.getErrorCode() == 8) {
										startActivity(SigninActivity.class);
										finish();
									} else {
										startActivity(CheckMapActivity.class);
										finish();
									}
								}
							});
						}
						hideLoading();
					}

					@Override
					public void onError(final String message) {
						showAlertDialog(message, new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								startActivity(CheckMapActivity.class);
								finish();
							}
						});
						hideLoading();
					}
				});
	}
}
