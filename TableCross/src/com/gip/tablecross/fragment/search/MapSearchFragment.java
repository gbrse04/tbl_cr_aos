package com.gip.tablecross.fragment.search;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.gip.tablecross.R;
import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.activity.MainActivity;
import com.gip.tablecross.adapter.RestaurantAdapter;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.widget.AutoBgButton;

public class MapSearchFragment extends BaseFragment implements OnClickListener {
	private static final int TAB_DISTANCE_1 = 0;
	private static final int TAB_DISTANCE_2 = 1;
	private static final int TAB_DISTANCE_3 = 2;
	private ListView lsvFoodMap;
	private List<Restaurant> listRestaurants;
	private RestaurantAdapter adapter;
	private AutoBgButton btnDistance1, btnDistance2, btnDistance3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_map, container, false);

		lsvFoodMap = (ListView) view.findViewById(R.id.lsvFoodMap);
		listRestaurants = new ArrayList<Restaurant>();
		adapter = new RestaurantAdapter(getActivity(), listRestaurants);
		lsvFoodMap.setAdapter(adapter);

		initUI(view);
		initControl();
		setSelectTabDistance(TAB_DISTANCE_1);

		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
		}
	}

	private void initUI(View view) {
		btnDistance1 = (AutoBgButton) view.findViewById(R.id.btnDistance1);
		btnDistance2 = (AutoBgButton) view.findViewById(R.id.btnDistance2);
		btnDistance3 = (AutoBgButton) view.findViewById(R.id.btnDistance3);
	}

	private void initControl() {
		btnDistance1.setOnClickListener(this);
		btnDistance2.setOnClickListener(this);
		btnDistance3.setOnClickListener(this);

		lsvFoodMap.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				goToFragment(MainActivity.RESTAURANT_DETAIL);
			}
		});
	}

	private void setSelectTabDistance(int tab) {
		switch (tab) {
		case TAB_DISTANCE_1:
			btnDistance1.setBackgroundResource(R.drawable.tab_distance_left_selected);
			btnDistance2.setBackgroundResource(R.drawable.tab_distance_center);
			btnDistance3.setBackgroundResource(R.drawable.tab_distance_right);

			btnDistance1.setTextColor(Color.WHITE);
			btnDistance2.setTextColor(Color.BLACK);
			btnDistance3.setTextColor(Color.BLACK);
			break;

		case TAB_DISTANCE_2:
			btnDistance1.setBackgroundResource(R.drawable.tab_distance_left);
			btnDistance2.setBackgroundResource(R.drawable.tab_distance_center_selected);
			btnDistance3.setBackgroundResource(R.drawable.tab_distance_right);

			btnDistance1.setTextColor(Color.BLACK);
			btnDistance2.setTextColor(Color.WHITE);
			btnDistance3.setTextColor(Color.BLACK);
			break;

		default:
			btnDistance1.setBackgroundResource(R.drawable.tab_distance_left);
			btnDistance2.setBackgroundResource(R.drawable.tab_distance_center);
			btnDistance3.setBackgroundResource(R.drawable.tab_distance_right_selected);

			btnDistance1.setTextColor(Color.BLACK);
			btnDistance2.setTextColor(Color.BLACK);
			btnDistance3.setTextColor(Color.WHITE);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDistance1:
			onClickDistance1();
			break;

		case R.id.btnDistance2:
			onClickDistance2();
			break;

		case R.id.btnDistance3:
			onClickDistance3();
			break;

		}
	}

	private void onClickDistance1() {
		setSelectTabDistance(TAB_DISTANCE_1);
	}

	private void onClickDistance2() {
		setSelectTabDistance(TAB_DISTANCE_2);
	}

	private void onClickDistance3() {
		setSelectTabDistance(TAB_DISTANCE_3);
	}
}