

package com.pshs.attendance_system.controllers.v1.secured.gradelevel;

import com.pshs.attendance_system.dto.GradeLevelDTO;
import com.pshs.attendance_system.dto.StrandDTO;
import com.pshs.attendance_system.entities.GradeLevel;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.services.GradeLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(
	name = "Grade Level",
	description = "API Endpoints for Grade Level"
)
@RequestMapping("/api/v1/grade-levels")
public class GradeLevelController {

	private static final Logger logger = LogManager.getLogger(GradeLevelController.class);
	private final GradeLevelService gradeLevelService;

	public GradeLevelController(GradeLevelService gradeLevelService) {
		this.gradeLevelService = gradeLevelService;
	}

	@Operation(
		summary = "Get All Grade Levels",
		description = "Get all grade levels paginated",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "List of grade levels"
			)
		}
	)
	@GetMapping("/grade-level")
	public ResponseEntity<Page<GradeLevelDTO>> getAllGradeLevels(
		@RequestParam(defaultValue = "0") Integer page,
		@RequestParam(defaultValue = "10") Integer size
	) {
		logger.info("Fetching all grade levels");
		return ResponseEntity.ok(
			gradeLevelService.getAllGradeLevels(page, size).map(
				GradeLevel::toDTO
			)
		);
	}

	@Operation(
		summary = "Create Grade Level",
		description = "Create a new grade level",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Shows the status of the operation"
			)
		}
	)
	@PostMapping("/grade-level")
	public ResponseEntity<Map<String, ExecutionStatus>> createGradeLevel(@RequestBody GradeLevelDTO gradeLevelDTO) {
		logger.info("Creating grade level: {}", gradeLevelDTO);
		ExecutionStatus status = gradeLevelService.createGradeLevel(gradeLevelDTO.toEntity());
		return ResponseEntity.ok(
			Map.of(
				"status",
				status
			)
		);
	}

	@Operation(
		summary = "Delete Grade Level",
		description = "Delete a grade level by ID",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Shows the status of the operation"
			)
		}
	)
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, ExecutionStatus>> deleteGradeLevel(@PathVariable Integer id) {
		logger.info("Deleting grade level by id: {}", id);
		return ResponseEntity.ok(
			Map.of(
				"status",
				gradeLevelService.deleteGradeLevel(id)
			)
		);
	}

	@Operation(
		summary = "Get Grade Level by ID",
		description = "Get a grade level by ID",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Grade Level object"
			)
		}
	)
	@GetMapping("/{id}")
	public ResponseEntity<GradeLevelDTO> getGradeLevelById(@PathVariable Integer id) {
		logger.info("Fetching grade level by id: {}", id);
		return ResponseEntity.ok(
			gradeLevelService.getGradeLevel(id).toDTO()
		);
	}


	@Operation(
		summary = "Update Grade Level",
		description = "Update a grade level by ID",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Shows the status of the operation"
			)
		}
	)
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, ExecutionStatus>> updateGradeLevel(@PathVariable Integer id, @RequestBody GradeLevelDTO gradeLevelDTO) {
		logger.info("Updating grade level by id: {}", id);
		return ResponseEntity.ok(
			Map.of(
				"status",
				gradeLevelService.updateGradeLevel(id, gradeLevelDTO.toEntity())
			)
		);
	}

	@GetMapping("/count")
	public ResponseEntity<Map<String, Integer>> countAllGradeLevels() {
		logger.info("Counting all grade levels");
		return ResponseEntity.ok(
			Map.of(
				"count",
				gradeLevelService.countAllGradeLevels()
			)
		);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<GradeLevel>> searchGradeLevels(@RequestParam(required = false) String name, @RequestBody(required = false) StrandDTO strand, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {

		// If name is not empty and strand is provided, then search grade levels by name and strand
		if (!name.isEmpty() && strand != null) {
			return ResponseEntity.ok(
				gradeLevelService.searchGradeLevelsByNameAndStrand(name, strand.getId(), PageRequest.of(page, size))
			);
		} else if (strand != null) { // If strand iso only provided, then search grade levels by strand
			return ResponseEntity.ok(
				gradeLevelService.searchGradeLevelsByStrand(strand.getId(), PageRequest.of(page, size))
			);
		} else if (!name.isEmpty()) { // If name is only provided, then search grade levels by name
			return ResponseEntity.ok(
				gradeLevelService.searchGradeLevelsByName(name, PageRequest.of(page, size))
			);
		} else {
			return ResponseEntity.badRequest()
				.body(Page.empty());
		}
	}
}