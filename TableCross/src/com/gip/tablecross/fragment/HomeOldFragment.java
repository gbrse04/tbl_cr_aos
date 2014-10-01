package com.gip.tablecross.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.object.SimpleResponse;

public class HomeOldFragment extends BaseFragment {
	private Restaurant homeRestaurant;
	private TextView lblRestaurantName, lblRestaurantDetail, lblNumberOfMeals, lblNumber1, lblNumber2, lblNumber3;
	private AQuery aq;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		initUI(view);
		initControl();
		initData();
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {

		}
	}

	private void initUI(View view) {
		lblRestaurantName = (TextView) view.findViewById(R.id.lblRestaurantName);
		lblRestaurantDetail = (TextView) view.findViewById(R.id.lblRestaurantDetail);
		lblNumberOfMeals = (TextView) view.findViewById(R.id.lblNumberOfMeals);
		lblNumber1 = (TextView) view.findViewById(R.id.lblNumber1);
		lblNumber2 = (TextView) view.findViewById(R.id.lblNumber2);
		lblNumber3 = (TextView) view.findViewById(R.id.lblNumber3);
	}

	private void initControl() {
	}

	private void initData() {
		getBaseActivity().showLoading();
		GlobalValue.modelManager.getRestaurantInfo(-1, new ModelManagerListener() {
			@Override
			public void onError(String message) {
				getBaseActivity().hideLoading();
			}

			@Override
			public void onSuccess(Object object, SimpleResponse simpleResponse) {
				homeRestaurant = (Restaurant) object;
				aq = new AQuery(getActivity());
				aq.id(R.id.imgFood).image(homeRestaurant.getImageUrl(), true, true, 0, 0, null, AQuery.FADE_IN_NETWORK,
						1.0f);
				lblRestaurantName.setText(homeRestaurant.getRestaurantName());
				lblRestaurantDetail.setText(homeRestaurant.getAddress());
				lblNumberOfMeals.setText(homeRestaurant.getShortDescription());
				String[] s = splitNumber(homeRestaurant.getOrderCount());
				lblNumber1.setText(s[0]);
				lblNumber2.setText(s[1]);
				lblNumber3.setText(s[2]);
				getBaseActivity().hideLoading();
			}

		});
	}

	private String[] splitNumber(int number) {
		String[] s = new String[3];
		if (number < 10) {
			s[0] = "0";
			s[1] = "0";
			s[2] = String.valueOf(number);
		} else if (number < 100) {
			s[0] = "0";
			s[1] = String.valueOf(number / 10);
			s[2] = String.valueOf(number % 10);
		} else {
			int temp = number / 100;
			int temp1 = number - 100 * temp;
			s[0] = String.valueOf(temp);
			s[1] = String.valueOf(temp1 / 10);
			s[2] = String.valueOf(temp1 % 10);
		}

		return s;
	}
}
