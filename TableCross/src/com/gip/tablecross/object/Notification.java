package com.gip.tablecross.object;

public class Notification {
	private int id;
	private int restaurantId;
	private int userId;
	private String notifyShort;
	private String notifyLong;
	private String notifyDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNotifyShort() {
		return notifyShort;
	}

	public void setNotifyShort(String notifyShort) {
		this.notifyShort = notifyShort;
	}

	public String getNotifyLong() {
		return notifyLong;
	}

	public void setNotifyLong(String notifyLong) {
		this.notifyLong = notifyLong;
	}

	public String getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(String notifyDate) {
		this.notifyDate = notifyDate;
	}
}
