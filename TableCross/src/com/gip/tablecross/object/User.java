package com.gip.tablecross.object;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	private int userId;
	private String mobile;
	private int point;
	private String email;
	private String birthday;
	private int birthdayYear;
	private int birthdayMonth;
	private int birthdayDay;
	private int orderCount;
	private int totalOrder;
	private int totalPoint;
	private String shareLink;
	private String sessionId;
	private int totalUserShare;

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};

	private User(Parcel in) {
		setPoint(in.readInt());
		setSessionId(in.readString());
		setEmail(in.readString());
		setUserId(in.readInt());
		setShareLink(in.readString());
		setMobile(in.readString());
	}

	public User() {
		point = 0;
		sessionId = "";
		email = "";
		userId = 0;
		shareLink = "";
		mobile = "";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(point);
		dest.writeString(sessionId);
		dest.writeString(email);
		dest.writeInt(userId);
		dest.writeString(shareLink);
		dest.writeString(mobile);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
		try {
			String[] s = birthday.split("/");
			birthdayYear = Integer.parseInt(s[0]);
			birthdayMonth = Integer.parseInt(s[1]);
			birthdayDay = Integer.parseInt(s[2]);
		} catch (Exception e) {
		}
	}

	public String getBirthdayJapanesse(String yearText, String monthText, String dayText) {
		String m, d;
		if (birthdayMonth < 10) {
			m = "0" + birthdayMonth;
		} else {
			m = String.valueOf(birthdayMonth);
		}
		if (birthdayDay < 10) {
			d = "0" + birthdayDay;
		} else {
			d = String.valueOf(birthdayDay);
		}
		return birthdayYear + yearText + m + monthText + d + dayText;
	}

	public void setBirthday(int year, int month, int day) {
		birthdayYear = year;
		birthdayMonth = month;
		birthdayDay = day;
		String m, d;
		if (month < 10) {
			m = "0" + month;
		} else {
			m = String.valueOf(month);
		}
		if (day < 10) {
			d = "0" + day;
		} else {
			d = String.valueOf(day);
		}
		birthday = year + "/" + m + "/" + d;
	}

	public int getBirthdayYear() {
		return birthdayYear;
	}

	public int getBirthdayMonth() {
		return birthdayMonth;
	}

	public int getBirthdayDay() {
		return birthdayDay;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(int totalOrder) {
		this.totalOrder = totalOrder;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public String getShareLink() {
		return shareLink;
	}

	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getTotalUserShare() {
		return totalUserShare;
	}

	public void setTotalUserShare(int totalUserShare) {
		this.totalUserShare = totalUserShare;
	}
}