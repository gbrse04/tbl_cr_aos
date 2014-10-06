package com.gip.tablecross.object;

public class Category {
	private int id;
	private String name;
	private String code;
	private boolean isChild;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isChild() {
		return isChild;
	}

	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}

	public void setChild(int child) {
		isChild = (child == 0);
	}
}
