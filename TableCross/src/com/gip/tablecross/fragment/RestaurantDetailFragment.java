package com.gip.tablecross.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.activity.MainActivity;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.util.NetworkUtil;

public class RestaurantDetailFragment extends BaseFragment {
	private ImageView imgFood;
	private TextView lblRestaurantName, lblRestaurantAddress, lblNumber, lblShortDescription, lblDescription;
	private AQuery aq;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
		initUI(view);
		initControl(view);
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
		}
	}

	private void initUI(View view) {
		imgFood = (ImageView) view.findViewById(R.id.imgFood);
		lblRestaurantName = (TextView) view.findViewById(R.id.lblRestaurantName);
		lblRestaurantAddress = (TextView) view.findViewById(R.id.lblRestaurantAddress);
		lblNumber = (TextView) view.findViewById(R.id.lblNumber);
		lblShortDescription = (TextView) view.findViewById(R.id.lblShortDescription);
		lblDescription = (TextView) view.findViewById(R.id.lblDescription);
	}

	private void initControl(View view) {
		aq = new AQuery(getActivity());
		view.findViewById(R.id.btnCall).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showOrderDialog();
			}
		});

		view.findViewById(R.id.btnMap).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getMainActivity().gotoFragment(MainActivity.RESTAURANT_MAP);
				((RestaurantMapFragment) getMainActivity().arrayFragments.get(MainActivity.RESTAURANT_MAP))
						.setMarkOnMap();
			}
		});

		view.findViewById(R.id.btnWeb).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getBaseActivity().openUrl(getMainActivity().currentRestaurant.getOrderWebUrl());
			}
		});

		imgFood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goToFragment(MainActivity.GALLERY);
				((GalleryFragment) getMainActivity().arrayFragments.get(MainActivity.GALLERY)).getImages();
			}
		});
	}

	@SuppressLint("InflateParams")
	private void showOrderDialog() {
		View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_order, null);
		final AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(dialogView).create();
		final NumberPicker picker = (NumberPicker) dialogView.findViewById(R.id.numberPicker);
		String[] values = new String[31];
		values[0] = " ";
		for (int i = 1; i < values.length; i++) {
			values[i] = Integer.toString(i);
		}
		picker.setMinValue(0);
		picker.setMaxValue(values.length - 1);
		picker.setDisplayedValues(values);

		dialogView.findViewById(R.id.btnCall).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!NetworkUtil.isNetworkAvailable(getActivity())) {
					showDialogNoNetwork();
				} else {
					getBaseActivity().showLoading();
					GlobalValue.modelManager.order(getMainActivity().currentRestaurant.getRestaurantId(),
							picker.getValue(), new ModelManagerListener() {
								@Override
								public void onSuccess(Object object, SimpleResponse simpleResponse) {
									dialog.dismiss();
									showToast(simpleResponse.getErrorMess());
									getBaseActivity().hideLoading();
									Intent callIntent = new Intent(Intent.ACTION_CALL);
									callIntent.setData(Uri.parse("tel:"
											+ getMainActivity().currentRestaurant.getPhone()));
									startActivity(callIntent);
								}

								@Override
								public void onError(String message) {
									showToast(message);
									getBaseActivity().hideLoading();
								}
							});

				}
			}
		});

		dialogView.findViewById(R.id.btnCancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});

		dialog.show();
	}

	public void setCurrentRestaurant() {
		aq.id(imgFood).image(getMainActivity().currentRestaurant.getImageUrl(), true, true, 0, 0, null,
				AQuery.FADE_IN_NETWORK, 1.0f);
		lblRestaurantName.setText(getMainActivity().currentRestaurant.getRestaurantName());
		lblRestaurantAddress.setText(getMainActivity().currentRestaurant.getAddress());
		lblNumber.setText(String.valueOf(getMainActivity().currentRestaurant.getPoint()));
		lblShortDescription.setText(getMainActivity().currentRestaurant.getShortDescription());
		lblDescription.setText(getMainActivity().currentRestaurant.getDescription());
	}
}