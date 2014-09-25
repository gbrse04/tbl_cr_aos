package com.bamboo.tablecross.common;

import android.app.Activity;

import com.bamboo.tablecross.modelmanager.ModelManager;
import com.bamboo.tablecross.object.Area;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;

public class GlobalValue {
	public static Area area;
	public static String accessTokenFacebook;
	public static SharedPref prefs;
	public static ModelManager modelManager;
	public static LocationInfo locationInfo;

	public static void constructor(Activity activity) {
		modelManager = new ModelManager(activity);
		locationInfo = new LocationInfo(activity);
		prefs = new SharedPref(activity);
	}
}
