package com.gip.tablecross.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.gip.tablecross.BaseFragment;
import com.gip.tablecross.R;
import com.gip.tablecross.activity.MainActivity;
import com.gip.tablecross.adapter.NotificationAdapter;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.modelmanager.ModelManagerListener;
import com.gip.tablecross.object.Notification;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.util.NetworkUtil;

public class NotificationFragment extends BaseFragment {
	private ListView lsvNotification;
	private List<Notification> listNotifications;
	private NotificationAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_notification, container, false);
		initUI(view);
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			// if (listNotifications.size() == 0) {
			getNotify();
			// }
		}
	}

	private void initUI(View view) {
		lsvNotification = (ListView) view.findViewById(R.id.lsvNotification);
		listNotifications = new ArrayList<Notification>();

		adapter = new NotificationAdapter(getActivity(), listNotifications);
		lsvNotification.setAdapter(adapter);
		lsvNotification.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (listNotifications.get(position).getRestaurantId() > 0) {
					getRestaurantInfo(listNotifications.get(position).getRestaurantId());
				}
			}
		});
	}

	private void getRestaurantInfo(int restauranId) {
		if (!NetworkUtil.isNetworkAvailable(this)) {
			showDialogNoNetwork();
		} else {
			getBaseActivity().showLoading();
			GlobalValue.modelManager.getRestaurantInfo(restauranId, new ModelManagerListener() {
				@Override
				public void onError(String message) {
					getBaseActivity().hideLoading();
				}

				@Override
				public void onSuccess(Object object, SimpleResponse simpleResponse) {
					getMainActivity().currentRestaurant = (Restaurant) object;
					getBaseActivity().hideLoading();
					goToFragment(MainActivity.RESTAURANT_DETAIL);
				}
			});
		}
	}

	private void getNotify() {
		if (!NetworkUtil.isNetworkAvailable(this)) {
			showDialogNoNetwork();
		} else {
			getBaseActivity().showLoading();
			GlobalValue.modelManager.getNotify(0, 100, new ModelManagerListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void onSuccess(Object object, SimpleResponse simpleResponse) {
					listNotifications.clear();
					listNotifications.addAll((List<Notification>) object);

					adapter.notifyDataSetChanged();
					getBaseActivity().hideLoading();
				}

				@Override
				public void onError(String message) {
					getBaseActivity().hideLoading();
				}
			});
		}
	}
}
