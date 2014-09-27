package com.gip.tablecross.object;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	private int point;
	private String sessionId;
	private String email;
	private String userId;
	private String shareLink;
	private String mobile;

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
		setUserId(in.readString());
		setShareLink(in.readString());
		setMobile(in.readString());
	}

	public User() {
		point = 0;
		sessionId = "";
		email = "";
		userId = "";
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
		dest.writeString(userId);
		dest.writeString(shareLink);
		dest.writeString(mobile);
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShareLink() {
		return shareLink;
	}

	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}