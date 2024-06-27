package com.pshs.attendance_system.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pshs.attendance_system.dto.TeacherDTO;
import com.pshs.attendance_system.dto.UserDTO;
import com.pshs.attendance_system.dto.transaction.CreateTeacherDTO;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.services.TeacherService;
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
import org.springframework.util.LinkedMultiValueMap;

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

	@Autowired
	private TeacherService teacherService;

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

		// Convert DTO to JSON
		CreateTeacherDTO createTeacher = new CreateTeacherDTO(teacherDTO);
		String createJson = mapper.writeValueAsString(createTeacher);

		// Create the teacher
		mock.perform(
				MockMvcRequestBuilders
					.post("/api/v1/teachers/create")
					.content(createJson)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Teacher record created successfully."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()));

		// Test if the teacher is invalid
		teacherDTO.setFirstName(null);
		teacherDTO.setLastName(null);
		String json = mapper.writeValueAsString(teacherDTO);

		mock.perform(MockMvcRequestBuilders.post("/api/v1/teachers/create")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation error occurred."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()));
	}

	@Test
	public void testDeleteTeacher() throws Exception {
		logger.info("Testing deleteTeacher() function in Teacher Controller");

		// Delete the teacher
		mock.perform(MockMvcRequestBuilders.delete("/api/v1/teachers/2")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Teacher record deleted successfully."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()));

		// Missing teacher test
		mock.perform(MockMvcRequestBuilders.delete("/api/v1/teachers/100000")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Teacher record not found."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.NOT_FOUND.name()));

		// Negative number
		mock.perform(MockMvcRequestBuilders.delete("/api/v1/teachers/-1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Failed to delete teacher record."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.FAILED.name()));
	}

	@Test
	public void testUpdateTeacher() throws Exception {
		logger.info("Testing updateTeacher() function in Teacher Controller");

		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setId(1);
		teacherDTO.setFirstName("John 2");
		teacherDTO.setLastName("Doe 2");
		teacherDTO.setSex("Female");
		teacherDTO.setUser(userService.getUser(2).toDTO());

		// Update the teacher
		mock.perform(
				MockMvcRequestBuilders.put("/api/v1/teachers/2")
					.content(mapper.writeValueAsString(teacherDTO))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Teacher record updated successfully."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()));

		// Missing teacher test
		mock.perform(MockMvcRequestBuilders.put("/api/v1/teachers/100000")
				.content(mapper.writeValueAsString(teacherDTO))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Teacher record not found."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.NOT_FOUND.name()));

		// negative teacher test
		mock.perform(MockMvcRequestBuilders.put("/api/v1/teachers/-1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(teacherDTO)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Failed to update teacher record."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.FAILED.name()));
	}

	@Test
	public void testGetTeacherByUserId() throws Exception {
		logger.info("Testing getTeacher() function in Teacher Controller");

		// Get the teacher
		mock.perform(MockMvcRequestBuilders.get("/api/v1/teachers/user/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

		// Missing teacher test
		mock.perform(MockMvcRequestBuilders.get("/api/v1/teachers/user/100000")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Teacher record not found."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.NOT_FOUND.name()));
	}

	@Test
	public void testSearchTeacher() throws Exception {
		logger.info("Testing searchTeacher() function in Teacher Controller");

		// Params
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("firstName", "Shepperd");
		params.set("page", "0");
		params.set("size", "10");

		// Test search by first name
		mock.perform(MockMvcRequestBuilders.get("/api/v1/teachers/search?firstName=Shepperd&page=0&size=10"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

		// Set new param
		params.remove("firstName");
		params.set("lastName", "Jera");

		// Test search by last name
		mock.perform(MockMvcRequestBuilders.get("/api/v1/teachers/search?lastName=Jera&page=0&size=10"))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());

		// Clear params and set the sex param
		params.remove("lastName");
		params.set("sex", "Male");

		// Test search by sex
		mock.perform(MockMvcRequestBuilders.get("/api/v1/teachers/search?sex=Male&page=0&size=10")
				.params(params))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testCountAllTeachers() throws Exception {
		logger.info("Testing countAllTeachers() function in Teacher Controller");
		mock.perform(MockMvcRequestBuilders.get("/api/v1/teachers/count/all"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()));
	}
}