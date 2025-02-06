package com.pshs.attendance_system.app.attendances.models.dto.charts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RealTimeLineChartDTO {
	private List<String> labels;
	private List<LineChartDataDTO> datasets;
}