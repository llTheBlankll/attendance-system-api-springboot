package com.pshs.attendance_system.dto.charts;

import java.util.List;

public class RealTimeLineChartDTO {
	private List<String> labels;
	private List<LineChartDataDTO> datasets;

	public RealTimeLineChartDTO() {
	}

	public RealTimeLineChartDTO(List<String> labels, List<LineChartDataDTO> datasets) {
		this.labels = labels;
		this.datasets = datasets;
	}

	public List<String> getLabels() {
		return labels;
	}

	public RealTimeLineChartDTO setLabels(List<String> labels) {
		this.labels = labels;
		return this;
	}

	public List<LineChartDataDTO> getDatasets() {
		return datasets;
	}

	public RealTimeLineChartDTO setDatasets(List<LineChartDataDTO> datasets) {
		this.datasets = datasets;
		return this;
	}
}