package com.gip.tablecross.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.gip.tablecross.object.Area;
import com.gip.tablecross.util.StringUtil;

/**
 * MySharedPreferences class which saves setting values
 * 
 */
public class MySharedPreferences {
	public final static String TABLE_CROSS_PREFERENCES = "TABLE_CROSS_PREFERENCES";
	private final String USER_NAME = "USER_NAME";
	private final String USER_EMAIL = "USER_EMAIL";
	private final String USER_PASSWORD = "USER_PASSWORD";
	private final String USER_LOGIN_TYPE = "USER_LOGIN_TYPE";
	private final String AREA_ID = "AREA_ID";
	private final String AREA_NAME = "AREA_NAME";
	private final String KEYWORD_SEARCH = "KEYWORD_SEARCH";
	private Context context;

	public MySharedPreferences(Context context) {
		this.context = context;
	}

	// ======================== UTILITY FUNCTIONS ========================
	public void putUserEmail(String userEmail) {
		putStringValue(USER_EMAIL, userEmail);
	}

	public String getUserEmail() {
		return getStringValue(USER_EMAIL);
	}

	public void putUserName(String userName) {
		putStringValue(USER_NAME, userName);
	}

	public String getUserName() {
		return getStringValue(USER_NAME);
	}

	public void putUserPasword(String userPassword) {
		putStringValue(USER_PASSWORD, userPassword);
	}

	public String getUserPasword() {
		return getStringValue(USER_PASSWORD);
	}

	public void clearUser() {
		putUserName("");
		putUserEmail("");
		putUserPasword("");
		putUserLoginType(0);
	}

	public void putArea(Area area) {
		try {
			putStringValue(AREA_ID, area.getAreaId());
			putStringValue(AREA_NAME, area.getAreaName());
		} catch (Exception e) {
		}
	}

	public Area getArea() {
		Area area = new Area();
		area.setAreaId(getStringValue(AREA_ID));
		area.setAreaName(getStringValue(AREA_NAME));
		return area;
	}

	public void putUserLoginType(int userLoginType) {
		putIntValue(USER_LOGIN_TYPE, userLoginType);
	}

	public int getUserLoginType() {
		return getIntValue(USER_LOGIN_TYPE);
	}

	public void addKeywordSearch(String keyword) {
		String saved = getStringValue(KEYWORD_SEARCH);
		if (StringUtil.isEmpty(saved)) {
			putStringValue(KEYWORD_SEARCH, keyword);
		} else {
			String temp[] = getStringValue(KEYWORD_SEARCH).split("#");
			for (String string : temp) {
				if (keyword.equals(string)) {
					return;
				}
			}
			putStringValue(KEYWORD_SEARCH, saved + "#" + keyword);
		}
	}

	public List<String> getListKeywordSearch() {
		String saved = getStringValue(KEYWORD_SEARCH);
		if (StringUtil.isEmpty(saved)) {
			return new ArrayList<String>();
		} else {
			return Arrays.asList(getStringValue(KEYWORD_SEARCH).split("#"));
		}
	}

	// ======================== CORE FUNCTIONS ========================
	/**
	 * Save a long integer to SharedPreferences
	 * 
	 * @param key
	 * @param n
	 */
	public void putLongValue(String key, long n) {
		// SmartLogger.log(TAG, "Set long integer value");
		SharedPreferences pref = context.getSharedPreferences(TABLE_CROSS_PREFERENCES, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putLong(key, n);
		editor.commit();
	}

	/**
	 * Read a long integer to SharedPreferences
	 * 
	 * @param key
	 * @return
	 */
	public long getLongValue(String key) {
		// SmartLogger.log(TAG, "Get long integer value");
		SharedPreferences pref = context.getSharedPreferences(TABLE_CROSS_PREFERENCES, 0);
		return pref.getLong(key, 0);
	}

	/**
	 * Save an integer to SharedPreferences
	 * 
	 * @param key
	 * @param n
	 */
	public void putIntValue(String key, int n) {
		// SmartLogger.log(TAG, "Set integer value");
		SharedPreferences pref = context.getSharedPreferences(TABLE_CROSS_PREFERENCES, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(key, n);
		editor.commit();
	}

	/**
	 * Read an integer to SharedPreferences
	 * 
	 * @param key
	 * @return
	 */
	public int getIntValue(String key) {
		// SmartLogger.log(TAG, "Get integer value");
		SharedPreferences pref = context.getSharedPreferences(TABLE_CROSS_PREFERENCES, 0);
		return pref.getInt(key, 0);
	}

	/**
	 * Save an string to SharedPreferences
	 * 
	 * @param key
	 * @param s
	 */
	public void putStringValue(String key, String s) {
		// SmartLogger.log(TAG, "Set string value");
		SharedPreferences pref = context.getSharedPreferences(TABLE_CROSS_PREFERENCES, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, s);
		editor.commit();
	}

	/**
	 * Read an string to SharedPreferences
	 * 
	 * @param key
	 * @return
	 */
	public String getStringValue(String key) {
		// SmartLogger.log(TAG, "Get string value");
		SharedPreferences pref = context.getSharedPreferences(TABLE_CROSS_PREFERENCES, 0);
		return pref.getString(key, "");
	}

	/**
	 * Read an string to SharedPreferences
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getStringValue(String key, String defaultValue) {
		// SmartLogger.log(TAG, "Get string value");
		SharedPreferences pref = context.getSharedPreferences(TABLE_CROSS_PREFERENCES, 0);
		return pref.getString(key, defaultValue);
	}

	/**
	 * Save an boolean to SharedPreferences
	 * 
	 * @param key
	 * @param s
	 */
	public void putBooleanValue(String key, boolean b) {
		// SmartLogger.log(TAG, "Set boolean value");
		SharedPreferences pref = context.getSharedPreferences(TABLE_CROSS_PREFERENCES, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(key, b);
		editor.commit();
	}

	/**
	 * Read an boolean to SharedPreferences
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBooleanValue(String key) {
		// SmartLogger.log(TAG, "Get boolean value");
		SharedPreferences pref = context.getSharedPreferences(TABLE_CROSS_PREFERENCES, 0);
		return pref.getBoolean(key, false);
	}

	/**
	 * Save an float to SharedPreferences
	 * 
	 * @param key
	 * @param s
	 */
	public void putFloatValue(String key, float f) {
		SharedPreferences pref = context.getSharedPreferences(TABLE_CROSS_PREFERENCES, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putFloat(key, f);
		editor.commit();
	}

	/**
	 * Read an float to SharedPreferences
	 * 
	 * @param key
	 * @return
	 */
	public float getFloatValue(String key) {
		SharedPreferences pref = context.getSharedPreferences(TABLE_CROSS_PREFERENCES, 0);
		return pref.getFloat(key, 0.0f);
	}
}
