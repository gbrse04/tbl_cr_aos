package com.gip.tablecross.object;

public class Notification {
	private int id;
	private String notifyShort;
	private String notifyLong;
	private String notifyDate;
	private String status;
	private String userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
