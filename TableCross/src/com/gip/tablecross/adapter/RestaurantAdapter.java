package com.gip.tablecross.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
			ViewHolder viewHolder = new ViewHolder();

			viewHolder.lblRestaurantName = (TextView) rowView.findViewById(R.id.lblRestaurantName);
			viewHolder.lblRestaurantAddress = (TextView) rowView.findViewById(R.id.lblRestaurantAddress);
			viewHolder.lblDescription = (TextView) rowView.findViewById(R.id.lblDescription);
			viewHolder.lblTime = (TextView) rowView.findViewById(R.id.lblTime);
			viewHolder.lblNumber = (TextView) rowView.findViewById(R.id.lblNumber);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		Restaurant item = listFoods.get(position);

		AQuery aq = listAq.recycle(convertView);

		aq.id(R.id.imgFood).image(item.getImageUrl(), true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);
		holder.lblRestaurantName.setText(item.getRestaurantName());
		holder.lblRestaurantAddress.setText(item.getAddress());
		holder.lblDescription.setText(item.getShortDescription());
		holder.lblTime.setText(item.getPhone());
		holder.lblNumber.setText("" + item.getOrderCount());

		return rowView;
	}

	static class ViewHolder {
		private TextView lblRestaurantName;
		private TextView lblRestaurantAddress;
		private TextView lblDescription;
		private TextView lblTime;
		private TextView lblNumber;
	}
}