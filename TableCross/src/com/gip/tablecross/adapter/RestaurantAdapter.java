package com.gip.tablecross.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;
import com.gip.tablecross.R;
import com.gip.tablecross.object.Restaurant;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
	private Activity context;
	private List<Restaurant> listFoods;
	private AQuery listAq;

	public RestaurantAdapter(Activity context, List<Restaurant> listFoods) {
		super(context, R.layout.item_restaurant, listFoods);
		this.context = context;
		this.listFoods = listFoods;
		listAq = new AQuery(context);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.item_restaurant, null);
		}

		Restaurant item = listFoods.get(position);

		AQuery aq = listAq.recycle(convertView);

		aq.id(R.id.imgFood).image(item.getImageUrl(), true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);
		aq.id(R.id.lblRestaurantName).text(item.getRestaurantName());
		aq.id(R.id.lblDescription).text(item.getDescription());
		aq.id(R.id.lblTime).text(item.getPhone());
		aq.id(R.id.lblNumber).text("123");

		return rowView;
	}
}