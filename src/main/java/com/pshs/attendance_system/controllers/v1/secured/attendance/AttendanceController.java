package com.pshs.attendance_system.controllers.v1.secured.attendance;

import com.pshs.attendance_system.dto.AttendanceDTO;
import com.pshs.attendance_system.enums.AttendanceStatus;
import com.pshs.attendance_system.enums.Status;
import com.pshs.attendance_system.services.AttendanceService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

public class AttendanceController {

	private final AttendanceService attendanceService;

	public AttendanceController(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}

	@PostMapping("/")
	public ResponseEntity<?> createAttendance(AttendanceDTO attendanceDTO) {
		return ResponseEntity.ok(attendanceService.createAttendance(attendanceDTO.toEntity()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAttendance(@PathVariable Integer id) {
		attendanceService.deleteAttendance(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<?> getAttendanceByStudentId(@PathVariable Long id, @RequestParam Integer page, @RequestParam Integer size) {
		return ResponseEntity.ok(attendanceService.getAllAttendancesByStudentId(id, PageRequest.of(page, size)));
	}

	@GetMapping("/{date}/status/{status}")
	public ResponseEntity<?> countAttendanceByDateAndStatus(@PathVariable LocalDate date, @PathVariable AttendanceStatus status) {
		return ResponseEntity.ok(attendanceService.countStudentsByStatusAndDate(status, date));
	}

	@GetMapping("/{date}")
	public ResponseEntity<?> getAttendanceByDate(@PathVariable LocalDate date, @RequestParam Integer page, @RequestParam Integer size) {
		return ResponseEntity.ok(attendanceService.getAllAttendancesByDate(date, PageRequest.of(page, size)));
	}
}