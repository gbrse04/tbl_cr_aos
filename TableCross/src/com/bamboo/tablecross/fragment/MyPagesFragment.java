package com.bamboo.tablecross.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.bamboo.tablecross.BaseFragment;
import com.bamboo.tablecross.R;
import com.bamboo.tablecross.activity.MainActivity;

public class MyPagesFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_page, container, false);
		initControl(view);
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
		}
	}

	private void initControl(View view) {
		view.findViewById(R.id.layoutGotoShare).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getMainActivity().setTabSelected(MainActivity.TAB_SHARE);
			}
		});
	}
}
