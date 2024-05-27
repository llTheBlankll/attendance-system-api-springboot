package com.pshs.attendance_system.controllers.v1.secured.teacher;

import com.pshs.attendance_system.dto.MessageResponse;
import com.pshs.attendance_system.dto.TeacherDTO;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.services.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping(value = "/teacher", consumes = "application/json", produces = "application/json")
	@Operation(summary = "Create Teacher", description = "Create a new teacher record.")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Teacher Object that will be required to create a new teacher record.", required = true)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Teacher record created successfully.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "400", description = "Failed to create teacher record.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")}),
		@ApiResponse(responseCode = "400", description = "Validation error occurred.", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")})
	})
	public ResponseEntity<?> createTeacher(@RequestBody TeacherDTO teacherDTO) {
		ExecutionStatus status = teacherService.createTeacher(teacherDTO.toEntity());
		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Teacher record created successfully."));
			}
			case FAILURE -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Failed to create teacher record."));
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Validation error occurred."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Failed to create teacher record."));
	}


}