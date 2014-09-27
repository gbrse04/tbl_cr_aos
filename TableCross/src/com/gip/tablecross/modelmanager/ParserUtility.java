package com.gip.tablecross.modelmanager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gip.tablecross.object.Area;
import com.gip.tablecross.object.Notification;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.object.SimpleResponse;

public class ParserUtility {
	public static SimpleResponse parserSimpleResponse(JSONObject object) {
		SimpleResponse simpleResponse = new SimpleResponse();
		simpleResponse.setSuccess(getStringValue(object, "success"));
		simpleResponse.setErrorCode(getStringValue(object, "errorCode"));
		simpleResponse.setErrorMess(getStringValue(object, "errorMess"));
		return simpleResponse;
	}

	public static List<Area> parserListAreas(JSONObject object) {
		List<Area> listAreas = new ArrayList<Area>();
		try {
			JSONArray arr = object.getJSONArray("items");
			if (arr != null && arr.length() > 0) {
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = arr.getJSONObject(i);
					Area area = new Area();
					area.setAreaId(getStringValue(obj, "areaId"));
					area.setAreaName(getStringValue(obj, "areaName"));

					listAreas.add(area);
				}
			}
		} catch (JSONException e) {
		}
		return listAreas;
	}

	public static List<Restaurant> parserListRestaurants(JSONObject object) {
		List<Restaurant> listRestaurants = new ArrayList<Restaurant>();
		try {
			JSONArray arr = object.getJSONArray("items");
			if (arr != null && arr.length() > 0) {
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = arr.getJSONObject(i);
					Restaurant restaurant = new Restaurant();
					restaurant.setRestaurantId(getIntValue(obj, "restaurantId"));
					restaurant.setRestaurantName(getStringValue(obj, "restaurantName"));
					restaurant.setAddress(getStringValue(obj, "address"));
					restaurant.setImageUrl(getStringValue(obj, "imageUrl"));
					restaurant.setWebsite(getStringValue(obj, "website"));
					restaurant.setLatitude(getDoubleValue(obj, "latitude"));
					restaurant.setLongitude(getDoubleValue(obj, "longitude"));
					restaurant.setShareLink(getStringValue(obj, "shareLink"));
					restaurant.setDescription(getStringValue(obj, "description"));
					restaurant.setPhone(getStringValue(obj, "phone"));
					restaurant.setEmail(getStringValue(obj, "email"));
					restaurant.setOrderWebUrl(getStringValue(obj, "orderWebUrl"));

					listRestaurants.add(restaurant);
				}
			}
		} catch (JSONException e) {
		}
		return listRestaurants;
	}

	public static List<Notification> parserListNotifications(JSONObject object) {
		List<Notification> listNotifications = new ArrayList<Notification>();
		try {
			JSONArray arr = object.getJSONArray("items");
			if (arr != null && arr.length() > 0) {
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = arr.getJSONObject(i);
					Notification restaurant = new Notification();
					restaurant.setId(getIntValue(obj, "id"));
					restaurant.setNotifyShort(getStringValue(obj, "notifyShort"));
					restaurant.setNotifyLong(getStringValue(obj, "notifyLong"));
					restaurant.setNotifyDate(getStringValue(obj, "notifyDate"));
					restaurant.setStatus(getStringValue(obj, "status"));
					restaurant.setUserId(getStringValue(obj, "userId"));

					listNotifications.add(restaurant);
				}
			}
		} catch (JSONException e) {
		}
		return listNotifications;
	}

	// Core function. Please don't change it
	public static String getStringValue(JSONObject obj, String key) {
		try {
			return obj.isNull(key) ? "" : obj.getString(key);
		} catch (JSONException e) {
			return "";
		}
	}

	public static long getLongValue(JSONObject obj, String key) {
		try {
			return obj.isNull(key) ? 0L : obj.getLong(key);
		} catch (JSONException e) {
			return 0L;
		}
	}

	public static int getIntValue(JSONObject obj, String key) {
		try {
			return obj.isNull(key) ? 0 : obj.getInt(key);
		} catch (JSONException e) {
			return 0;
		}
	}

	public static Double getDoubleValue(JSONObject obj, String key) {
		double d = 0.0;
		try {
			return obj.isNull(key) ? d : obj.getDouble(key);
		} catch (JSONException e) {
			return d;
		}
	}

	public static boolean getBooleanValue(JSONObject obj, String key) {
		try {
			return obj.isNull(key) ? false : obj.getBoolean(key);
		} catch (JSONException e) {
			return false;
		}
	}
}