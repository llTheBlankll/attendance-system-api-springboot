package com.pshs.attendance_system.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pshs.attendance_system.dto.TeacherDTO;
import com.pshs.attendance_system.dto.UserDTO;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.services.UserService;
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
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class TeacherControllerTest {

	private static final Logger logger = LogManager.getLogger(TeacherControllerTest.class);
	protected ObjectMapper mapper = new ObjectMapper();

	@Autowired
	protected MockMvc mock;

	@Autowired
	private UserService userService;

	@BeforeEach
	public void setUp() {
		logger.info("Setting up test");
		logger.info("Adding Java Time Module to Object Mapper");
		this.mapper.registerModule(
			new JavaTimeModule()
		);
	}

	@Test
	public void testCreateTeacher() throws Exception {
		logger.info("Testing createTeacher");

		UserDTO userDTO = userService.getUser(1).toDTO();
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setFirstName("John");
		teacherDTO.setLastName("Doe");
		teacherDTO.setSex("Male");
		teacherDTO.setUser(userDTO);
		String json = mapper.writeValueAsString(teacherDTO);

		// Create the teacher
		mock.perform(
			MockMvcRequestBuilders
					.post("/api/v1/teachers/create")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Teacher record created successfully."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()));

		// Test if teacher doesn't exist
		mock.perform(
			MockMvcRequestBuilders.post("/api/v1/teachers/create")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isConflict())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Failed to create teacher record."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.FAILED.name()));

		// Test if the teacher is invalid
		teacherDTO.setFirstName(null);
		teacherDTO.setLastName(null);
		json = mapper.writeValueAsString(teacherDTO);

		mock.perform(MockMvcRequestBuilders.post("/api/v1/teachers/create")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation error occurred."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()));
	}
}