package com.pshs.attendance_system.app.attendances.controllers;

import com.pshs.attendance_system.app.attendances.models.entities.Attendance;
import com.pshs.attendance_system.models.DateRange;
import com.pshs.attendance_system.enums.AttendanceStatus;
import com.pshs.attendance_system.app.attendances.services.AttendanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/attendances/statistics")
@Tag(name = "Attendance Statistics", description = "Attendance Statistics API")
public class AttendanceStatisticsController {

	private final AttendanceService attendanceService;

	public AttendanceStatisticsController(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}

	@PostMapping("/section/{sectionId}/grade-level/{gradeLevelId}/date")
	public ResponseEntity<?> getAllSectionAndGradeLevelAttendanceByDate(@PathVariable int sectionId, @PathVariable int gradeLevelId, @RequestParam LocalDate date, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "date") String sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction orderBy, @RequestParam boolean noPaging) {
		Sort sort = Sort.by(orderBy, sortBy);
		if (noPaging) {
			return ResponseEntity.ok(attendanceService.getAllSectionAndGradeLevelAttendanceByDate(date, sectionId, gradeLevelId, PageRequest.of(page, size, sort)).getContent().stream().map(Attendance::toDTO).toList());
		}

		return ResponseEntity.ok(attendanceService.getAllSectionAndGradeLevelAttendanceByDate(date, sectionId, gradeLevelId, PageRequest.of(page, size, sort)).map(Attendance::toDTO));
	}

	@PostMapping("/student/{id}/days/{attendanceStatus}")
	public ResponseEntity<?> getStudentTotalDaysByStatus(@PathVariable Long id, @PathVariable AttendanceStatus attendanceStatus, @RequestBody DateRange dateRange) {
		return ResponseEntity.ok(attendanceService.countStudentAttendanceByStatusAndDate(
			id,
			attendanceStatus,
			dateRange
		));
	}

	@GetMapping("/top-10-students")
	public ResponseEntity<?> getTop10StudentsByAttendance() {
		return null;
	}
}