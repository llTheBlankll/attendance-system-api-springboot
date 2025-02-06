

package com.pshs.attendance_system.app.users.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pshs.attendance_system.app.users.models.dto.UserSearchInput;
import com.pshs.attendance_system.models.MessageResponse;
import com.pshs.attendance_system.app.users.models.dto.UserInput;
import com.pshs.attendance_system.app.users.models.dto.UserDTO;
import com.pshs.attendance_system.app.authentication.models.dto.ChangePasswordDTO;
import com.pshs.attendance_system.app.users.models.entities.User;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.users.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Log4j2
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/create")
	public ResponseEntity<MessageResponse> createUser(@RequestBody UserInput userInput) throws JsonProcessingException {
		User status = userService.createUser(userInput.toEntity());

		if (status == null) {
			return ResponseEntity.badRequest().body(
				new MessageResponse(
					"User already exists or invalid data",
					ExecutionStatus.VALIDATION_ERROR
				)
			);
		}

		return ResponseEntity.ok(
			new MessageResponse(
				"User created successfully",
				ExecutionStatus.SUCCESS
			)
		);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteUser(@PathVariable Integer id) {
		log.info("Deleting user with ID: {}", id);
		ExecutionStatus status = userService.deleteUser(id);

		return switch (status) {
			case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new MessageResponse(
					"User does not exist",
					status
				)
			);
			case SUCCESS -> ResponseEntity.ok(
				new MessageResponse(
					"User deleted successfully",
					status
				)
			);
			default -> ResponseEntity.badRequest().body(
				new MessageResponse(
					"Failed to delete user",
					status
				)
			);
		};
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> updateUser(@PathVariable Integer id, @RequestBody UserInput userInput) {
		log.info("Updating user with ID: {}", id);
		ExecutionStatus status = userService.updateUser(id, userInput.toEntity());

		return switch (status) {
			case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new MessageResponse(
					"User does not exist",
					ExecutionStatus.NOT_FOUND
				)
			);

			case SUCCESS -> ResponseEntity.ok(
				new MessageResponse(
					"User updated successfully",
					ExecutionStatus.SUCCESS
				)
			);

			default -> ResponseEntity.internalServerError().body(
				new MessageResponse(
					"Failed to update user",
					status
				)
			);
		};
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable Integer id) {
		log.info("Retrieving user with ID: {}", id);
		User user = userService.getUser(id);

		// User does not exist
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new MessageResponse(
					"User does not exist",
					ExecutionStatus.NOT_FOUND
				)
			);
		}

		// Success
		return ResponseEntity.ok(
			user.toDTO()
		);
	}

	@GetMapping("")
	public ResponseEntity<Page<UserDTO>> getAllUsers(@RequestParam int page, @RequestParam int size) {
		log.info("Retrieving all users");
		return ResponseEntity.ok(
			userService.getAllUsers(page, size).map(User::toDTO)
		);
	}

	@GetMapping("/count")
	public ResponseEntity<?> countAllUsers() {
		log.info("Counting all users");
		return ResponseEntity.ok(
			userService.countAllUsers()
		);
	}

	@PatchMapping("/{id}/password")
	public ResponseEntity<MessageResponse> updatePassword(@PathVariable Integer id, @RequestBody ChangePasswordDTO changePasswordDTO) {
		log.info("Updating password for user with ID: {}", id);

		// Checks if the old password is correct
		Boolean isPasswordCorrect = userService.isUserPasswordMatch(id, changePasswordDTO.getOldPassword());
		if (!isPasswordCorrect) {
			return ResponseEntity.badRequest().body(
				new MessageResponse(
					"Password is incorrect",
					ExecutionStatus.VALIDATION_ERROR
				)
			);
		}

		// Updates the password
		ExecutionStatus status = userService.updateUserPassword(id, changePasswordDTO.getNewPassword());
		return switch (status) {
			case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new MessageResponse(
					"User does not exist",
					ExecutionStatus.NOT_FOUND
				)
			);

			case SUCCESS -> ResponseEntity.ok(
				new MessageResponse(
					"Password updated successfully",
					ExecutionStatus.SUCCESS
				)
			);

			default -> ResponseEntity.badRequest().body(
				new MessageResponse(
					"Failed to update password",
					status
				)
			);
		};
	}

	@PatchMapping("/{id}/is-locked")
	public ResponseEntity<MessageResponse> updateIsLocked(@PathVariable Integer id, @RequestParam boolean isLocked) {
		log.info("Updating isLocked for user with ID: {}", id);
		ExecutionStatus status = userService.changeUserLockStatus(id, isLocked);

		return switch (status) {
			case VALIDATION_ERROR -> ResponseEntity.badRequest().body(
				new MessageResponse(
					"User id might be invalid",
					ExecutionStatus.VALIDATION_ERROR
				)
			);

			case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new MessageResponse(
					"User does not exist",
					ExecutionStatus.NOT_FOUND
				)
			);

			case SUCCESS -> ResponseEntity.ok(
				new MessageResponse(
					"User lock status updated successfully",
					ExecutionStatus.SUCCESS
				)
			);

			default -> ResponseEntity.badRequest().body(
				new MessageResponse(
					"Failed to update user lock status",
					status
				)
			);
		};
	}

	@PatchMapping("/{id}/is-enabled")
	public ResponseEntity<MessageResponse> updateIsActive(@PathVariable Integer id, @RequestParam boolean enabled) {
		log.info("Updating isActive for user with ID: {}", id);
		ExecutionStatus status = userService.changeUserEnabledStatus(id, enabled);

		return switch (status) {
			case VALIDATION_ERROR -> ResponseEntity.badRequest().body(
				new MessageResponse(
					"User id might be invalid",
					ExecutionStatus.VALIDATION_ERROR
				)
			);

			case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new MessageResponse(
					"User does not exist",
					ExecutionStatus.NOT_FOUND
				)
			);

			case SUCCESS -> ResponseEntity.ok(
				new MessageResponse(
					"User enabled status updated successfully",
					ExecutionStatus.SUCCESS
				)
			);

			default -> ResponseEntity.badRequest().body(
				new MessageResponse(
					"Failed to update user enabled status",
					status
				)
			);
		};
	}

	@PatchMapping("/{id}/is-expired")
	public ResponseEntity<MessageResponse> isUserExpired(@PathVariable Integer id, @RequestParam boolean expired) {
		log.info("Checking if user with ID: {} is expired", id);
		ExecutionStatus status = userService.changeUserExpiredStatus(id, expired);
		return switch (status) {
			case VALIDATION_ERROR -> ResponseEntity.badRequest().body(
				new MessageResponse(
					"User id might be invalid",
					ExecutionStatus.VALIDATION_ERROR
				)
			);

			case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new MessageResponse(
					"User does not exist",
					ExecutionStatus.NOT_FOUND
				)
			);

			case SUCCESS -> ResponseEntity.ok(
				new MessageResponse(
					"User expired status updated successfully",
					ExecutionStatus.SUCCESS
				)
			);

			default -> ResponseEntity.badRequest().body(
				new MessageResponse(
					"Failed to update user expired status",
					status
				)
			);
		};
	}

	@PatchMapping("/{id}/is-credentials-expired")
	public ResponseEntity<MessageResponse> isUserCredentialsExpired(@PathVariable Integer id, @RequestParam boolean credentialsExpired) {
		log.info("Checking if user with ID: {} has expired credentials", id);
		ExecutionStatus status = userService.changeUserCredentialsExpiredStatus(id, credentialsExpired);

		return switch (status) {
			case VALIDATION_ERROR -> ResponseEntity.badRequest().body(
				new MessageResponse(
					"User ID might be invalid",
					ExecutionStatus.VALIDATION_ERROR
				)
			);

			case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new MessageResponse(
					"User does not exist",
					ExecutionStatus.NOT_FOUND
				)
			);

			case SUCCESS -> ResponseEntity.ok(
				new MessageResponse(
					"User credentials expired status updated successfully",
					ExecutionStatus.SUCCESS
				)
			);

			default -> ResponseEntity.badRequest().body(
				new MessageResponse(
					"Failed to update user credentials expired status",
					status
				)
			);
		};
	}

	@GetMapping("/search")
	public ResponseEntity<Page<UserDTO>> searchUsers(
		@RequestBody UserSearchInput searchInput,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		try {
			Page<User> result = userService.search(searchInput, PageRequest.of(page, size));

			return ResponseEntity.ok(result.map(User::toDTO));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}