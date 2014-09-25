package com.bamboo.tablecross.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bamboo.tablecross.BaseFragment;
import com.bamboo.tablecross.R;

public class SettingFragment extends BaseFragment {

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

	private void initUI(View view) {
	}

	private void initControl() {
	}
}
