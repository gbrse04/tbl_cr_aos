package com.bamboo.tablecross.object;

public class Notification {
	private String id;
	private String time;
	private String content;
	private String link;

	public Notification(String id, String time, String content, String link) {
		super();
		this.id = id;
		this.time = time;
		this.content = content;
		this.link = link;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
