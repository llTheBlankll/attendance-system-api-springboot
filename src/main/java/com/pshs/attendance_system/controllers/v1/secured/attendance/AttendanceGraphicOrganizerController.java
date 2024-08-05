package com.pshs.attendance_system.controllers.v1.secured.attendance;

import com.pshs.attendance_system.dto.MessageResponse;
import com.pshs.attendance_system.dto.charts.LineChartDTO;
import com.pshs.attendance_system.dto.charts.LineChartDataDTO;
import com.pshs.attendance_system.dto.charts.RealTimeLineChartDTO;
import com.pshs.attendance_system.entities.Attendance;
import com.pshs.attendance_system.entities.range.DateRange;
import com.pshs.attendance_system.enums.AttendanceStatus;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.services.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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
	@Operation(
		summary = "Get the total count of attendance by AttendanceStatus and Date Range",
		description = "Get All of the Attendances count",
		method = "POST",
		parameters = {
			@Parameter(name = "attendanceStatus", description = "AttendanceStatus", required = true),
		}
	)
	public LineChartDTO lineChartData(@RequestParam AttendanceStatus attendanceStatus, @RequestBody DateRange dateRange) {
		List<String> labels = new ArrayList<>();
		List<Integer> data = new ArrayList<>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Get the data from the service
		// Loop the date range
		for (LocalDate date = dateRange.getStartDate(); date.isBefore(dateRange.getEndDate()); date = date.plusDays(1)) {
			// Get the attendance data.
			// Get the attendance count to the labels and data
			labels.add(dateTimeFormatter.format(date));
			data.add(attendanceService.getAttendanceCountByDateAndStatus(date, attendanceStatus));
		}

		return new LineChartDTO(labels, data);
	}

	@PostMapping("/sections/{sectionId}/line-chart")
	public LineChartDTO lineChartDataBySection(@RequestParam AttendanceStatus attendanceStatus, @RequestBody DateRange dateRange, @PathVariable Integer sectionId) {
		List<String> labels = new ArrayList<>();
		List<Integer> data = new ArrayList<>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Get the data from the service
		// Loop the date range
		for (LocalDate date = dateRange.getStartDate(); date.isBefore(dateRange.getEndDate()); date = date.plusDays(1)) {
			// Get the attendance data.
			// Get the attendance count to the labels and data
			labels.add(dateTimeFormatter.format(date));
			data.add(attendanceService.countAttendanceInSection(sectionId, date, attendanceStatus));
		}

		return new LineChartDTO(labels, data);
	}

	@PostMapping("/students/{studentId}/line-chart")
	public LineChartDTO lineChartDataByStudent(@RequestParam AttendanceStatus attendanceStatus, @RequestBody DateRange dateRange, @PathVariable Long studentId) {
		List<String> labels = new ArrayList<>();
		List<Integer> data = new ArrayList<>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Get the data from the service
		// Loop the date range
		for (LocalDate date = dateRange.getStartDate(); date.isBefore(dateRange.getEndDate()); date = date.plusDays(1)) {
			// Get the attendance data.
			// Get the attendance count to the labels and data
			labels.add(dateTimeFormatter.format(date));
			data.add(attendanceService.countStudentAttendanceByStatusAndDate(studentId, attendanceStatus, date));
		}

		return new LineChartDTO(labels, data);
	}

	@PostMapping("/real-time/line-chart")
	public ResponseEntity<?> realTimeLineChartData(@RequestParam LocalDate date) {
		if (date == null) {
			return ResponseEntity.badRequest().body(new MessageResponse(
				"Date is required.",
				ExecutionStatus.INVALID
			));
		}

		List<String> labels = new ArrayList<>();
		List<LineChartDataDTO> lineChartDataDTOs = new ArrayList<>();

		// Get all attendances on that date first.
		List<Attendance> attendances = attendanceService.getAllAttendancesByDate(date);

		// Loop the attendance and get the time
		for (Attendance attendance : attendances) {
			// Get the time-in
			// Get the attendance counts to the labels and data
			// Check for absenting first
			if (attendance.getTimeOut() == null && attendance.getTime() == null) {
				continue;
			}

			lineChartDataDTOs.add(new LineChartDataDTO(
				AttendanceStatus.ON_TIME.name(),
				attendance.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
			);
			labels.add(attendance.getTime().toString());

			lineChartDataDTOs.add(new LineChartDataDTO(
				AttendanceStatus.SIGNED_OUT.name(),
				attendance.getTimeOut().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
			);
			labels.add(attendance.getTimeOut().toString());
		}

		return ResponseEntity.ok(
			new RealTimeLineChartDTO(
				labels, lineChartDataDTOs
			)
		);
	}

	@PostMapping("/real-time/sections/{sectionId}/line-chart")
	public ResponseEntity<?> realTimeLineChartDataBySection(@RequestParam LocalDate date, @PathVariable Integer sectionId) {
		if (sectionId == null) {
			return ResponseEntity.badRequest().body(new MessageResponse(
				"Section ID is required.",
				ExecutionStatus.INVALID
			));
		} else if (date == null) {
			return ResponseEntity.badRequest().body(new MessageResponse(
				"Date is required.",
				ExecutionStatus.INVALID
			));
		}

		List<String> labels = new ArrayList<>();
		List<LineChartDataDTO> lineChartDataDTOs = new ArrayList<>();

		// Get all attendances on that date first.
		List<Attendance> attendances = attendanceService.getAllAttendancesByDateAndSection(date, sectionId);

		// Sort attendances by time and timeOut first
		List<Attendance> sortedAttendance = attendances.stream().sorted((a, b) -> {
			if (a.getTime() == null || b.getTime() == null) {
				return 0;
			}

			return a.getTime().compareTo(b.getTime());
		}).sorted((a, b) -> {
			if (a.getTimeOut() == null || b.getTimeOut() == null) {
				return 0;
			}

			return a.getTimeOut().compareTo(b.getTimeOut());
		}).toList();

		// Loop the attendance and get the time
		for (Attendance attendance : sortedAttendance) {
			// Get the time-in
			// Get the attendance counts to the labels and data
			// Check for absenting first
			if (attendance.getTimeOut() == null && attendance.getTime() == null) {
				continue;
			}

			lineChartDataDTOs.add(new LineChartDataDTO(
				attendance.getStatus().name(),
				attendance.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
			);
			labels.add(attendance.getTime().toString());
		}

		for (Attendance attendance : sortedAttendance) {
			// Get the time-in
			// Get the attendance counts to the labels and data
			// Check for absenting first
			if (attendance.getTimeOut() == null && attendance.getTime() == null) {
				continue;
			}

			lineChartDataDTOs.add(new LineChartDataDTO(
				AttendanceStatus.SIGNED_OUT.name(),
				attendance.getTimeOut().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
			);
			labels.add(attendance.getTimeOut().toString());
		}

		return ResponseEntity.ok(
			new RealTimeLineChartDTO(
				labels, lineChartDataDTOs
			)
		);
	}
}