package com.gip.tablecross.common;

public final class WebServiceConfig {
	// Network time out: 60s
	public static int NETWORK_TIME_OUT = 60000;

	// result code for activity result
	public static int RESULT_OK = 1;

	// ===================== DOMAIN =====================
	public static String PROTOCOL_HTTP = "http://";
	public static String PROTOCOL_HTTPS = "https://";
	public static String APP_DOMAIN = PROTOCOL_HTTP + "tablecross.jp:8081/client-api/";

	public static final String URL_GRAPH_FACEBOOK = "https://graph.facebook.com";
	public static final String URL_SHARE_LINE = "http://line.me/R/msg/text/?";

	// ===================== WEB SERVICE LINK =====================
	public static String URL_REGISTER = APP_DOMAIN + "register";
	public static String URL_LOGIN = APP_DOMAIN + "login";
	public static String URL_LOGOUT = APP_DOMAIN + "logout";
	public static String URL_CHANGE_PASSWORD = APP_DOMAIN + "changePassword";
	public static String URL_GET_AREAS = APP_DOMAIN + "getAreas";
	public static String URL_GET_USER_INFO = APP_DOMAIN + "getUserInfo";
	public static String URL_UPDATE_USER = APP_DOMAIN + "updateUser";
	public static String URL_SEARCH_RESTAURANT = APP_DOMAIN + "searchRestaurant";
	public static String URL_GET_NOTIFY = APP_DOMAIN + "getNotifyList";
	public static String URL_GET_NOTIFY_UNPUSH = APP_DOMAIN + "getNotifyUnPushList";
}