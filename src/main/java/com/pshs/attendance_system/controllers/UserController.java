package com.pshs.attendance_system.controllers;

import com.pshs.attendance_system.dto.UserCreationDTO;
import com.pshs.attendance_system.dto.UserDTO;
import com.pshs.attendance_system.entities.User;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/")
	public ResponseEntity<?> createUser(@RequestBody UserCreationDTO userCreationDTO) {
		logger.info("Creating user: {}", userCreationDTO);
		ExecutionStatus status = userService.createUser(userCreationDTO.toEntity());
		Map<String, ExecutionStatus> statusMap = Map.of("status", status);

		return ResponseEntity.ok(
			statusMap
		);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, ExecutionStatus>> deleteUser(@PathVariable int id) {
		logger.info("Deleting user with ID: {}", id);
		ExecutionStatus status = userService.deleteUser(id);
		Map<String, ExecutionStatus> statusMap = Map.of("status", status);

		return ResponseEntity.ok(
			statusMap
		);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, ExecutionStatus>> updateUser(@PathVariable int id, @RequestBody UserCreationDTO userCreationDTO) {
		logger.info("Updating user with ID: {}", id);
		ExecutionStatus status = userService.updateUser(id, userCreationDTO.toEntity());
		Map<String, ExecutionStatus> statusMap = Map.of("status", status);

		return ResponseEntity.ok(
			statusMap
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
		logger.info("Retrieving user with ID: {}", id);
		return ResponseEntity.ok(
			userService.getUser(id).toDTO()
		);
	}

	@GetMapping("/")
	public ResponseEntity<Page<UserDTO>> getAllUsers(@RequestParam int page, @RequestParam int size) {
		logger.info("Retrieving all users");
		return ResponseEntity.ok(
			userService.getAllUsers(page, size).map(User::toDTO)
		);
	}

	@GetMapping("/count")
	public ResponseEntity<Map<String, Integer>> countAllUsers() {
		logger.info("Counting all users");
		return ResponseEntity.ok(
			Map.of("count", userService.countAllUsers())
		);
	}

	@PutMapping("/{id}/password")
	public ResponseEntity<Map<String, ExecutionStatus>> updatePassword(@PathVariable int id, @RequestParam String password) {
		logger.info("Updating password for user with ID: {}", id);
		ExecutionStatus status = userService.updateUserPassword(id, password);
		Map<String, ExecutionStatus> statusMap = Map.of("status", status);

		return ResponseEntity.ok(
			statusMap
		);
	}

	@PutMapping("/{id}/is-locked")
	public ResponseEntity<Map<String, ExecutionStatus>> updateIsLocked(@PathVariable int id, @RequestParam boolean isLocked) {
		logger.info("Updating isLocked for user with ID: {}", id);
		ExecutionStatus status = userService.changeUserLockStatus(id, isLocked);
		Map<String, ExecutionStatus> statusMap = Map.of("status", status);

		return ResponseEntity.ok(
			statusMap
		);
	}

	@PutMapping("/{id}/is-enabled")
	public ResponseEntity<Map<String, ExecutionStatus>> updateIsActive(@PathVariable int id, @RequestParam boolean enabled) {
		logger.info("Updating isActive for user with ID: {}", id);
		ExecutionStatus status = userService.changeUserEnabledStatus(id, enabled);
		Map<String, ExecutionStatus> statusMap = Map.of("status", status);

		return ResponseEntity.ok(
			statusMap
		);
	}

	@GetMapping("/{id}/is-expired")
	public ResponseEntity<Map<String, ExecutionStatus>> isUserExpired(@PathVariable int id, @RequestParam boolean expired) {
		logger.info("Checking if user with ID: {} is expired", id);
		return ResponseEntity.ok(
			Map.of("status", userService.changeUserExpiredStatus(id, expired))
		);
	}

	@GetMapping("/{id}/is-credentials-expired")
	public ResponseEntity<Map<String, ExecutionStatus>> isUserCredentialsExpired(@PathVariable int id, @RequestParam boolean credentialsExpired) {
		logger.info("Checking if user with ID: {} has expired credentials", id);
		return ResponseEntity.ok(
			Map.of("status", userService.changeUserCredentialsExpiredStatus(id, credentialsExpired))
		);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<UserDTO>> searchUsers(@RequestParam(required = false) String username,
	                                                 @RequestParam(required = false) String email,

	                                                 @RequestParam int page, @RequestParam int size) {

	}


}