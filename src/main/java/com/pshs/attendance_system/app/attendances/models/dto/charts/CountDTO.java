package com.pshs.attendance_system.app.attendances.models.dto.charts;

public class CountDTO {

	private Integer count;
	private String type;

	public CountDTO(Integer count, String type) {
		this.count = count;
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public CountDTO setCount(Integer count) {
		this.count = count;
		return this;
	}

	public String getType() {
		return type;
	}

	public CountDTO setType(String type) {
		this.type = type;
		return this;
	}
}