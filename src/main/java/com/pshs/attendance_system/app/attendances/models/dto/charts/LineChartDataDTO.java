package com.pshs.attendance_system.app.attendances.models.dto.charts;

public class LineChartDataDTO {

	private String label;
	private String data;

	public LineChartDataDTO() {
	}

	public LineChartDataDTO(String label, String data) {
		this.label = label;
		this.data = data;
	}

	public String getLabel() {
		return label;
	}

	public LineChartDataDTO setLabel(String label) {
		this.label = label;
		return this;
	}

	public String getData() {
		return data;
	}

	public LineChartDataDTO setData(String data) {
		this.data = data;
		return this;
	}
}