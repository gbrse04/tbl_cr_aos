package com.gip.tablecross.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gip.tablecross.R;
import com.gip.tablecross.BaseActivity;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Area;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.util.Logger;

public class CheckMapActivity extends BaseActivity {
	private boolean isStart = true;
	private Spinner spnArea;
	private List<Area> listAreas;
	private boolean isComBackMainActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_map);
		initUI();
		initControl();
		setData();
		isComBackMainActivity = getIntent().getBooleanExtra("", false);
	}

	private void initUI() {
		spnArea = (Spinner) findViewById(R.id.spnArea);
		listAreas = new ArrayList<Area>();
		listAreas.add(new Area("0", getString(R.string.selectAnArea)));
	}

	private void initControl() {
		spnArea.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (isStart) {
					isStart = false;
				} else {
					GlobalValue.area = listAreas.get(position);
					if (isComBackMainActivity) {
						setResult(RESULT_OK);
					} else {
						startActivity(SigninActivity.class);
					}
					finish();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	private void setData() {
		showLoading();
		GlobalValue.modelManager.getAreas(new ModelManagerListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object object, SimpleResponse simpleResponse) {
				listAreas.addAll((List<Area>) object);
				List<String> list = new ArrayList<String>();
				for (Area area : listAreas) {
					list.add(area.getAreaName());
				}

				Logger.d("", "area " + list.size());
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
						android.R.layout.simple_spinner_item, list);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spnArea.setAdapter(adapter);
				hideLoading();
			}

			@Override
			public void onError(String message) {
				hideLoading();
			}
		});
	}
}
