package com.gip.tablecross.fragment;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;

public class RestaurantDetailFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);

		initControl(view);
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
		}
	}

	private void initControl(View view) {
		view.findViewById(R.id.btnCall).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showOrderDialog();
			}
		});

		view.findViewById(R.id.btnMap).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				double latitude = 35.640147;
				double longitude = 139.5802687;
				String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
		});

		view.findViewById(R.id.btnWeb).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
				startActivity(browserIntent);
			}
		});
	}

	@SuppressLint("InflateParams")
	private void showOrderDialog() {
		View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_order, null);
		final AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(dialogView)
				.setTitle(R.string.orderNumber).create();
		NumberPicker picker = (NumberPicker) dialogView.findViewById(R.id.numberPicker);
		String[] values = new String[30];
		for (int i = 0; i < values.length; i++) {
			values[i] = Integer.toString(i + 1);
		}
		picker.setMinValue(0);
		picker.setMaxValue(values.length - 1);
		picker.setDisplayedValues(values);

		dialogView.findViewById(R.id.btnCall).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:123456789"));
				startActivity(callIntent);
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
}
