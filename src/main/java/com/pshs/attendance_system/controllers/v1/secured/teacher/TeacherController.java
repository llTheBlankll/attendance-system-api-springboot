package com.pshs.attendance_system.controllers.v1.secured.teacher;

import com.pshs.attendance_system.dto.MessageResponse;
import com.pshs.attendance_system.dto.TeacherDTO;
import com.pshs.attendance_system.entities.Teacher;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.services.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teacher")
@Tag(
	name = "Teacher Endpoints",
	description = "API Endpoints for Teacher"
)
public class TeacherController {

	private final TeacherService teacherService;

	public TeacherController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	@Operation(summary = "Create Teacher", description = "Create a new teacher record.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Teacher record created successfully.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "409", description = "Failed to create teacher record.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "400", description = "Validation error occurred.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")})
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Teacher Object that will be required to create a new teacher record.", required = true)
	public ResponseEntity<?> createTeacher(@RequestBody TeacherDTO teacherDTO) {
		ExecutionStatus status = teacherService.createTeacher(teacherDTO.toEntity());
		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Teacher record created successfully."));
			}
			case FAILURE -> {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Failed to create teacher record."));
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Validation error occurred."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Failed to create teacher record."));
	}

	@DeleteMapping(name = "/", consumes = "application/json", produces = "application/json")
	@Operation(summary = "Delete Teacher", description = "Delete a teacher record.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Teacher record deleted successfully.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "404", description = "Teacher record not found.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "400", description = "Failed to delete teacher record.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")})
	})
	@Parameters({
		@Parameter(name = "id", description = "The ID of the teacher.")
	})
	public ResponseEntity<?> deleteTeacher(@RequestParam int id) {
		ExecutionStatus status = teacherService.deleteTeacher(id);
		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Teacher record deleted successfully."));
			}
			case NOT_FOUND -> {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Teacher record not found."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Failed to delete teacher record."));
	}

	@PutMapping(value = "/", consumes = "application/json", produces = "application/json")
	@Operation(summary = "Update Teacher", description = "Update a teacher record.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Teacher record updated successfully.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "404", description = "Teacher record not found.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "400", description = "Validation error occurred.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")})
	})
	@Parameters({
		@Parameter(name = "id", description = "The ID of the teacher.")
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Teacher Object that will be required to update a teacher record.", required = true)
	public ResponseEntity<?> updateTeacher(@RequestParam int id, @RequestBody TeacherDTO teacherDTO) {
		ExecutionStatus status = teacherService.updateTeacher(id, teacherDTO.toEntity());
		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Teacher record updated successfully."));
			}
			case NOT_FOUND -> {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Teacher record not found."));
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Validation error occurred."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Failed to update teacher record."));
	}

	@PatchMapping(value = "/firstname", consumes = "application/json", produces = "application/json")
	@Operation(summary = "Update Teacher First Name", description = "Update a teacher's first name.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Teacher record updated successfully.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "404", description = "Teacher record not found.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "400", description = "Validation error occurred.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")})
	})
	@Parameters({
		@Parameter(name = "id", description = "The ID of the teacher."),
		@Parameter(name = "firstName", description = "The first name of the teacher.")
	})
	public ResponseEntity<?> updateTeacherFirstName(@RequestParam int id, @RequestParam String firstName) {
		ExecutionStatus status = teacherService.updateTeacherFirstName(id, firstName);
		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Teacher first name was updated successfully."));
			}
			case NOT_FOUND -> {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Teacher record not found."));
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Validation error occurred."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Failed to update teacher record."));
	}

	@PatchMapping(value = "/lastname", consumes = "application/json", produces = "application/json")
	@Operation(summary = "Update Teacher Last Name", description = "Update a teacher's last name.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Teacher record updated successfully.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "404", description = "Teacher record not found.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "400", description = "Validation error occurred.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")})
	})
	@Parameters({
		@Parameter(name = "id", description = "The ID of the teacher."),
		@Parameter(name = "lastName", description = "The last name of the teacher.")
	})
	public ResponseEntity<?> updateTeacherLastName(@RequestParam int id, @RequestParam String lastName) {
		ExecutionStatus status = teacherService.updateTeacherLastName(id, lastName);
		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Teacher last name was updated successfully."));
			}
			case NOT_FOUND -> {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Teacher record not found."));
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Validation error occurred."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Failed to update teacher record."));
	}

	@GetMapping(value = "/", produces = "application/json")
	@Operation(summary = "Get Teacher", description = "Get a teacher record.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Teacher record retrieved successfully.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "404", description = "Teacher record not found.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")})
	})
	@Parameters({
		@Parameter(name = "id", description = "The ID of the teacher.")
	})
	public ResponseEntity<?> getTeacher(@RequestParam int id) {
		Teacher teacher = teacherService.getTeacher(id);
		if (teacher == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Teacher record not found."));
		}

		return ResponseEntity.ok(teacher.toDTO());
	}

	@GetMapping("/search")
	@Operation(summary = "Search Teachers", description = "Search for teacher records.")
	@Parameters({
		@Parameter(name = "firstName", description = "First Name of the teacher."),
		@Parameter(name = "lastName", description = "Last Name of the teacher."),
		@Parameter(name = "sex", description = "The sexuality of the teacher.")
	})
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Teacher records retrieved successfully.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "404", description = "No teacher records found.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")})
	})
	public ResponseEntity<?> searchTeachers(@RequestParam(required = false) String firstName,
	                                        @RequestParam(required = false) String lastName,
	                                        @RequestParam(required = false) String sex,
																					@RequestParam(defaultValue = "lastName") String sort,
																					@RequestParam(defaultValue = "asc") String order,
	                                        @RequestParam int page,
	                                        @RequestParam int size) {
		Page<Teacher> teachers;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

		if ("desc".equalsIgnoreCase(order)) {
			pageable = PageRequest.of(page, size, Sort.by(sort).descending());
		}

		if (firstName != null && lastName != null && sex != null) {
			teachers = teacherService.searchTeacherByFirstNameAndLastNameAndSex(firstName, lastName, sex, pageable);
		} else if (firstName != null && lastName != null) {
			teachers = teacherService.searchTeacherByFirstNameAndLastName(firstName, lastName, pageable);
		} else if (firstName != null && sex != null) {
			teachers = teacherService.searchTeacherByFirstNameAndSex(firstName, sex, pageable);
		} else if (lastName != null && sex != null) {
			teachers = teacherService.searchTeacherByLastNameAndSex(lastName, sex, pageable);
		} else if (firstName != null) {
			teachers = teacherService.searchTeacherByFirstName(firstName, pageable);
		} else if (lastName != null) {
			teachers = teacherService.searchTeacherByLastName(lastName, pageable);
		} else if (sex != null) {
			teachers = teacherService.searchTeacherBySex(sex, pageable);
		} else {
			teachers = teacherService.getAllTeachers(pageable);
		}

		return ResponseEntity.ok(teachers);
	}

	@GetMapping("/count/all")
	@Operation(summary = "Count All Teachers", description = "Count all teacher records.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The count of all teachers in the database.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")})
	})
	public ResponseEntity<?> countAllTeachers() {
		int count = teacherService.getTeacherCount();
		if (count <= 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("No teacher records found."));
		}

		return ResponseEntity.ok(new MessageResponse(String.valueOf(count)));
	}
}