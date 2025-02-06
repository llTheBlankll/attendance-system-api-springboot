package com.pshs.attendance_system.app.rfid_credentials.controllers;

import com.pshs.attendance_system.app.rfid_credentials.models.dto.RFIDCredentialDTO;
import com.pshs.attendance_system.models.MessageResponse;
import com.pshs.attendance_system.app.students.models.dto.StudentDTO;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.rfid_credentials.services.RFIDCredentialService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rfid-credentials")
@Tag(name = "RFID Credentials", description = "Operations pertaining to RFID credentials")
public class RFIDController {

	private final RFIDCredentialService rfidCredentialService;

	public RFIDController(RFIDCredentialService rfidCredentialService) {
		this.rfidCredentialService = rfidCredentialService;
	}

	@PostMapping("/")
	public ResponseEntity<?> createRFIDCredential(@RequestBody RFIDCredentialDTO rfidCredentialDTO) {
		if (rfidCredentialDTO == null) {
			return ResponseEntity.badRequest().body("RFID Credential is required");
		}

		ExecutionStatus status = rfidCredentialService.createRFIDCredential(rfidCredentialDTO.toEntity());
		switch (status) {
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Validation Error", status));
			}
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("RFID Credential created successfully", status));
			}
			default -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Failed to create RFID Credential", status));
			}
		}
	}

	@DeleteMapping("/")
	public ResponseEntity<?> deleteRFIDCredential(@RequestBody RFIDCredentialDTO rfidCredentialDTO) {
		if (rfidCredentialDTO == null) {
			return ResponseEntity.badRequest().body("RFID Credential is required");
		}

		ExecutionStatus status = rfidCredentialService.deleteRFIDCredential(rfidCredentialDTO.toEntity());
		switch (status) {
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Validation Error", status));
			}
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("RFID Credential deleted successfully", status));
			}
			default -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Failed to delete RFID Credential", status));
			}
		}
	}

	@PutMapping("/")
	public ResponseEntity<?> updateRFIDCredential(@RequestBody RFIDCredentialDTO credentialDTO) {
		if (credentialDTO == null) {
			return ResponseEntity.badRequest().body("RFID Credential is required");
		}

		ExecutionStatus status = rfidCredentialService.updateRFIDCredential(credentialDTO.toEntity());
		switch (status) {
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Validation Error", status));
			}
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("RFID Credential updated successfully", status));
			}
			default -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Failed to update RFID Credential", status));
			}
		}
	}

	@GetMapping("/student/{studentId}")
	public ResponseEntity<?> getRFIDCredentialByStudentId(@PathVariable Long studentId) {
		if (studentId == null) {
			return ResponseEntity.badRequest().body("Student ID is required");
		}

		RFIDCredentialDTO credentialDTO = rfidCredentialService.getRFIDCredentialByStudentId(studentId).toDTO();

		if (credentialDTO.getId() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("RFID Credential not found", ExecutionStatus.NOT_FOUND));
		}

		return ResponseEntity.ok(credentialDTO);
	}

	@PostMapping("/student")
	public ResponseEntity<?> getRFIDCredentialByStudent(@RequestBody StudentDTO student) {
		if (student == null) {
			return ResponseEntity.badRequest().body("Student is required");
		}

		RFIDCredentialDTO credentialDTO = rfidCredentialService.getRFIDCredentialByStudent(student.toEntity()).toDTO();

		if (credentialDTO.getId() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("RFID Credential not found", ExecutionStatus.NOT_FOUND));
		}

		return ResponseEntity.ok(credentialDTO);
	}
}