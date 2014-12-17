package com.gip.tablecross.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gip.tablecross.R;
import com.gip.tablecross.object.Notification;
import com.gip.tablecross.util.Logger;

public class NotificationAdapter extends ArrayAdapter<Notification> {
	private Activity context;
	private List<Notification> listNotifications;
	private SpannableString stringMore;

	public NotificationAdapter(Activity context, List<Notification> listNotifications) {
		super(context, R.layout.item_notification, listNotifications);
		this.context = context;
		this.listNotifications = listNotifications;
		stringMore = new SpannableString(context.getString(R.string.more));
		stringMore.setSpan(new UnderlineSpan(), 0, stringMore.length(), 0);
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

		Logger.d("", "Notification: " + item.getRestaurantId());

		holder.lblTime.setText(item.getNotifyDate());
		holder.lblContent.setText(item.getNotifyShort());
		if (item.getRestaurantId() == 0) {
			holder.lblLink.setVisibility(View.GONE);
		} else {
			holder.lblLink.setText(stringMore);
		}

		return rowView;
	}

	static class ViewHolder {
		private TextView lblTime;
		private TextView lblContent;
		private TextView lblLink;
	}
}