package com.gip.tablecross.modelmanager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gip.tablecross.object.Area;
import com.gip.tablecross.object.Category;
import com.gip.tablecross.object.Image;
import com.gip.tablecross.object.Notification;
import com.gip.tablecross.object.Restaurant;
import com.gip.tablecross.object.SimpleResponse;
import com.gip.tablecross.object.User;

public class ParserUtility {
	public static SimpleResponse parserSimpleResponse(JSONObject object) {
		SimpleResponse simpleResponse = new SimpleResponse();
		simpleResponse.setSuccess(getBooleanValue(object, "success"));
		simpleResponse.setErrorCode(getIntValue(object, "errorCode"));
		simpleResponse.setErrorMess(getStringValue(object, "errorMess"));
		return simpleResponse;
	}

	public static User parseUserFacebook(String json) {
		User user = new User();
		try {
			JSONObject object = new JSONObject(json);
			user.setEmail(getStringValue(object, "email"));
			user.setSurnameKanji(getStringValue(object, "first_name"));
			user.setLastnameKanji(getStringValue(object, "last_name"));
			user.setNameKanji(getStringValue(object, "name"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return user;
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

	public static List<Category> parserListCategories(JSONObject object) {
		List<Category> listCategories = new ArrayList<Category>();
		try {
			JSONArray arr = object.getJSONArray("items");
			if (arr != null && arr.length() > 0) {
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = arr.getJSONObject(i);
					Category category = new Category();
					category.setId(getIntValue(obj, "id"));
					category.setName(getStringValue(obj, "name"));
					category.setCode(getStringValue(obj, "code"));
					category.setChild(getIntValue(obj, "isChild"));
					listCategories.add(category);
				}
			}
		} catch (JSONException e) {
		}
		return listCategories;
	}

	public static List<Image> parserListImages(JSONObject object) {
		List<Image> listImages = new ArrayList<Image>();
		try {
			JSONArray arr = object.getJSONArray("items");
			if (arr != null && arr.length() > 0) {
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = arr.getJSONObject(i);
					Image image = new Image();
					image.setId(getIntValue(obj, "id"));
					image.setRestaurantId(getStringValue(obj, "restaurantId"));
					image.setImageUrl(getStringValue(obj, "imageUrl"));
					listImages.add(image);
				}
			}
		} catch (JSONException e) {
		}
		return listImages;
	}

	public static User parserUser(JSONObject object) {
		User user = new User();
		user.setUserId(getIntValue(object, "userId"));
		user.setMobile(getStringValue(object, "mobile"));
		user.setPoint(getIntValue(object, "point"));
		user.setEmail(getStringValue(object, "email"));
		user.setBirthday(getStringValue(object, "birthday"));
		user.setOrderCount(getIntValue(object, "orderCount"));
		user.setTotalOrder(getIntValue(object, "totalOrder"));
		user.setTotalPoint(getIntValue(object, "totalPoint"));
		user.setShareLink(getStringValue(object, "shareLink"));
		user.setSessionId(getStringValue(object, "sessionId"));
		user.setTotalUserShare(getIntValue(object, "totalUserShare"));
		return user;
	}

	public static List<Restaurant> parserListRestaurants(JSONObject object) {
		List<Restaurant> listRestaurants = new ArrayList<Restaurant>();
		try {
			JSONArray arr = object.getJSONArray("items");
			if (arr != null && arr.length() > 0) {
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = arr.getJSONObject(i);
					listRestaurants.add(parserRestaurant(obj));
				}
			}
		} catch (JSONException e) {
		}
		return listRestaurants;
	}

	public static Restaurant parserRestaurantWithKey(JSONObject obj) {
		try {
			return parserRestaurant(obj.getJSONObject("restaurant"));
		} catch (JSONException e) {
			return new Restaurant();
		}
	}

	public static Restaurant parserRestaurant(JSONObject obj) {
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
		restaurant.setOrderCount(getIntValue(obj, "orderCount"));
		restaurant.setPoint(getIntValue(obj, "point"));
		restaurant.setShortDescription(getStringValue(obj, "shortDescription"));
		restaurant.setOrderDate(getStringValue(obj, "orderDate"));
		return restaurant;
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
