package com.pshs.attendance_system.app.students.controllers;

import com.pshs.attendance_system.models.MessageResponse;
import com.pshs.attendance_system.app.students.models.dto.StudentDTO;
import com.pshs.attendance_system.app.students.models.entities.Student;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.students.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Student", description = "Student API")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/all")
	@Operation(summary = "Get all students", description = "Get all students")
	@Parameters({
		@Parameter(name = "page", description = "Page number", required = true),
		@Parameter(name = "size", description = "Page size", required = true)
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Students returned successfully")
	})
	public ResponseEntity<?> getAllStudents(@RequestParam Integer page, @RequestParam Integer size,
	                                        @RequestParam(defaultValue = "lastName") String sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction orderBy) {
		// Create a sort object
		Sort sort = Sort.by(orderBy, sortBy);

		// Get all students
		Page<Student> studentPage = studentService.getAllStudents(PageRequest.of(page, size, sort));
		return ResponseEntity.ok(
			convertStudentPageToDTO(studentPage, sort)
		);
	}

	@PostMapping("/create")
	@Operation(summary = "Create student", description = "Create student")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student to create", required = true)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Student created successfully"),
		@ApiResponse(responseCode = "400", description = "Student creation failed")
	})
	public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO) {
		ExecutionStatus status = studentService.createStudent(studentDTO.toEntity());
		return switch (status) {
			case SUCCESS -> ResponseEntity.ok(new MessageResponse("Student is created successfully", ExecutionStatus.SUCCESS));
			case VALIDATION_ERROR -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Student is not valid", ExecutionStatus.VALIDATION_ERROR));
			case FAILED -> ResponseEntity.badRequest().body(new MessageResponse("Student already exists", ExecutionStatus.FAILED));
			default -> ResponseEntity.badRequest().body(new MessageResponse("Student creation failed", ExecutionStatus.FAILED));
		};
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete student", description = "Delete student")
	@Parameters({
		@Parameter(name = "id", description = "Student ID", required = true)
	})
	public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Student ID is required", ExecutionStatus.FAILED));
		}

		ExecutionStatus status = studentService.deleteStudent(id);
		if (status == ExecutionStatus.SUCCESS) {
			return ResponseEntity.ok(new MessageResponse("Student deleted successfully", status));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Student deletion failed", status));
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
			return ResponseEntity.badRequest().body(new MessageResponse("Student ID is required", ExecutionStatus.FAILED));
		}

		ExecutionStatus status = studentService.updateStudent(id, studentDTO.toEntity());
		if (status == ExecutionStatus.SUCCESS) {
			return ResponseEntity.ok(new MessageResponse("Student updated successfully", status));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Student update failed", status));
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
	public ResponseEntity<?> updateStudentGradeLevel(@PathVariable Long id, @RequestParam Integer gradeLevelId) {
		if (id == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Student ID is required", ExecutionStatus.FAILED));
		}

		ExecutionStatus status = studentService.updateStudentGradeLevel(id, gradeLevelId);
		if (status == ExecutionStatus.SUCCESS) {
			return ResponseEntity.ok(new MessageResponse("Student grade level updated successfully", status));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Student grade level update failed", status));
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
	public ResponseEntity<?> updateStudentSection(@PathVariable Long id, @RequestParam Integer sectionId) {
		if (id == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Student ID is required", ExecutionStatus.FAILED));
		}

		ExecutionStatus status = studentService.updateStudentSection(id, sectionId);
		if (status == ExecutionStatus.SUCCESS) {
			return ResponseEntity.ok(new MessageResponse("Student section updated successfully", status));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Student section update failed", status));
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
			return ResponseEntity.badRequest().body(new MessageResponse("Student not found", ExecutionStatus.FAILED));
		}

		return ResponseEntity.ok(student.toDTO());
	}

	@GetMapping("/by-guardian")
	@Operation(summary = "Get students by guardian", description = "Get student by guardian id")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Guardian ID", required = true)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Students returned successfully"),
		@ApiResponse(responseCode = "400", description = "Students not found")
	})
	public ResponseEntity<?> getStudentsByGuardian(@RequestParam Integer guardianId) {
		return ResponseEntity.ok(studentService.getStudentByGuardian(guardianId).toDTO());
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
	public ResponseEntity<?> searchStudents(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int page, @RequestParam int size,
	                                        @RequestParam(required = false, defaultValue = "false") Boolean noPaging,
	                                        @RequestParam(required = false, defaultValue = "ASC") Sort.Direction orderBy,
	                                        @RequestParam(required = false, defaultValue = "lastName") String sortBy) {
		// Check if noPaging is null
		if (noPaging == null) {
			noPaging = false;
		}

		Sort sort = Sort.by(orderBy, sortBy);
		if (noPaging) {
			return ResponseEntity.ok(
				convertStudentPageToDTO(
					studentService.searchStudentsByFirstAndLastName(
						firstName, lastName, Pageable.unpaged(sort)
					),
					sort
				)
			);
		}

		if (firstName != null && lastName == null) {
			return ResponseEntity.ok(
				convertStudentPageToDTO(
					studentService.searchStudentsByFirstName(firstName, PageRequest.of(page, size, sort)),
					sort
				)
			);
		} else if (firstName == null && lastName != null) {
			return ResponseEntity.ok(
				convertStudentPageToDTO(
					studentService.searchStudentsByLastName(lastName, PageRequest.of(page, size, sort)),
					sort
				)
			);
		} else {
			return ResponseEntity.ok(
				convertStudentPageToDTO(
					studentService.searchStudentsByFirstAndLastName(firstName, lastName, PageRequest.of(page, size, sort)),
					sort
				)
			);
		}
	}

	@GetMapping("/all/grade-level/{id}")
	@Operation(summary = "Get all students by grade level", description = "Get all students by grade level")
	@Parameters({
		@Parameter(name = "id", description = "Grade level ID", required = true)
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Students returned successfully")
	})
	public ResponseEntity<?> getStudentsByGradeLevel(@PathVariable Integer id,
	                                                 @RequestParam int page, @RequestParam int size,
	                                                 @RequestParam(required = false, defaultValue = "false") Boolean noPaging,
	                                                 @RequestParam(required = false, defaultValue = "ASC") Sort.Direction orderBy,
	                                                 @RequestParam(required = false, defaultValue = "lastName") String sortBy) {
		if (noPaging == null) {
			noPaging = false;
		}

		Sort sort = Sort.by(orderBy, sortBy);
		if (noPaging) {
			// Convert Page<Student> to Page<StudentDTO> and return
			Page<Student> students = studentService.getStudentsInGradeLevel(id, Pageable.unpaged(sort));
			return ResponseEntity.ok(
				convertStudentPageToDTO(students, sort)
			);
		}

		// Convert Page<Student> to Page<StudentDTO> and return
		Page<Student> students = studentService.getStudentsInGradeLevel(id, PageRequest.of(page, size, sort));
		return ResponseEntity.ok(
			new PageImpl<>(students.getContent().stream().map(Student::toDTO).collect(Collectors.toList()), PageRequest.of(page, size, sort), students.getTotalElements())
		);
	}

	@GetMapping("/all/section/{id}")
	@Operation(summary = "Get all students by section", description = "Get all students by section")
	@Parameters({
		@Parameter(name = "id", description = "Section ID", required = true)
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Students returned successfully")
	})
	public ResponseEntity<?> getStudentsBySection(@PathVariable Integer id,
	                                              @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
	                                              @RequestParam(required = false, defaultValue = "false") Boolean noPaging,
	                                              @RequestParam(required = false, defaultValue = "ASC") Sort.Direction orderBy,
	                                              @RequestParam(required = false, defaultValue = "lastName") String sortBy) {
		if (noPaging == null) {
			noPaging = false;
		}

		Sort sort = Sort.by(orderBy, sortBy);
		if (noPaging) {
			// Convert Page<Student> to Page<StudentDTO>
			Page<Student> students = studentService.getStudentsInSection(id, Pageable.unpaged(sort));

			if (students != null) {
				return ResponseEntity.ok(
					convertStudentPageToDTO(students, sort)
				);
			}
		}

		Page<Student> students = studentService.getStudentsInSection(id, PageRequest.of(page, size, sort));
		return ResponseEntity.ok(
			convertStudentPageToDTO(students, sort)
		);
	}

	private Page<StudentDTO> convertStudentPageToDTO(Page<Student> studentPage, Sort sort) {
		return new PageImpl<>(studentPage.getContent().stream()
			.map(Student::toDTO)
			.collect(Collectors.toList()), PageRequest.of(studentPage.getNumber(), studentPage.getSize(), sort), studentPage.getTotalElements());
	}

	private Page<StudentDTO> convertStudentPageToDTO(Page<Student> studentPage) {
		return new PageImpl<>(studentPage.getContent().stream()
			.map(Student::toDTO)
			.collect(Collectors.toList()), PageRequest.of(studentPage.getNumber(), studentPage.getSize()), studentPage.getTotalElements());
	}
}