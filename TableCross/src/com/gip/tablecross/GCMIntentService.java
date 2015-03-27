package com.gip.tablecross;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import com.gip.tablecross.activity.MainActivity;
import com.gip.tablecross.common.GlobalValue;
import com.gip.tablecross.common.MySharedPreferences;
import com.gip.tablecross.util.Logger;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	public static final String SENDER_ID = "737419960017";
	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super(SENDER_ID);
	}

	@SuppressWarnings("deprecation")
	private void generateNotification(Context context, String message) {
		String title = context.getString(R.string.app_name);
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher, message, when);
		Intent notificationIntent = new Intent(context, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);

		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(1000);
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		Logger.e(TAG, "onError: " + arg1);
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		Logger.d(TAG, "RECIEVED A MESSAGE");
		// Get the data from intent and send to notificaion bar
		generateNotification(arg0, arg1.getStringExtra("message"));
	}

	@Override
	protected void onRegistered(Context arg0, String arg1) {
		Logger.e(TAG, "onRegistered ID: " + arg1);

		try {
			if (GlobalValue.prefs == null) {
				GlobalValue.prefs = new MySharedPreferences(this);
			}
		} catch (Exception e) {
			Logger.e(TAG, "ERROR");
		}

		if (GlobalValue.prefs != null) {
			String key = GlobalValue.prefs.getNotificationKey();
			if (key.equals("")) {
				GlobalValue.prefs.putNotificationKey(arg1);
			}
		}
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Logger.e(TAG, "onUnregistered: " + arg1);
	}
}