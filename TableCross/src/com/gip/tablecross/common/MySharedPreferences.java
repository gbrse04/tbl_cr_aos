package com.gip.tablecross.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * MySharedPreferences class which saves setting values
 * 
 */
public class MySharedPreferences {
	public final static String TRUC_TIEP_BONG_DA_PREFERENCES = "TRUC_TIEP_BONG_DA_PREFERENCES";
	private final String USER_ID = "USER_ID";
	private final String WEIGHT = "WEIGHT";
	private final String HIGHT = "HIGHT";
	private final String TYPE_BLOOD = "TYPE_BLOOD";
	private final String ACCOUNT_ID = "ACCOUNT_ID";
	private final String IS_GET_USER_VERSION_1_0_1 = "IS_GET_USER_VERSION_1_0_1";
	private Context context;

	public MySharedPreferences(Context context) {
		this.context = context;
	}

	// ======================== UTILITY FUNCTIONS ========================
	public void putUserId(String siteId) {
		putStringValue(USER_ID, siteId);
	}

	public String getUserId() {
		return getStringValue(USER_ID);
	}

	public void putWeight(float w) {
		putFloatValue(WEIGHT, w);
	}

	public float getWeight() {
		return getFloatValue(WEIGHT);
	}

	public void putHight(float h) {
		putFloatValue(HIGHT, h);
	}

	public float getHight() {
		return getFloatValue(HIGHT);
	}

	public void putTypeBlood(int type) {
		putIntValue(TYPE_BLOOD, type);
	}

	public int getTypeBlood() {
		return getIntValue(TYPE_BLOOD);
	}

	public void putAccountId(String accountId) {
		putStringValue(ACCOUNT_ID, accountId);
	}

	public String getAccountId() {
		return getStringValue(ACCOUNT_ID);
	}

	public void putGetUserVersion101() {
		putBooleanValue(IS_GET_USER_VERSION_1_0_1, true);
	}

	public boolean isGetUserVersion101() {
		return getBooleanValue(IS_GET_USER_VERSION_1_0_1);
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
		SharedPreferences pref = context.getSharedPreferences(TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		SharedPreferences pref = context.getSharedPreferences(TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		SharedPreferences pref = context.getSharedPreferences(TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		SharedPreferences pref = context.getSharedPreferences(TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		SharedPreferences pref = context.getSharedPreferences(TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		SharedPreferences pref = context.getSharedPreferences(TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		SharedPreferences pref = context.getSharedPreferences(TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		SharedPreferences pref = context.getSharedPreferences(TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		SharedPreferences pref = context.getSharedPreferences(TRUC_TIEP_BONG_DA_PREFERENCES, 0);
		return pref.getBoolean(key, false);
	}

	/**
	 * Save an float to SharedPreferences
	 * 
	 * @param key
	 * @param s
	 */
	public void putFloatValue(String key, float f) {
		SharedPreferences pref = context.getSharedPreferences(TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		SharedPreferences pref = context.getSharedPreferences(TRUC_TIEP_BONG_DA_PREFERENCES, 0);
		return pref.getFloat(key, 0.0f);
	}
}
