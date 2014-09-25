package com.bamboo.tablecross.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bamboo.tablecross.R;
import com.bamboo.tablecross.object.Notification;

public class NotificationAdapter extends ArrayAdapter<Notification> {
	private Activity context;
	private List<Notification> listNotifications;

	public NotificationAdapter(Activity context, List<Notification> listNotifications) {
		super(context, R.layout.item_notification, listNotifications);
		this.context = context;
		this.listNotifications = listNotifications;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.item_notification, null);
			ViewHolder viewHolder = new ViewHolder();

			viewHolder.lblTime = (TextView) rowView.findViewById(R.id.lblTime);
			viewHolder.lblContent = (TextView) rowView.findViewById(R.id.lblContent);
			viewHolder.lblLink = (TextView) rowView.findViewById(R.id.lblLink);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		Notification item = listNotifications.get(position);
		holder.lblTime.setText(item.getTime());
		holder.lblContent.setText(item.getContent());
		holder.lblLink.setText(item.getLink());

		return rowView;
	}

	static class ViewHolder {
		private TextView lblTime;
		private TextView lblContent;
		private TextView lblLink;
	}
}