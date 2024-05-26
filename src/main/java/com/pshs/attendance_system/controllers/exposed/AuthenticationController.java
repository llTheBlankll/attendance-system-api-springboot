/*
 * COPYRIGHT (C) 2024 VINCE ANGELO BATECAN
 *
 * PERMISSION IS HEREBY GRANTED, FREE OF CHARGE, TO STUDENTS, FACULTY, AND STAFF OF PUNTURIN SENIOR HIGH SCHOOL TO USE THIS SOFTWARE FOR EDUCATIONAL PURPOSES ONLY.
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL COPIES OR SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 *
 * MODIFICATIONS:
 *
 * ANY MODIFICATIONS OR DERIVATIVE WORKS OF THE SOFTWARE SHALL BE CONSIDERED PART OF THE SOFTWARE AND SHALL BE SUBJECT TO THE TERMS AND CONDITIONS OF THIS LICENSE.
 * ANY PERSON OR ENTITY MAKING MODIFICATIONS TO THE SOFTWARE SHALL ASSIGN AND TRANSFER ALL RIGHT, TITLE, AND INTEREST IN AND TO SUCH MODIFICATIONS TO VINCE ANGELO BATECAN.
 * VINCE ANGELO BATECAN SHALL OWN ALL INTELLECTUAL PROPERTY RIGHTS IN AND TO SUCH MODIFICATIONS.
 *
 * NO COMMERCIAL USE:
 *
 * THE SOFTWARE SHALL NOT BE SOLD, RENTED, LEASED, OR OTHERWISE COMMERCIALLY EXPLOITED. THE SOFTWARE IS INTENDED FOR PERSONAL, NON-COMMERCIAL USE ONLY WITHIN PUNTURIN SENIOR HIGH SCHOOL.
 *
 * NO WARRANTIES:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.pshs.attendance_system.controllers.exposed;

import com.pshs.attendance_system.dto.ErrorResponse;
import com.pshs.attendance_system.dto.LoginDTO;
import com.pshs.attendance_system.dto.LoginToken;
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
@RequestMapping("/auth")
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
			new ErrorResponse("Request Body is required.")
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
				new ErrorResponse("An error occurred while processing your request.")
			);
		} catch (BadCredentialsException e) { // Catch bad credentials exception
			return ResponseEntity.badRequest().body(
				new ErrorResponse("Invalid username or password.")
			);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(
				new ErrorResponse("ERR: " + e.getMessage())
			);
		}
	}
}