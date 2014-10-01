package com.gip.tablecross.common;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.gip.tablecross.R;
import com.gip.tablecross.modelmanager.ModelManager;
import com.gip.tablecross.object.Area;
import com.littlefluffytoys.littlefluffylocationlibrary.LocationInfo;

public class GlobalValue {

	public static String accessTokenFacebook;
	public static MySharedPreferences prefs;
	public static ModelManager modelManager;
	public static LocationInfo locationInfo;
	//
	public static List<Area> listAreas;
	public static Area area;

	public static void constructor(Activity activity) {
		modelManager = new ModelManager(activity);
		locationInfo = new LocationInfo(activity);
		prefs = new MySharedPreferences(activity);
		//
		area = new Area();
		listAreas = new ArrayList<Area>();
		listAreas.add(new Area("0", activity.getString(R.string.selectAnArea)));
	}
}