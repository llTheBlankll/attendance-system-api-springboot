

package com.pshs.attendance_system.controllers.v1.exposed;

import com.pshs.attendance_system.dto.LoginDTO;
import com.pshs.attendance_system.dto.LoginToken;
import com.pshs.attendance_system.dto.MessageResponse;
import com.pshs.attendance_system.dto.ResponseMessageV2;
import com.pshs.attendance_system.entities.User;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.enums.ResponseStatus;
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
			new MessageResponse("Request Body is required.")
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
				new Date(System.currentTimeMillis() + jwtService.getExpirationTime())
			);

			// Update user last login time
			ExecutionStatus status = userService.updateUserLastLogin(user.getId(), Instant.now());

			// Check if the last login time was updated successfully and return the token
			if (status == ExecutionStatus.SUCCESS) {
				return ResponseEntity.ok(loginToken);
			}

			// Return error response if the last login time was not updated which should not happen
			return ResponseEntity.badRequest().body(
				new MessageResponse("An error occurred while processing your request.")
			);
		} catch (BadCredentialsException e) { // Catch bad credentials exception
			return ResponseEntity.badRequest().body(
				new MessageResponse("Invalid username or password.")
			);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(
				new MessageResponse("ERR: " + e.getMessage())
			);
		}
	}

	@PostMapping("/is-valid")
	public ResponseEntity<?> isTokenValid(@RequestParam String token, @RequestParam String username) {
		boolean isValid = jwtService.isTokenValidAndNotExpired(token, username);
		if (!isValid) {
			return ResponseEntity.badRequest().body(new ResponseMessageV2("INVALID", ResponseStatus.INVALID));
		}

		return ResponseEntity.ok(new ResponseMessageV2("VALID", ResponseStatus.VALID));
	}

	@PostMapping("/is-expired")
	public ResponseEntity<?> isTokenExpired(@RequestParam String token) {
		boolean isExpired = jwtService.isTokenExpired(token);
		if (isExpired) {
			return ResponseEntity.badRequest().body(new ResponseMessageV2("EXPIRED", ResponseStatus.INVALID));
		}

		return ResponseEntity.ok(new ResponseMessageV2("NOT EXPIRED", ResponseStatus.VALID));
	}
}