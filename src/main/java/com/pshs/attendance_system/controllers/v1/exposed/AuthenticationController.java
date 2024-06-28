

package com.pshs.attendance_system.controllers.v1.exposed;

import com.pshs.attendance_system.dto.*;
import com.pshs.attendance_system.dto.authentication.LoginDTO;
import com.pshs.attendance_system.dto.authentication.LoginToken;
import com.pshs.attendance_system.entities.User;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.security.JWTService;
import com.pshs.attendance_system.services.AuthenticationService;
import com.pshs.attendance_system.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	private final JWTService jwtService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public AuthenticationController(AuthenticationService authenticationService, JWTService jwtService, UserService userService, PasswordEncoder passwordEncoder) {
		this.authenticationService = authenticationService;
		this.jwtService = jwtService;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * ! Handle HttpMessageNotReadableException, which is thrown when the request body is missing
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException() {
		return ResponseEntity.badRequest().body(
			new StatusMessageResponse(
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
				new StatusMessageResponse(
					"Failed to update last login time.",
					ExecutionStatus.FAILED
				)
			);
		} catch (BadCredentialsException e) { // Catch bad credentials exception
			return ResponseEntity.badRequest().body(
				new StatusMessageResponse(
					"Invalid username or password.",
					ExecutionStatus.INVALID
				)
			);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(
				new StatusMessageResponse(
					"An error occurred.",
					ExecutionStatus.FAILED
				)
			);
		}
	}

	@PostMapping("/is-valid")
	public ResponseEntity<?> isTokenValid(@RequestParam String token, @RequestParam String username) {
		boolean isValid = jwtService.isTokenValidAndNotExpired(token, username);
		if (!isValid) {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("INVALID", ExecutionStatus.INVALID));
		}

		return ResponseEntity.ok(new StatusMessageResponse("VALID", ExecutionStatus.VALID));
	}

	@PostMapping("/is-expired")
	public ResponseEntity<?> isTokenExpired(@RequestParam String token) {
		boolean isExpired = jwtService.isTokenExpired(token);
		if (isExpired) {
			return ResponseEntity.badRequest().body(new StatusMessageResponse("EXPIRED", ExecutionStatus.INVALID));
		}

		return ResponseEntity.ok(new StatusMessageResponse("NOT EXPIRED", ExecutionStatus.VALID));
	}
}