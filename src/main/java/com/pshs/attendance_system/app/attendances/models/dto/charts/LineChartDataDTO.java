package com.pshs.attendance_system.app.attendances.models.dto.charts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LineChartDataDTO {

	private String label;
	private String data;
}