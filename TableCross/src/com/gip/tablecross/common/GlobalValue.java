package com.gip.tablecross.common;

import android.app.Activity;

import com.gip.tablecross.modelmanager.ModelManager;
import com.gip.tablecross.object.Area;
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
		area = new Area();
	}
}