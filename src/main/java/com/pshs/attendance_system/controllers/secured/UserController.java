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

package com.pshs.attendance_system.controllers.secured;

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
	private final String STATUS = "status";

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/")
	public ResponseEntity<Map<String, User>> createUser(@RequestBody UserCreationDTO userCreationDTO) {
		logger.info("Creating user: {}", userCreationDTO);
		User status = userService.createUser(userCreationDTO.toEntity());
		Map<String, User> statusMap = Map.of(STATUS, status);

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
		Map<String, ExecutionStatus> statusMap = Map.of(STATUS, status);

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
	public ResponseEntity<Page<UserDTO>> searchUsers(
		@RequestParam(required = false) String username,
		@RequestParam(required = false) String email,
		@RequestParam(required = false) String role,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		boolean isUsernameEmpty = username.isEmpty();
		boolean isEmailEmpty = email.isEmpty();
		boolean isRoleEmpty = role.isEmpty();
		Page<User> result;

		if (!isUsernameEmpty && isEmailEmpty && isRoleEmpty) {
			result = userService.searchUsersByUsername(username, page, size);
		} else if (isUsernameEmpty && !isEmailEmpty && isRoleEmpty) {
			result = userService.searchUsersByEmail(email, page, size);
		} else if (isUsernameEmpty && isEmailEmpty && !isRoleEmpty) {
			result = userService.searchUsersByRole(role, page, size);
		} else if (!isUsernameEmpty && !isEmailEmpty && isRoleEmpty) {
			result = userService.searchUsersByUsernameAndEmail(username, email, page, size);
		} else if (!isUsernameEmpty && isEmailEmpty) {
			result = userService.searchUsersByUsernameAndRole(username, role, page, size);
		} else if (isUsernameEmpty && !isEmailEmpty) {
			result = userService.searchUsersByEmailAndRole(email, role, page, size);
		} else {
			result = userService.searchUsersByUsernameAndEmailAndRole(username, email, role, page, size);
		}

		return ResponseEntity.ok(
			result.map(User::toDTO)
		);
	}
}