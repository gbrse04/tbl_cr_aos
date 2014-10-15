package com.gip.tablecross.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.activity.MainActivity;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.util.StringUtil;

public class HomeFragment extends BaseFragment {
	private Restaurant homeRestaurant;
	private TextView lblRestaurantName, lblRestaurantAddress, lblTime, lblNumber, lblDescription, lblNumber0,
			lblNumber1, lblNumber2, lblNumber3, lblNumber4;
	private View layoutRestaurant;
	private AQuery aq;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		initUI(view);
		initControl();
		initData();
		return view;
	}

	private void initUI(View view) {
		lblRestaurantName = (TextView) view.findViewById(R.id.lblRestaurantName);
		lblRestaurantAddress = (TextView) view.findViewById(R.id.lblRestaurantAddress);
		lblTime = (TextView) view.findViewById(R.id.lblTime);
		lblNumber = (TextView) view.findViewById(R.id.lblNumber);
		lblDescription = (TextView) view.findViewById(R.id.lblDescription);
		lblNumber0 = (TextView) view.findViewById(R.id.lblNumber0);
		lblNumber1 = (TextView) view.findViewById(R.id.lblNumber1);
		lblNumber2 = (TextView) view.findViewById(R.id.lblNumber2);
		lblNumber3 = (TextView) view.findViewById(R.id.lblNumber3);
		lblNumber4 = (TextView) view.findViewById(R.id.lblNumber4);
		layoutRestaurant = view.findViewById(R.id.layoutRestaurant);
	}

	private void initControl() {
		aq = new AQuery(getActivity());
		layoutRestaurant.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getMainActivity().currentRestaurant = homeRestaurant;
				goToFragment(MainActivity.RESTAURANT_DETAIL);
				getMainActivity().setHeader(true, getString(R.string.home), false, R.string.share);
			}
		});
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
				aq.id(R.id.imgFood).image(homeRestaurant.getImageUrl(), true, true, 0, 0, null, AQuery.FADE_IN_NETWORK,
						1.0f);
				lblRestaurantName.setText(homeRestaurant.getRestaurantName());
				lblRestaurantAddress.setText(homeRestaurant.getAddress());
				lblTime.setText(homeRestaurant.getPhone());
				lblNumber.setText(String.valueOf(homeRestaurant.getPoint()));
				lblDescription.setText(R.string.numberOfMeals);
				getBaseActivity().hideLoading();
				getMainActivity().getUserInfo();
			}
		});
	}

	public void setUserPoint() {
		String[] s = StringUtil.splitNumber(getMainActivity().user.getPoint());
		lblNumber0.setText(s[0]);
		lblNumber1.setText(s[1]);
		lblNumber2.setText(s[2]);
		lblNumber3.setText(s[3]);
		lblNumber4.setText(s[4]);
	}
}