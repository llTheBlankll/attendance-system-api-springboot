package com.pshs.attendance_system.controllers.v1.secured.strand;

import com.pshs.attendance_system.dto.MessageResponse;
import com.pshs.attendance_system.dto.StrandDTO;
import com.pshs.attendance_system.entities.Strand;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.services.StrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@Tag(name = "Strand", description = "Strand API")
@RequestMapping("/api/v1/strands")
public class StrandController {

	private final StrandService strandService;

	public StrandController(StrandService strandService) {
		this.strandService = strandService;
	}

	@PostMapping(name = "/v1/strands", consumes = "application/json", produces = "application/json")
	@Operation(summary = "Create Strand", description = "Create a new strand")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Strand created successfully"),
		@ApiResponse(responseCode = "400", description = "Strand already exists"),
		@ApiResponse(responseCode = "500", description = "Failed to create strand")
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Strand object to be created", required = true)
	public ResponseEntity<?> createStrand(@RequestBody StrandDTO strand) {
		ExecutionStatus status = strandService.createStrand(strand.toEntity());
		switch (status) {
			case SUCCESS -> {
				// return 201 Created
				return ResponseEntity.status(201).body(new MessageResponse("Strand created successfully."));
			}
			case VALIDATION_ERROR -> {
				// return 400 Bad Request
				return ResponseEntity.badRequest().body(new MessageResponse("Strand already exists."));
			}
		}

		// return 500 Internal Server Error
		return ResponseEntity.status(500).body(new MessageResponse("Failed to create strand."));
	}

	@DeleteMapping("/")
	@Operation(summary = "Delete Strand", description = "Delete a strand")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Strand deleted successfully"),
		@ApiResponse(responseCode = "404", description = "Strand not found"),
		@ApiResponse(responseCode = "500", description = "Failed to delete strand")
	})
	@Parameters({
		@Parameter(name = "strandId", description = "ID of the strand to be deleted", required = true)
	})
	public ResponseEntity<?> deleteStrand(@RequestParam int strandId) {
		ExecutionStatus status = strandService.deleteStrand(strandId);
		switch (status) {
			case SUCCESS -> {
				// return 200 OK
				return ResponseEntity.ok(new MessageResponse("Strand deleted successfully."));
			}
			case NOT_FOUND -> {
				// return 404 Not Found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Strand not found."));
			}
		}

		// return 500 Internal Server Error
		return ResponseEntity.status(500).body(new MessageResponse("Failed to delete strand."));
	}

	@PutMapping("/")
	@Operation(summary = "Update Strand", description = "Update a strand")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Strand updated successfully"),
		@ApiResponse(responseCode = "404", description = "Strand not found"),
		@ApiResponse(responseCode = "500", description = "Failed to update strand")
	})
	@Parameters({
		@Parameter(name = "strandId", description = "ID of the strand to be updated", required = true)
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated strand object", required = true)
	public ResponseEntity<?> updateStrand(@RequestParam int strandId, @RequestBody StrandDTO strand) {
		ExecutionStatus status = strandService.updateStrand(strandId, strand.toEntity());
		switch (status) {
			case SUCCESS -> {
				// return 200 OK
				return ResponseEntity.ok(new MessageResponse("Strand updated successfully."));
			}
			case NOT_FOUND -> {
				// return 404 Not Found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Strand not found."));
			}
		}

		// return 500 Internal Server Error
		return ResponseEntity.status(500).body(new MessageResponse("Failed to update strand."));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get Strand by ID", description = "Get a strand by ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Strand object"),
		@ApiResponse(responseCode = "404", description = "Strand not found")
	})
	public ResponseEntity<?> getStrand(@PathVariable int id) {
		Strand strand = strandService.getStrand(id);
		if (strand == null) {
			// return 404 Not Found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Strand not found."));
		}

		// return 200 OK
		return ResponseEntity.ok(strand.toDTO());
	}

	@GetMapping("/")
	@Operation(summary = "Get All Strands", description = "Get all strands")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "List of strands")
	})
	@Parameters({
		@Parameter(name = "page", description = "Page number", required = true),
		@Parameter(name = "size", description = "Number of items per page", required = true)
	})
	public ResponseEntity<?> getAllStrands(int page, int size) {
		return ResponseEntity.ok(strandService.getAllStrands(PageRequest.of(page, size)));
	}
}