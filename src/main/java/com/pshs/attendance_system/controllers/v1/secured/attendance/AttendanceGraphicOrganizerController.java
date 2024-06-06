package com.pshs.attendance_system.controllers.v1.secured.attendance;

import com.pshs.attendance_system.dto.LineChartDTO;
import com.pshs.attendance_system.entities.range.DateRange;
import com.pshs.attendance_system.enums.Status;
import com.pshs.attendance_system.services.AttendanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendances/graphic-organizers")
@Tag(name = "Attendance Graphic Organizer", description = "Attendance Graphic Organizer API")
public class AttendanceGraphicOrganizerController {

	private final AttendanceService attendanceService;

	public AttendanceGraphicOrganizerController(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}

	@PostMapping("/line-chart")
	public LineChartDTO lineChartData(@RequestParam Status status, @RequestBody DateRange dateRange) {
		List<String> labels = new ArrayList<>();
		List<Integer> data = new ArrayList<>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Get the data from the service
		// Loop the date range
		for (LocalDate date = dateRange.getStartDate(); date.isBefore(dateRange.getEndDate()); date = date.plusDays(1)) {
			// Get the attendance data.
			// Get the attendance count to the labels and data
			labels.add(dateTimeFormatter.format(date));
			data.add(attendanceService.getAttendanceCountByDateAndStatus(date, status));
		}

		return new LineChartDTO(labels, data);
	}

	@PostMapping("/sections/{sectionId}/line-chart")
	public LineChartDTO lineChartDataBySection(@RequestParam Status status, @RequestBody DateRange dateRange, @PathVariable Integer sectionId) {
		List<String> labels = new ArrayList<>();
		List<Integer> data = new ArrayList<>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Get the data from the service
		// Loop the date range
		for (LocalDate date = dateRange.getStartDate(); date.isBefore(dateRange.getEndDate()); date = date.plusDays(1)) {
			// Get the attendance data.
			// Get the attendance count to the labels and data
			labels.add(dateTimeFormatter.format(date));
			data.add(attendanceService.countAttendanceInSection(sectionId, date, status));
		}

		return new LineChartDTO(labels, data);
	}

	@PostMapping("/students/{studentId}/line-chart")
	public LineChartDTO lineChartDataByStudent(@RequestParam Status status, @RequestBody DateRange dateRange, @PathVariable Long studentId) {
		List<String> labels = new ArrayList<>();
		List<Integer> data = new ArrayList<>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Get the data from the service
		// Loop the date range
		for (LocalDate date = dateRange.getStartDate(); date.isBefore(dateRange.getEndDate()); date = date.plusDays(1)) {
			// Get the attendance data.
			// Get the attendance count to the labels and data
			labels.add(dateTimeFormatter.format(date));
			data.add(attendanceService.countStudentAttendanceByStatusAndDate(studentId, status, date));
		}

		return new LineChartDTO(labels, data);
	}
}