package com.pshs.attendance_system.controllers.v1.secured.attendance;

import com.pshs.attendance_system.dto.AttendanceDTO;
import com.pshs.attendance_system.entities.range.DateRange;
import com.pshs.attendance_system.enums.Status;
import com.pshs.attendance_system.services.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/attendances")
@Tag(name = "Attendance", description = "Attendance API")
public class AttendanceController {

	private final AttendanceService attendanceService;

	public AttendanceController(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllRecentAttendance(@RequestParam Integer page, @RequestParam Integer size, @RequestParam(defaultValue = "ASC")  String sortBy, @RequestParam(defaultValue = "date") String order) {
		Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
		return ResponseEntity.ok(attendanceService.getAllAttendances(PageRequest.of(page, size).withSort(sort)));
	}

	@PostMapping("/attendance")
	@Operation(summary = "Create Attendance", description = "Create a new attendance")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Attendance object to be created", required = true)
	public ResponseEntity<?> createAttendance(@RequestBody AttendanceDTO attendanceDTO) {
		return ResponseEntity.ok(attendanceService.createAttendance(attendanceDTO.toEntity()));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Attendance", description = "Delete an attendance")
	@Parameters({
		@Parameter(name = "id", description = "Attendance ID", required = true)
	})
	public ResponseEntity<?> deleteAttendance(@PathVariable Integer id) {
		attendanceService.deleteAttendance(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/student/{id}")
	@Operation(summary = "Get All of the Attendance of the Student", description = "Get attendance by student ID")
	@Parameters({
		@Parameter(name = "id", description = "Student ID", required = true)
	})
	public ResponseEntity<?> getAttendanceByStudentId(@PathVariable Long id, @RequestParam Integer page, @RequestParam Integer size, @RequestParam(defaultValue = "date") String sortBy, @RequestParam(defaultValue = "ASC") String order) {
		Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
		return ResponseEntity.ok(attendanceService.getAllAttendancesByStudentId(id, PageRequest.of(page, size).withSort(sort)));
	}

	@GetMapping("/date/{date}")
	@Operation(summary = "Get All of the Attendance by Date", description = "Get All of the Attendances")
	@Parameters({
		@Parameter(name = "date", description = "Date", required = true)
	})
	public ResponseEntity<?> getAttendanceByDate(@PathVariable LocalDate date, @RequestParam Integer page, @RequestParam Integer size, @RequestParam(defaultValue = "date") String sortBy, @RequestParam(defaultValue = "ASC") String order) {
		Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
		return ResponseEntity.ok(attendanceService.getAllAttendancesByDate(date, PageRequest.of(page, size).withSort(sort)));
	}

	@PostMapping("/status/{status}/date-range")
	@Operation(summary = "Get the total count of attendance by Status and Date Range", description = "Get All of the Attendances count")
	@Parameters({
		@Parameter(name = "status", description = "Status", required = true),
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Date Range", required = true, content = {@Content(schema = @Schema(implementation = DateRange.class))})
	public ResponseEntity<?> getAttendanceByStatusAndDateRange(@PathVariable Status status, @RequestBody DateRange dateRange) {
		return ResponseEntity.ok(attendanceService.countStudentsByStatusAndDateRange(status, dateRange));
	}

	@GetMapping("/status/{status}/date")
	@Operation(summary = "Get the total count of attendance by Status and Date", description = "Get All of the Attendances count")
	@Parameters({
		@Parameter(name = "status", description = "Status", required = true),
		@Parameter(name = "date", description = "Date", required = true)
	})
	public ResponseEntity<?> getAttendanceByStatusAndDate(@PathVariable Status status, @RequestParam LocalDate date) {
		return ResponseEntity.ok(attendanceService.countStudentsByStatusAndDateRange(status, new DateRange(date, date)));
	}
}