package com.gip.tablecross.fragment;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gip.tablecross.R;
import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.object.Notification;

public class NotificationDetailFragment extends BaseFragment {
	private TextView lblTime, lblContent, lblLink, lblDescription;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notification_detail, container, false);
		initUI(view);
		return view;
	}

	private void initUI(View view) {
		lblTime = (TextView) view.findViewById(R.id.lblTime);
		lblContent = (TextView) view.findViewById(R.id.lblContent);
		lblLink = (TextView) view.findViewById(R.id.lblLink);
		lblDescription = (TextView) view.findViewById(R.id.lblDescription);
	}

	public void setData(Notification notification) {
		SpannableString content = new SpannableString(notification.getNotifyShort());
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

		lblTime.setText(notification.getNotifyDate());
		lblContent.setText(notification.getNotifyShort());
		lblLink.setText(content);
		lblDescription.setText(notification.getNotifyLong());
	}
}
