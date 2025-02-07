

package com.pshs.attendance_system.app.authentication.controllers;

import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.models.MessageResponse;
import com.pshs.attendance_system.app.authentication.models.dto.LoginDTO;
import com.pshs.attendance_system.app.authentication.models.dto.LoginToken;
import com.pshs.attendance_system.app.users.models.entities.User;
import com.pshs.attendance_system.security.JWTService;
import com.pshs.attendance_system.app.authentication.services.AuthenticationService;
import com.pshs.attendance_system.app.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	private final JWTService jwtService;
	private final UserService userService;

	public AuthenticationController(AuthenticationService authenticationService, JWTService jwtService, UserService userService) {
		this.authenticationService = authenticationService;
		this.jwtService = jwtService;
		this.userService = userService;
	}

	/**
	 * ! Handle HttpMessageNotReadableException, which is thrown when the request body is missing
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException() {
		return ResponseEntity.badRequest().body(
			new MessageResponse(
				"Request body is missing.",
				ExecutionStatus.INVALID
			)
		);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO login) {
		try {
			// Authenticate the user through authentication service
			User user = authenticationService.signIn(login);

			// Generate JWT token
			LoginToken loginToken = new LoginToken(
				user.getUsername(),
				jwtService.generateToken(user),
				user.getRole(),
				new Date(System.currentTimeMillis() + jwtService.getExpirationTime()),
				user.toDTO()
			);

			// Update user last login time
			ExecutionStatus status = userService.updateUserLastLogin(user.getId(), Instant.now());

			// Check if the last login time was updated successfully and return the token
			if (status == ExecutionStatus.SUCCESS) {
				return ResponseEntity.ok(loginToken);
			}

			// Return error response if the last login time was not updated which should not happen
			return ResponseEntity.badRequest().body(
				new MessageResponse(
					"Failed to update last login time.",
					ExecutionStatus.FAILED
				)
			);
		} catch (BadCredentialsException e) { // Catch bad credentials exception
			return ResponseEntity.badRequest().body(
				new MessageResponse(
					"Invalid username or password.",
					ExecutionStatus.INVALID
				)
			);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(
				new MessageResponse(
					"An error occurred: " + e.getMessage(),
					ExecutionStatus.FAILED
				)
			);
		}
	}

	@PostMapping("/is-valid")
	public ResponseEntity<?> isTokenValid(@RequestParam String token, @RequestParam String username) {
		boolean isValid = jwtService.isTokenValidAndNotExpired(token, username);
		if (!isValid) {
			return ResponseEntity.badRequest().body(new MessageResponse("INVALID", ExecutionStatus.INVALID));
		}

		return ResponseEntity.ok(new MessageResponse("VALID", ExecutionStatus.VALID));
	}

	@PostMapping("/is-expired")
	public ResponseEntity<?> isTokenExpired(@RequestParam String token) {
		boolean isExpired = jwtService.isTokenExpired(token);
		if (isExpired) {
			return ResponseEntity.badRequest().body(new MessageResponse("EXPIRED", ExecutionStatus.INVALID));
		}

		return ResponseEntity.ok(new MessageResponse("NOT EXPIRED", ExecutionStatus.VALID));
	}
}