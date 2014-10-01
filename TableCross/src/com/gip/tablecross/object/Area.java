package com.gip.tablecross.object;

public class Area {
	private String areaId;
	private String areaName;

	public Area() {
	}

	public Area(String areaId, String areaName) {
		this.areaId = areaId;
		this.areaName = areaName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Override
	public String toString() {
		return "Area [areaId=" + areaId + ", areaName=" + areaName + "]";
	}
}
