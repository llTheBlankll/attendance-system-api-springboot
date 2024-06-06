package com.pshs.attendance_system.controllers.v1.secured.attendance;

import com.pshs.attendance_system.services.AttendanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendances/statistics")
@Tag(name = "Attendance Statistics", description = "Attendance Statistics API")
public class AttendanceStatisticsController {

	private final AttendanceService attendanceService;

	public AttendanceStatisticsController(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}

}