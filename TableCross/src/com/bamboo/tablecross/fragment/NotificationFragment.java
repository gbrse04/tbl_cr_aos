package com.bamboo.tablecross.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bamboo.tablecross.BaseFragment;
import com.bamboo.tablecross.R;
import com.bamboo.tablecross.adapter.NotificationAdapter;
import com.bamboo.tablecross.object.Notification;

public class NotificationFragment extends BaseFragment {
	private ListView lsvNotification;
	private List<Notification> listNotifications;
	private NotificationAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notification, container, false);

		lsvNotification = (ListView) view.findViewById(R.id.lsvNotification);
		listNotifications = new ArrayList<Notification>();
		listNotifications.add(new Notification("0", getString(R.string.demoTime), getString(R.string.demoContent),
				getString(R.string.demoLink)));
		listNotifications.add(new Notification("0", getString(R.string.demoTime), getString(R.string.demoContent),
				getString(R.string.demoLink)));
		listNotifications.add(new Notification("0", getString(R.string.demoTime), getString(R.string.demoContent),
				getString(R.string.demoLink)));
		listNotifications.add(new Notification("0", getString(R.string.demoTime), getString(R.string.demoContent),
				getString(R.string.demoLink)));
		listNotifications.add(new Notification("0", getString(R.string.demoTime), getString(R.string.demoContent),
				getString(R.string.demoLink)));
		listNotifications.add(new Notification("0", getString(R.string.demoTime), getString(R.string.demoContent),
				getString(R.string.demoLink)));
		listNotifications.add(new Notification("0", getString(R.string.demoTime), getString(R.string.demoContent),
				getString(R.string.demoLink)));
		listNotifications.add(new Notification("0", getString(R.string.demoTime), getString(R.string.demoContent),
				getString(R.string.demoLink)));
		listNotifications.add(new Notification("0", getString(R.string.demoTime), getString(R.string.demoContent),
				getString(R.string.demoLink)));
		listNotifications.add(new Notification("0", getString(R.string.demoTime), getString(R.string.demoContent),
				getString(R.string.demoLink)));

		adapter = new NotificationAdapter(getActivity(), listNotifications);
		lsvNotification.setAdapter(adapter);
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
		}
	}
}
