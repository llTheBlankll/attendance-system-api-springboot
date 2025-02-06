package com.pshs.attendance_system.app.guardians.controllers;

import com.pshs.attendance_system.app.guardians.models.dto.GuardianDTO;
import com.pshs.attendance_system.models.MessageResponse;
import com.pshs.attendance_system.app.guardians.models.entities.Guardian;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.guardians.services.GuardianService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/guardians")
@Tag(
	name = "Guardian",
	description = "Guardian related operations"
)
public class GuardianController {

	private final GuardianService guardianService;

	public GuardianController(GuardianService guardianService) {
		this.guardianService = guardianService;
	}

	@PostMapping(name = "/guardian", consumes = "application/json", produces = "application/json")
	@Operation(summary = "Create guardian record")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Guardian created successfully"),
		@ApiResponse(responseCode = "400", description = "Guardian already exists"),
		@ApiResponse(responseCode = "500", description = "Failed to create guardian")
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Guardian object", required = true)
	public ResponseEntity<?> createGuardian(@RequestBody GuardianDTO guardian) {
		ExecutionStatus status = guardianService.createGuardian(guardian.toEntity());

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.status(201).body("Guardian created successfully.");
			}
			case INVALID -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Guardian already exists.",
						ExecutionStatus.VALIDATION_ERROR
					)
				);
			}
			default -> {
				return ResponseEntity.status(500).body(
					new MessageResponse(
						"Failed to create guardian.",
						ExecutionStatus.FAILED
					)
				);
			}
		}
	}

	@DeleteMapping(value = "/{guardianId}", produces = "application/json")
	@Operation(summary = "Delete guardian by ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Guardian deleted successfully"),
		@ApiResponse(responseCode = "404", description = "Guardian not found"),
		@ApiResponse(responseCode = "500", description = "Failed to delete guardian")
	})
	@Parameters({
		@Parameter(name = "guardianId", description = "Guardian ID", required = true)
	})
	public ResponseEntity<?> deleteGuardian(@PathVariable int guardianId) {
		ExecutionStatus status = guardianService.deleteGuardian(guardianId);

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(
					new MessageResponse(
						"Guardian deleted successfully.",
						ExecutionStatus.SUCCESS
					)
				);
			}
			case NOT_FOUND -> {
				return ResponseEntity.status(404).body(
					new MessageResponse(
						"Guardian not found.",
						ExecutionStatus.NOT_FOUND
					)
				);
			}
			default -> {
				return ResponseEntity.status(500).body(
					new MessageResponse(
						"Failed to delete guardian.",
						ExecutionStatus.FAILED
					)
				);
			}
		}
	}

	@PutMapping(value = "/{guardianId}", consumes = "application/json", produces = "application/json")
	@Operation(summary = "Update guardian by ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Guardian updated successfully"),
		@ApiResponse(responseCode = "404", description = "Guardian not found"),
		@ApiResponse(responseCode = "400", description = "Guardian already exists"),
		@ApiResponse(responseCode = "500", description = "Failed to update guardian")
	})
	@Parameters({
		@Parameter(name = "guardianId", description = "Guardian ID", required = true)
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Guardian object", required = true)
	public ResponseEntity<?> updateGuardian(@PathVariable int guardianId, @RequestBody GuardianDTO guardian) {
		ExecutionStatus status = guardianService.updateGuardian(guardianId, guardian.toEntity());

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(
					new MessageResponse(
						"Guardian updated successfully.",
						ExecutionStatus.SUCCESS
					)
				);
			}
			case NOT_FOUND -> {
				return ResponseEntity.status(404).body(
					new MessageResponse(
						"Guardian not found.",
						ExecutionStatus.NOT_FOUND
					)
				);
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Guardian already exists.",
						ExecutionStatus.INVALID
					)
				);
			}
			default -> {
				return ResponseEntity.status(500).body(
					new MessageResponse(
						"Failed to update guardian.",
						ExecutionStatus.FAILED
					)
				);
			}
		}
	}

	@GetMapping(value = "/guardian", produces = "application/json")
	public ResponseEntity<?> getAllGuardian(@RequestParam int page, @RequestParam int size) {
		return ResponseEntity.ok(guardianService.getAllGuardian(PageRequest.of(page, size)));
	}

	@GetMapping(value = "/{guardianId}", produces = "application/json")
	public ResponseEntity<?> getGuardian(@PathVariable int guardianId) {
		Optional<Guardian> guardian = guardianService.getGuardian(guardianId);

		if (guardian.isEmpty()) {
			return ResponseEntity.status(404).body(
				new MessageResponse(
					"Guardian not found.",
					ExecutionStatus.NOT_FOUND
				)
			);
		}

		return ResponseEntity.ok(guardian.get().toDTO());
	}

	@GetMapping(value = "/search", produces = "application/json")
	@Operation(summary = "Search guardian")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Guardian found"),
		@ApiResponse(responseCode = "404", description = "Guardian not found")
	})
	public ResponseEntity<?> searchGuardian(@RequestParam String fullName, @RequestParam String contactNumber, @RequestParam(defaultValue = "fullName") String sort, @RequestParam("ASC") String order, @RequestParam int page, @RequestParam int size) {
		Page<Guardian> guardians;
		Pageable pageRequest = PageRequest.of(page, size).withSort(Sort.by(sort).ascending());

		if (order.equals("desc")) {
			pageRequest = PageRequest.of(page, size).withSort(Sort.by(sort).descending());
		}

		if (fullName != null && contactNumber != null) {
			guardians = guardianService.searchGuardianByFullNameAndContactNumber(fullName, contactNumber, pageRequest);
		} else if (fullName != null) {
			guardians = guardianService.searchGuardianByFullName(fullName, pageRequest);
		} else if (contactNumber != null) {
			guardians = guardianService.searchGuardianByContactNumber(contactNumber, pageRequest);
		} else {
			guardians = guardianService.getAllGuardian(pageRequest);
		}

		return ResponseEntity.ok(guardians);
	}
}