package com.gip.tablecross.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

	private String TAG = getClass().getSimpleName();

	public static final String APPLICATION_INSTALL_FIRST_TIME = "APPLICATION_INSTALL_FIRST_TIME";

	public static final String PREF_SETTING_USER_ID = "PREF_SETTING_USER_ID";

	public static final String PREF_SETTING_USER_NAME = "PREF_SETTING_USER_NAME";

	public static final String PREF_SETTING_USER_PASSWORD = "PREF_SETTING_USER_PASSWORD";

	public static final String PREF_SETTING_USER_ACCESS_TOKEN = "PREF_SETTING_USER_ACCESS_TOKEN";

	public static final String USE_ACTIVE_MAP = "USE_ACTIVE_MAP";

	public static final String PREF_LAST_LOCATION_LONG = "PREF_LAST_LOCATION_LONG";
	public static final String PREF_LAST_LOCATION_LAT = "PREF_LAST_LOCATION_LAT";

	public static final String PREF_DEFAULT_LANGUAGE = "PREF_DEFAULT_LANGUAGE";

	public static final String PREF_USER_IS_KICK_OUT = "PREF_USER_IS_KICK_OUT";

	public static final String USER = "USER";
	public static final String PASS = "PASS";
	public static final String TOKEN = "access_token";
	public static final String USER_ID = "USER_ID";
	public static final String FACEBOOK_ID = "FACEBOOK_ID";
	public static final String FACEBOOK_LOGIN_STEP2 = "FACEBOOK_LOGIN_STEP2";
	public static final String TWITTER_LOGIN_STEP2 = "TWITTER_LOGIN_STEP2";
	public static final String FOURSQUARE_LOGIN = "FOURSQUARE_LOGIN";
	public static final String FACEBOOK_TOKEN = "FACEBOOK_TOKEN";
	public static final String FACEBOOK_NAME = "FACEBOOK_NAME";
	public static final String FACEBOOK_FIRST_NAME = "FACEBOOK_FIRST_NAME";
	public static final String FACEBOOK_LAST_NAME = "FACEBOOK_LAST_NAME";
	public static final String FACEBOOK_BIRTHDAY = "FACEBOOK_BIRTHDAY";
	public static final String FACEBOOK_EMAIL = "FACEBOOK_EMAIL";
	public static final String TWITTER_ID = "TWITTER_ID";
	public static final String TWITTER_NAME = "TWITTER_NAME";
	public static final String TWITTER_TOKEN = "TWITTER_TOKEN";
	public static final String TWITTER_TOKEN_SECRET = "TWITTER_TOKEN_SECRET";
	public static final String FOURSQUARE_TOKEN = "FOURSQUARE_TOKEN";

	public static final String AVATAR_URL = "AVATAR_URL";
	public static final String USING_FACEBOOK_ACCOUNT = "USING_FACEBOOK_ACCOUNT";

	public static final String IS_AUTO_SCROLL_CAROUSEL = "IS_AUTO_SCROLL_CAROUSEL";
	public static final String VOTE_1 = "VOTE_1";
	public static final String VOTE_2 = "VOTE_2";
	public static final String VOTE_3 = "VOTE_3";
	public static final String VOTE_4 = "VOTE_4";
	public static final String VOTE_5 = "VOTE_5";
	public static final String VOTE_6 = "VOTE_6";

	public static final String PAYMENT = "PAYMENT";

	// ================================================================

	private Context context;

	public SharedPref(Context context) {
		this.context = context;
	}

	// ======================== UTILITY FUNCTIONS ========================

	public void setPayment() {

		putBooleanValue(PAYMENT, true);
	}

	public boolean getPayment() {
		return getBooleanValue(PAYMENT);
	}

	public void setFirstIntall() {

		putBooleanValue(APPLICATION_INSTALL_FIRST_TIME, true);
	}

	public boolean getFirstIntall() {
		return getBooleanValue(APPLICATION_INSTALL_FIRST_TIME);
	}

	public void setTwitterLoginStep2(boolean value) {

		putBooleanValue(TWITTER_LOGIN_STEP2, value);
	}

	public void setTwitterLoginStep2() {

		putBooleanValue(TWITTER_LOGIN_STEP2, true);
	}

	public boolean getTwitterLoginStep2() {
		return getBooleanValue(TWITTER_LOGIN_STEP2);
	}

	public void setFBLoginStep2(boolean value) {
		putBooleanValue(FACEBOOK_LOGIN_STEP2, value);
	}

	public void setFBLoginStep2() {
		putBooleanValue(FACEBOOK_LOGIN_STEP2, true);
	}

	public boolean getFBLoginStep2() {
		return getBooleanValue(FACEBOOK_LOGIN_STEP2);
	}

	public void setFSLoginStep2() {

		putBooleanValue(FOURSQUARE_LOGIN, true);
	}

	public boolean getFSLoginStep2() {
		return getBooleanValue(FOURSQUARE_LOGIN);
	}

	public void putUsingFacebookAccount(String usingFacebookAccount) {
		putStringValue(USING_FACEBOOK_ACCOUNT, usingFacebookAccount);
	}

	public String getUsingFacebookAccount() {
		return getStringValue(USING_FACEBOOK_ACCOUNT);
	}

	public String getAvatar() {
		return getStringValue(AVATAR_URL);
	}

	public void setAvatarUrl(String avatar) {
		putStringValue(AVATAR_URL, avatar);
	}

	public void putUser(String strUser) {
		putStringValue(USER, strUser);
	}

	public String getUser() {
		return getStringValue(USER);
	}

	public void putAccessToken(String token) {
		putStringValue(TOKEN, token);
	}

	public String getAcccessToken() {
		return getStringValue(TOKEN);
	}

	public void putPass(String strPass) {
		putStringValue(PASS, strPass);
	}

	public String getPass() {
		return getStringValue(PASS);
	}

	public void putUserIdPush(String strUserId) {
		putStringValue(USER_ID, strUserId);
	}

	public String getUserIdPush() {
		return getStringValue(USER_ID);
	}

	public void putFacebookId(String strFacebookId) {
		putStringValue(FACEBOOK_ID, strFacebookId);
	}

	public String getFacebookId() {
		return getStringValue(FACEBOOK_ID);
	}

	public void putFacebookToken(String strFacebookToken) {
		putStringValue(FACEBOOK_TOKEN, strFacebookToken);
	}

	public String getFacebookToken() {
		return getStringValue(FACEBOOK_TOKEN);
	}

	public void putFacebookName(String strName) {
		putStringValue(FACEBOOK_NAME, strName);
	}

	public String getFacebookName() {
		return getStringValue(FACEBOOK_NAME);
	}

	public void putFacebookFirstName(String strFirstName) {
		putStringValue(FACEBOOK_FIRST_NAME, strFirstName);
	}

	public String getFacebookFirstName() {
		return getStringValue(FACEBOOK_FIRST_NAME);
	}

	public void putFacebookLastName(String strLastName) {
		putStringValue(FACEBOOK_LAST_NAME, strLastName);
	}

	public String getFacebookLastName() {
		return getStringValue(FACEBOOK_LAST_NAME);
	}

	public void putFacebookEmail(String strEmail) {
		putStringValue(FACEBOOK_EMAIL, strEmail);
	}

	public String getFacebookEmail() {
		return getStringValue(FACEBOOK_EMAIL);
	}

	public void putFacebookAvatar(String strEmail) {
		putStringValue(FACEBOOK_EMAIL, strEmail);
	}

	public String getFacebookAvatar() {
		return getStringValue(FACEBOOK_EMAIL);
	}

	public String getTwitterID() {
		return getStringValue(TWITTER_ID);
	}

	public String getTwitterName() {
		return getStringValue(TWITTER_NAME);
	}

	public String getTwitterToken() {
		return getStringValue(TWITTER_TOKEN);
	}

	public void setTwitterID(String id) {
		putStringValue(TWITTER_ID, id);
	}

	public void setTwitterName(String name) {
		putStringValue(TWITTER_NAME, name);
	}

	public void setTwitterToken(String token) {
		putStringValue(TWITTER_TOKEN, token);
	}

	public void setTwitterTokenSecret(String tokenSecret) {
		putStringValue(TWITTER_TOKEN_SECRET, tokenSecret);
	}

	public String getTwitterTokenSecret() {
		return getStringValue(TWITTER_TOKEN_SECRET);
	}

	public String getFourSquareToken(String token) {

		return getStringValue(FOURSQUARE_TOKEN);
	}

	public void setFourSquareToken(String token) {
		putStringValue(FOURSQUARE_TOKEN, token);
	}

	// ======================== CORE FUNCTIONS ========================

	/**
	 * Save a long integer to SharedPreferences
	 * 
	 * @param key
	 * @param n
	 */
	public void putLongValue(String key, long n) {
		// SmartLog.log(TAG, "Set long integer value");
		SharedPreferences pref = context.getSharedPreferences(MySharedPreferences.TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		// SmartLog.log(TAG, "Get long integer value");
		SharedPreferences pref = context.getSharedPreferences(MySharedPreferences.TRUC_TIEP_BONG_DA_PREFERENCES, 0);
		return pref.getLong(key, 0);
	}

	/**
	 * Save an integer to SharedPreferences
	 * 
	 * @param key
	 * @param n
	 */
	public void putIntValue(String key, int n) {
		// SmartLog.log(TAG, "Set integer value");
		SharedPreferences pref = context.getSharedPreferences(MySharedPreferences.TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		// SmartLog.log(TAG, "Get integer value");
		SharedPreferences pref = context.getSharedPreferences(MySharedPreferences.TRUC_TIEP_BONG_DA_PREFERENCES, 0);
		return pref.getInt(key, 0);
	}

	/**
	 * Save an string to SharedPreferences
	 * 
	 * @param key
	 * @param s
	 */
	public void putStringValue(String key, String s) {
		// SmartLog.log(TAG, "Set string value");
		SharedPreferences pref = context.getSharedPreferences(MySharedPreferences.TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		// SmartLog.log(TAG, "Get string value");
		SharedPreferences pref = context.getSharedPreferences(MySharedPreferences.TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		// SmartLog.log(TAG, "Get string value");
		SharedPreferences pref = context.getSharedPreferences(MySharedPreferences.TRUC_TIEP_BONG_DA_PREFERENCES, 0);
		return pref.getString(key, defaultValue);
	}

	/**
	 * Save an boolean to SharedPreferences
	 * 
	 * @param key
	 * @param s
	 */
	public void putBooleanValue(String key, Boolean b) {
		// SmartLog.log(TAG, "Set boolean value");
		SharedPreferences pref = context.getSharedPreferences(MySharedPreferences.TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		// SmartLog.log(TAG, "Get boolean value");
		SharedPreferences pref = context.getSharedPreferences(MySharedPreferences.TRUC_TIEP_BONG_DA_PREFERENCES, 0);
		return pref.getBoolean(key, false);
	}

	/**
	 * Save an float to SharedPreferences
	 * 
	 * @param key
	 * @param s
	 */
	public void putFloatValue(String key, float f) {
		SharedPreferences pref = context.getSharedPreferences(MySharedPreferences.TRUC_TIEP_BONG_DA_PREFERENCES, 0);
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
		SharedPreferences pref = context.getSharedPreferences(MySharedPreferences.TRUC_TIEP_BONG_DA_PREFERENCES, 0);
		return pref.getFloat(key, 0.0f);
	}
}
