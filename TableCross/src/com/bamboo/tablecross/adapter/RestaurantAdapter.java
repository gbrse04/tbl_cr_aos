package com.bamboo.tablecross.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bamboo.tablecross.R;
import com.bamboo.tablecross.object.Restaurant;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
	private Activity context;
	private List<Restaurant> listFoods;private AQuery listAq;

	public RestaurantAdapter(Activity context, List<Restaurant> listFoods) {
		super(context, R.layout.item_restaurant, listFoods);
		this.context = context;
		this.listFoods = listFoods;listAq=new AQuery(context); 
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.item_restaurant, null);
			ViewHolder viewHolder = new ViewHolder();

			viewHolder.imgFood = (ImageView) rowView.findViewById(R.id.imgFood);
			viewHolder.lblFoodName = (TextView) rowView.findViewById(R.id.lblFoodName);
			viewHolder.lblFoodDescription = (TextView) rowView.findViewById(R.id.lblFoodDescription);
			viewHolder.lblTime = (TextView) rowView.findViewById(R.id.lblTime);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		Restaurant item = listFoods.get(position);
//		holder.lblFoodName.setText(item.getRestaurantName());
//		holder.lblFoodDescription.setText(item.getAddress());
//		holder.lblTime.setText(item.getOrderDate());
		
		AQuery aq = listAq.recycle(convertView);
		aq.id(R.id.lblFoodName).text(item.getRestaurantName());
		aq.id(R.id.lblFoodDescription).text(item.getAddress());
		aq.id(R.id.lblTime).text(item.getOrderDate());
			aq.id(R.id.imgFood).image(item.getImageUrl(), true, true, 0, 0, null, AQuery.FADE_IN_NETWORK,
					1.0f);

		return rowView;
	}

	static class ViewHolder {
		private ImageView imgFood;
		private TextView lblFoodName;
		private TextView lblFoodDescription;
		private TextView lblTime;
	}
}