package com.gip.tablecross.fragment.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;

public class FeatureSearchFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_feature, container, false);
		initUI(view);
		initControl();
		return view;
	}

	private void initUI(View view) {
	}

	private void initControl() {
	}
}
