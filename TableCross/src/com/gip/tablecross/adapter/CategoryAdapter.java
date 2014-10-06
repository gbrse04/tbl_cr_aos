package com.gip.tablecross.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gip.tablecross.R;
import com.gip.tablecross.object.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {
	private Activity context;
	private List<Category> listCategories;

	public CategoryAdapter(Activity context, List<Category> listCategories) {
		super(context, R.layout.item_restaurant, listCategories);
		this.context = context;
		this.listCategories = listCategories;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.item_category, null);
			ViewHolder viewHolder = new ViewHolder();

			viewHolder.lblCategory = (TextView) rowView.findViewById(R.id.lblCategory);

			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		Category item = listCategories.get(position);
		holder.lblCategory.setText(item.getName());
		if (item.isChild()) {
			holder.lblCategory.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_more, 0);
		} else {
			holder.lblCategory.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		}
		return rowView;
	}

	static class ViewHolder {
		private TextView lblCategory;
	}
}