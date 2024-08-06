package com.pshs.attendance_system.models.dto.charts;

import java.util.List;

public class LineChartDTO {

	private List<String> labels;
	private List<Integer> data;

	public LineChartDTO(List<String> labels, List<Integer> data) {
		this.labels = labels;
		this.data = data;
	}

	public List<String> getLabels() {
		return labels;
	}

	public LineChartDTO setLabels(List<String> labels) {
		this.labels = labels;
		return this;
	}

	public List<Integer> getData() {
		return data;
	}

	public LineChartDTO setData(List<Integer> data) {
		this.data = data;
		return this;
	}
}