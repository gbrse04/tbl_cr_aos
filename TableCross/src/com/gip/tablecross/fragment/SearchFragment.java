package com.gip.tablecross.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.activity.MainActivity;
import com.gip.tablecross.fragment.search.FeatureSearchFragment;

public class SearchFragment extends BaseFragment implements OnClickListener {
	private View lblSearchCondition, lblSearchLocation, lblSearchMap, lblSearchHistory;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, container, false);

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
		lblSearchCondition = view.findViewById(R.id.lblSearchCondition);
		lblSearchLocation = view.findViewById(R.id.lblSearchLocation);
		lblSearchMap = view.findViewById(R.id.lblSearchMap);
		lblSearchHistory = view.findViewById(R.id.lblSearchHistory);
	}

	private void initControl() {
		lblSearchCondition.setOnClickListener(this);
		lblSearchLocation.setOnClickListener(this);
		lblSearchMap.setOnClickListener(this);
		lblSearchHistory.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lblSearchCondition:
			onClickSearchCondition();
			break;

		case R.id.lblSearchLocation:
			onClickSearchLocation();
			break;

		case R.id.lblSearchMap:
			onClickSearchMap();
			break;

		case R.id.lblSearchHistory:
			onClickSearchHistory();
			break;
		}
	}

	private void onClickSearchCondition() {
		goToFragment(MainActivity.SEARCH_CONDITION);
	}

	private void onClickSearchLocation() {
		goToFragment(MainActivity.SEARCH_LOCATION);
	}

	private void onClickSearchMap() {
		goToFragment(MainActivity.SEARCH_HISTORY);
	}

	private void onClickSearchHistory() {
		goToFragment(MainActivity.SEARCH_FEATURE);
		((FeatureSearchFragment) getMainActivity().arrayFragments[MainActivity.SEARCH_FEATURE]).getData();
	}
}
