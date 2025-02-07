package com.pshs.attendance_system.app.students.controllers;

import com.pshs.attendance_system.models.MessageResponse;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.students.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/students/statistics")
@Tag(name = "Student Statistics", description = "Student Statistics API")
public class StudentStatisticsController {

	private final StudentService studentService;

	public StudentStatisticsController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/all")
	@Operation(summary = "Count all students", description = "Count all students")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The count of all student.")
	})
	public ResponseEntity<?> countAllStudents() {
		return ResponseEntity.ok(studentService.countStudents());
	}

	@GetMapping("/sexuality")
	@Operation(summary = "Count all students by their sexuality", description = "Count all students by their sexuality")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The count of all student by their sexuality.")
	})
	@Parameters({
		@Parameter(name = "sex", description = "The sexuality of the student", required = true)
	})
	public ResponseEntity<?> countAllStudentsBySexuality(@RequestParam String sex) {
		if (sex.isEmpty()) {
			return ResponseEntity.badRequest().body(
				new MessageResponse(
					"Sexuality is required",
					ExecutionStatus.VALIDATION_ERROR
				)
			);
		}

		return ResponseEntity.ok(
			Math.toIntExact(studentService.countStudentsBySex(sex))
		);
	}

	@GetMapping("/section")
	@Operation(summary = "Count all students by their section", description = "Count all students by their section")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The count of all student by their section.")
	})
	public ResponseEntity<?> countAllStudentsBySection(@RequestParam Integer section) {
		if (section == null) {
			return ResponseEntity.badRequest().body(
				new MessageResponse(
					"Section object is required",
					ExecutionStatus.VALIDATION_ERROR
				)
			);
		}

		return ResponseEntity.ok(
			Map.of(
				"students", Math.toIntExact(studentService.countStudentsInSection(section))
			)
		);
	}

	@GetMapping("/grade-level")
	@Operation(summary = "Count all students by their grade level", description = "Count all students by their grade level")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The count of all student by their grade level.")
	})
	@Parameters({
		@Parameter(name = "id", description = "The grade level id", required = true)
	})
	public ResponseEntity<?> countAllStudentsByGradeLevelById(@RequestParam int id) {
		return ResponseEntity.ok(
			Map.of(
				"students", Math.toIntExact(studentService.countStudentsInGradeLevel(id))
			)
		);
	}

	@GetMapping("/grade-level/section")
	@Operation(summary = "Count all students by their grade level and section", description = "Count all students by their grade level and section")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The count of all student by their grade level and section.")
	})
	@Parameters({
		@Parameter(name = "gradeLevelId", description = "The grade level id", required = true),
		@Parameter(name = "section", description = "The section of the student", required = true)
	})
	public ResponseEntity<?> countAllStudentsByGradeLevelAndSection(@RequestParam Integer gradeLevelId, @RequestParam Integer section) {
		return ResponseEntity.ok(
			Map.of(
				"students", Math.toIntExact(studentService.countStudentsInGradeLevelAndSection(gradeLevelId, section))
			)
		);
	}
}