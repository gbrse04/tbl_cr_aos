package com.gip.tablecross.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.object.Notification;

public class NotificationDetailFragment extends BaseFragment {
	private TextView lblTime, lblContent, lblDescription;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notification_detail, container, false);
		initUI(view);
		return view;
	}

	private void initUI(View view) {
		lblTime = (TextView) view.findViewById(R.id.lblTime);
		lblContent = (TextView) view.findViewById(R.id.lblContent);
		lblDescription = (TextView) view.findViewById(R.id.lblDescription);
	}

	public void setData(Notification notification) {
		lblTime.setText(notification.getNotifyDate());
		lblContent.setText(notification.getNotifyShort());
		lblDescription.setText(notification.getNotifyLong());
	}
}