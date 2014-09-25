package com.bamboo.tablecross;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.StrictMode;
import android.telephony.TelephonyManager;

public class PacketUtility {
	private static PacketUtility self;

	/**
	 * Get package name
	 * 
	 * @return Main package name
	 */
	public static String getPacketName() {
		if (self == null) {
			self = new PacketUtility();
		}
		return self.getClass().getPackage().getName();
	}

	/**
	 * Get version name of application
	 * 
	 * @param context
	 * @return Version name
	 */
	public static String getVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Get imei of device
	 * 
	 * @param context
	 * @return imei
	 */
	public static String getImei(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	public static void enableStrictMode() {
//		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
//				.detectNetwork() // or .detectAll() for all detectable problems
//				.penaltyLog().build());
//		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
//				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
	}
}
