

package com.pshs.attendance_system.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pshs.attendance_system.dto.UserCreationDTO;
import com.pshs.attendance_system.dto.authentication.ChangePasswordDTO;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

	private static final Logger logger = LogManager.getLogger(UserControllerTest.class);

	@Autowired
	protected MockMvc mock;

	private final int USER_ID = 3;
	private final ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		logger.info("Setting up User Implementation Test");
		this.mapper.registerModule(
			new JavaTimeModule()
		);
	}

	@Test
	public void testCreateUser() throws Exception {
		logger.info("Testing create user");
		UserCreationDTO userCreationDTO = new UserCreationDTO();

		userCreationDTO.setUsername("testCreate"); // test create is a non-existing user
		userCreationDTO.setPassword("test");
		userCreationDTO.setEmail("testCreate@gmail.com");
		userCreationDTO.setRole("TEACHER");
		userCreationDTO.setEnabled(true);
		userCreationDTO.setExpired(false);
		userCreationDTO.setCredentialsExpired(false);
		userCreationDTO.setLocked(false);

		logger.info(
			mapper.writeValueAsString(
				userCreationDTO
			)
		);

		// Test if a user is created
		mock.perform(MockMvcRequestBuilders.post("/api/v1/users/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					mapper.writeValueAsString(
						userCreationDTO
					)
				))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User created successfully"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

		// Test if user exists
		userCreationDTO.setUsername("test"); // test is an existing user in the database that has a role GUEST.
		mock.perform(MockMvcRequestBuilders.post("/api/v1/users/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					mapper.writeValueAsString(
						userCreationDTO
					)
				))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User already exists or invalid data"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
		logger.info("User was tested that it exists!!!");
		logger.info("User was tested that it was created successfully!!!");
	}

	@Test
	public void testDeleteUser() throws Exception {
		logger.info("Testing delete user");
		// Test if user exists
		mock.perform(MockMvcRequestBuilders.delete("/api/v1/users/100"))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User does not exist"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.NOT_FOUND.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

		mock.perform(MockMvcRequestBuilders.delete("/api/v1/users/1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User deleted successfully"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
		logger.info("User was tested that it does not exist!!!");
	}

	@Test
	public void testUpdateUser() throws Exception {
		logger.info("Testing update user");
		UserCreationDTO userCreationDTO = new UserCreationDTO();

		userCreationDTO.setUsername("testedUser");
		userCreationDTO.setPassword("tested!");
		userCreationDTO.setEmail("testUser@gmail.com");
		userCreationDTO.setRole("TEACHER");
		userCreationDTO.setEnabled(true);
		userCreationDTO.setExpired(false);
		userCreationDTO.setCredentialsExpired(false);
		userCreationDTO.setLocked(false);

		// Test if a user is updated
		mock.perform(MockMvcRequestBuilders.put("/api/v1/users/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					mapper.writeValueAsString(
						userCreationDTO
					)
				))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User updated successfully"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

		// Test if user not exists
		mock.perform(MockMvcRequestBuilders.put("/api/v1/users/100")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					mapper.writeValueAsString(
						userCreationDTO
					)
				))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User does not exist"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.NOT_FOUND.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	public void testGetUserById() throws Exception {
		logger.info("Testing get user by ID");
		// Test if user exists
		mock.perform(MockMvcRequestBuilders.get("/api/v1/users/1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

		// Test if user not exists
		mock.perform(MockMvcRequestBuilders.get("/api/v1/users/100"))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User does not exist"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.NOT_FOUND.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	public void getAllUsers() throws Exception {
		logger.info("Testing get all users");
		// Test if user exists
		mock.perform(MockMvcRequestBuilders.get("/api/v1/users?page=0&size=10"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	public void testUpdatePasswordOfUser() throws Exception {
		
		logger.info("Testing update password of user");
		ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("test", "test123");

		// Change password of user
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + USER_ID + "/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					mapper.writeValueAsString(changePasswordDTO)
				))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Password updated successfully"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

		// Test if user not exists
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/100/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					mapper.writeValueAsString(changePasswordDTO)
				))
			// We expect a bad request instead of not found
			// because we should not expose the fact
			// that a user does not exist
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Password is incorrect"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

		// Test if password is incorrect
		changePasswordDTO.setOldPassword("test123");
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/1/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					mapper.writeValueAsString(changePasswordDTO)
				))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Password is incorrect"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	public void testUpdateIsLockedUser() throws Exception {
		
		logger.info("Testing update isLocked of user");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("isLocked", Boolean.TRUE.toString());

		// Change isLocked of user
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + USER_ID + "/is-locked")
				.contentType(MediaType.APPLICATION_JSON)
				.params(params))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User lock status updated successfully"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

		// user is not found check
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + (USER_ID + 100) + "/is-locked")
				.params(
					params
				))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User does not exist"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.NOT_FOUND.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

		// Check if user negative
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/-1/is-locked")
				.params(
					params
				))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User id might be invalid"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	public void updateUserIsEnabled() throws Exception {
		
		logger.info("Testing update is enabled of user");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("enabled", Boolean.TRUE.toString());

		// Change isEnabled of user
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + USER_ID + "/is-enabled")
				.contentType(MediaType.APPLICATION_JSON)
				.params(params))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User enabled status updated successfully"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

		// user is not found check
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + (USER_ID + 100) + "/is-enabled")
				.params(
					params
				))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User does not exist"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.NOT_FOUND.name()));

		// Check if user negative
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/-1/is-enabled")
				.params(
					params
				))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User id might be invalid"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()));
	}

	@Test
	public void updateUserIsExpired() throws Exception {
		
		logger.info("Testing update is expired of user");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("expired", Boolean.TRUE.toString());

		// Change isExpired of user
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + USER_ID + "/is-expired")
				.contentType(MediaType.APPLICATION_JSON)
				.params(params))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User expired status updated successfully"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

		// user is not found check
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + (USER_ID + 100) + "/is-expired")
				.params(
					params
				))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User does not exist"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.NOT_FOUND.name()));

		// Check if user negative
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/-1/is-expired")
				.params(
					params
				))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User id might be invalid"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()));
	}

	@Test
	public void updateUserIsCredentialsExpired() throws Exception {
		int USER_ID = 1;
		logger.info("Testing update is credentials expired of user");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("credentialsExpired", Boolean.TRUE.toString());

		// Change isCredentialsExpired of user
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + USER_ID + "/is-credentials-expired")
				.contentType(MediaType.APPLICATION_JSON)
				.params(params))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User credentials expired status updated successfully"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

		// user is not found check
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/" + (USER_ID + 100) + "/is-credentials-expired")
				.params(
					params
				))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User does not exist"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.NOT_FOUND.name()));

		// Check if user negative
		mock.perform(MockMvcRequestBuilders.patch("/api/v1/users/-1/is-credentials-expired")
				.params(
					params
				))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User ID might be invalid"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()));
	}

	@Test
	public void testGetAllUsers() throws Exception {
		logger.info("Testing get all users");
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("page", "0");
		params.set("size", "10");

		mock.perform(MockMvcRequestBuilders.get("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.params(params))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	public void searchUsers() throws Exception {
		logger.info("Testing search users");
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("username", "test");
		params.set("email", "test2@gmail.com");
		params.set("role", "GUEST");
		params.set("page", "0");
		params.set("size", "10");

		mock.perform(MockMvcRequestBuilders.get("/api/v1/users/search")
				.contentType(MediaType.APPLICATION_JSON)
				.params(params))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}
}