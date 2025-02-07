

package com.pshs.attendance_system.app.gradelevels.controllers;

import com.pshs.attendance_system.app.gradelevels.models.dto.GradeLevelDTO;
import com.pshs.attendance_system.models.MessageResponse;
import com.pshs.attendance_system.app.gradelevels.models.entities.GradeLevel;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.gradelevels.services.GradeLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@Tag(name = "Grade Level", description = "API Endpoints for Grade Level")
@RequestMapping("/api/v1/grade-levels")
public class GradeLevelController {

	private static final Logger logger = LogManager.getLogger(GradeLevelController.class);
	private final GradeLevelService gradeLevelService;

	public GradeLevelController(GradeLevelService gradeLevelService) {
		this.gradeLevelService = gradeLevelService;
	}

	@Operation(summary = "Get All Grade Levels", description = "Get all grade levels paginated", responses = {@ApiResponse(responseCode = "200", description = "List of grade levels")})
	@GetMapping("/all")
	public ResponseEntity<?> getAllGradeLevels(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "ASC") String order, @RequestParam Boolean noPaging) {
		if (noPaging == null) {
			noPaging = false;
		}

		logger.debug("Fetching all grade levels");
		Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
		if (noPaging) {
			return ResponseEntity.ok(gradeLevelService.getAllGradeLevels(Pageable.unpaged(sort)).getContent().stream()
				.map(GradeLevel::toDTO).toList());
		}

		return ResponseEntity.ok(gradeLevelService.getAllGradeLevels(PageRequest.of(page, size, sort)).map(GradeLevel::toDTO));
	}

	@Operation(summary = "Create Grade Level", description = "Create a new grade level", responses = {@ApiResponse(responseCode = "200", description = "Shows the status of the operation")})
	@PostMapping("/create")
	public ResponseEntity<?> createGradeLevel(@RequestBody GradeLevelDTO gradeLevelDTO) {
		logger.debug("Creating grade level: {}", gradeLevelDTO);
		ExecutionStatus status = gradeLevelService.createGradeLevel(gradeLevelDTO.toEntity());
		if (Objects.requireNonNull(status) == ExecutionStatus.SUCCESS) {
			return ResponseEntity.ok(new MessageResponse("Grade Level created successfully", status));
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Grade Level creation failed", status));
	}

	@Operation(summary = "Delete Grade Level", description = "Delete a grade level by ID", responses = {@ApiResponse(responseCode = "200", description = "Shows the status of the operation")})
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, ExecutionStatus>> deleteGradeLevel(@PathVariable Integer id) {
		logger.debug("Deleting grade level by id: {}", id);
		return ResponseEntity.ok(Map.of("status", gradeLevelService.deleteGradeLevel(id)));
	}

	@Operation(summary = "Get Grade Level by ID", description = "Get a grade level by ID", responses = {@ApiResponse(responseCode = "200", description = "Grade Level object")})
	@GetMapping("/{id}")
	public ResponseEntity<GradeLevelDTO> getGradeLevelById(@PathVariable Integer id) {
		logger.debug("Fetching grade level by id: {}", id);
		Optional<GradeLevel> gradeLevelOptional = gradeLevelService.getGradeLevel(id);

		if (gradeLevelOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(gradeLevelService.getGradeLevel(id).orElse(new GradeLevel()).toDTO());
		}
	}

	@Operation(summary = "Update Grade Level", description = "Update a grade level by ID", responses = {@ApiResponse(responseCode = "200", description = "Shows the status of the operation")})
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, ExecutionStatus>> updateGradeLevel(@PathVariable Integer id, @RequestBody GradeLevelDTO gradeLevelDTO) {
		logger.debug("Updating grade level by id: {}", id);
		return ResponseEntity.ok(Map.of("status", gradeLevelService.updateGradeLevel(id, gradeLevelDTO.toEntity())));
	}

	@GetMapping("/count")
	public ResponseEntity<Map<String, Integer>> countAllGradeLevels() {
		logger.debug("Counting all grade levels");
		return ResponseEntity.ok(Map.of("count", gradeLevelService.countAllGradeLevels()));
	}

	@GetMapping("/search")
	public ResponseEntity<Page<GradeLevel>> searchGradeLevels(@RequestParam(required = false) String name, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "ASC") String order) {
		logger.debug("Searching grade levels by name: {}", name);
		Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
		if (!name.isEmpty()) {
			return ResponseEntity.ok(gradeLevelService.searchGradeLevelsByName(name, PageRequest.of(page, size, sort)));
		} else {
			return ResponseEntity.badRequest().body(Page.empty());
		}
	}
}