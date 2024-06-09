package com.pshs.attendance_system.controllers.v1.secured.student;

import com.pshs.attendance_system.dto.*;
import com.pshs.attendance_system.entities.Student;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Student", description = "Student API")
public class StudentController {

	private static final Logger logger = LogManager.getLogger(StudentController.class);
	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/")
	@Operation(summary = "Get all students", description = "Get all students")
	@Parameters({
		@Parameter(name = "page", description = "Page number", required = true),
		@Parameter(name = "size", description = "Page size", required = true)
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Students returned successfully")
	})
	public ResponseEntity<?> getAllStudents(@RequestParam int page, @RequestParam int size) {
		Page<Student> studentPage = studentService.getAllStudents(PageRequest.of(page, size));
		Page<StudentDTO> dtoPage = new PageImpl<>(studentPage.getContent().stream()
			.map(Student::toDTO)
			.collect(Collectors.toList()), PageRequest.of(page, size), studentPage.getTotalElements());
		logger.info("Returning {} students", dtoPage.getTotalElements());
		return ResponseEntity.ok(dtoPage);
	}

	@PostMapping("/")
	@Operation(summary = "Create student", description = "Create student")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student to create", required = true)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Student created successfully"),
		@ApiResponse(responseCode = "400", description = "Student creation failed")
	})
	public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO) {
		ExecutionStatus status = studentService.createStudent(studentDTO.toEntity());
		if (status == ExecutionStatus.SUCCESS) {
			return ResponseEntity.ok(new StatusMessageResponse("Student created successfully", status));
		} else {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student creation failed", status));
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete student", description = "Delete student")
	@Parameters({
		@Parameter(name = "id", description = "Student ID", required = true)
	})
	public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student ID is required", ExecutionStatus.FAILURE));
		}

		ExecutionStatus status = studentService.deleteStudent(id);
		if (status == ExecutionStatus.SUCCESS) {
			return ResponseEntity.ok(new StatusMessageResponse("Student deleted successfully", status));
		} else {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student deletion failed", status));
		}
	}

	@DeleteMapping("/student")
	@Operation(summary = "Delete student", description = "Delete student")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student to delete", required = true)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Student deleted successfully"),
		@ApiResponse(responseCode = "400", description = "Student deletion failed")
	})
	public ResponseEntity<?> deleteStudent(@RequestBody StudentDTO student) {
		if (student == null) {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student ID is required", ExecutionStatus.FAILURE));
		}

		ExecutionStatus status = studentService.deleteStudent(student.toEntity());
		if (status == ExecutionStatus.SUCCESS) {
			return ResponseEntity.ok(new StatusMessageResponse("Student deleted successfully", status));
		} else {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student deletion failed", status));
		}
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update student", description = "Update student")
	@Parameters({
		@Parameter(name = "id", description = "Student ID", required = true)
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Student updated successfully"),
		@ApiResponse(responseCode = "400", description = "Student update failed")
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student to update", required = true, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
	public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
		// Validation
		if (id == null) {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student ID is required", ExecutionStatus.FAILURE));
		}

		ExecutionStatus status = studentService.updateStudent(id, studentDTO.toEntity());
		if (status == ExecutionStatus.SUCCESS) {
			return ResponseEntity.ok(new StatusMessageResponse("Student updated successfully", status));
		} else {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student update failed", status));
		}
	}

	@PatchMapping("/{id}/grade-level")
	@Operation(summary = "Update student grade level", description = "Update student grade level")
	@Parameters({
		@Parameter(name = "id", description = "Student ID", required = true)
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Student grade level updated successfully"),
		@ApiResponse(responseCode = "400", description = "Student grade level update failed")
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student grade level to update", required = true)
	public ResponseEntity<?> updateStudentGradeLevel(@PathVariable Long id, @RequestBody GradeLevelDTO gradeLevel) {
		if (id == null) {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student ID is required", ExecutionStatus.FAILURE));
		}

		ExecutionStatus status = studentService.updateStudentGradeLevel(id, gradeLevel.getId());
		if (status == ExecutionStatus.SUCCESS) {
			return ResponseEntity.ok(new StatusMessageResponse("Student grade level updated successfully", status));
		} else {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student grade level update failed", status));
		}
	}

	@PatchMapping("/{id}/section")
	@Operation(summary = "Update student section", description = "Update student section by ID")
	@Parameters({
		@Parameter(name = "id", description = "Student ID", required = true)
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Student section updated successfully"),
		@ApiResponse(responseCode = "400", description = "Student section update failed")
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student section to update", required = true)
	public ResponseEntity<?> updateStudentSection(@PathVariable Long id, @RequestBody SectionDTO section) {
		if (id == null) {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student ID is required", ExecutionStatus.FAILURE));
		}

		ExecutionStatus status = studentService.updateStudentSection(id, section.getId());
		if (status == ExecutionStatus.SUCCESS) {
			return ResponseEntity.ok(new StatusMessageResponse("Student section updated successfully", status));
		} else {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student section update failed", status));
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get student by ID", description = "Get student by ID")
	@Parameters({
		@Parameter(name = "id", description = "Student ID", required = true)
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Student returned successfully"),
		@ApiResponse(responseCode = "400", description = "Student not found")
	})
	public ResponseEntity<?> getStudentById(@PathVariable Long id) {
		Student student = studentService.getStudentById(id);
		if (student == null) {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("Student not found", ExecutionStatus.FAILURE));
		}
		return ResponseEntity.ok(student.toDTO());
	}

	@GetMapping("/by-guardian")
	@Operation(summary = "Get students by guardian", description = "Get students by guardian")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Guardian ID", required = true)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Students returned successfully"),
		@ApiResponse(responseCode = "400", description = "Students not found")
	})
	public ResponseEntity<?> getStudentsByGuardian(@RequestBody GuardianDTO guardian) {
		return ResponseEntity.ok(studentService.getStudentByGuardian(guardian.toEntity()));
	}

	@GetMapping("/by-guardian-id")
	@Operation(summary = "Get students by guardian", description = "Get student by guardian id")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Guardian ID", required = true)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Students returned successfully"),
		@ApiResponse(responseCode = "400", description = "Students not found")
	})
	public ResponseEntity<?> getStudentsByGuardian(@RequestParam Integer guardianId) {
		return ResponseEntity.ok(studentService.getStudentByGuardian(guardianId));
	}

	@GetMapping("/search")
	@Operation(summary = "Search students", description = "Search students")
	@Parameters({
		@Parameter(name = "query", description = "Search query", required = true),
		@Parameter(name = "page", description = "Page number", required = true),
		@Parameter(name = "size", description = "Page size", required = true)
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Students returned successfully")
	})
	public ResponseEntity<?> searchStudents(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int page, @RequestParam int size) {
		if (firstName != null && lastName == null) {
			return ResponseEntity.ok(studentService.searchStudentsByFirstName(firstName, PageRequest.of(page, size)));
		} else if (firstName == null && lastName != null) {
			return ResponseEntity.ok(studentService.searchStudentsByLastName(lastName, PageRequest.of(page, size)));
		} else {
			return ResponseEntity.ok(studentService.searchStudentsByFirstAndLastName(firstName, lastName, PageRequest.of(page, size)));
		}
	}
}