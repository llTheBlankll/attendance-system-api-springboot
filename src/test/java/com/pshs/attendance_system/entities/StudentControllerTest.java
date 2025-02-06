package com.pshs.attendance_system.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pshs.attendance_system.app.students.models.dto.StudentDTO;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.gradelevels.models.entities.GradeLevel;
import com.pshs.attendance_system.app.guardians.models.entities.Guardian;
import com.pshs.attendance_system.app.sections.models.entities.Section;
import com.pshs.attendance_system.app.gradelevels.services.GradeLevelService;
import com.pshs.attendance_system.app.guardians.services.GuardianService;
import com.pshs.attendance_system.app.sections.services.SectionService;
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

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class StudentControllerTest {

	private static final Logger logger = LogManager.getLogger(StudentControllerTest.class);

	@Autowired
	private MockMvc mock;

	@Autowired
	private SectionService sectionService;

	@Autowired
	private GradeLevelService gradeLevelService;

	@Autowired
	private GuardianService guardianService;

	private final ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		logger.info("Setting up Student Implementation Test");
		mapper.registerModule(
			new JavaTimeModule()
		);
	}

	@Test
	public void testGetAllStudents() throws Exception {
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("page", "0");
		params.set("size", "10");

		mock.perform(MockMvcRequestBuilders.get("/api/v1/students/all")
				.params(params))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

		logger.info("All Students are acquired successfully");
	}

	@Test
	public void testCreateStudent() throws Exception {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setId(592152315L);
		studentDTO.setFirstName("Vince Angelo");
		studentDTO.setLastName("Batecan");
		studentDTO.setBirthdate(LocalDate.from(LocalDate.of(2004, 7, 7)));
		studentDTO.setAddress("1234 Punturin St. Punturin, Valenzuela City");
		studentDTO.setSex("Male");
		studentDTO.setGradeLevel(
			gradeLevelService.getGradeLevel(2).map(GradeLevel::toDTO).orElse(null)
		);
		studentDTO.setSection(
			sectionService.getSection(2).map(Section::toDTO).orElse(null)
		);
		studentDTO.setGuardian(
			guardianService.getGuardian(1).map(Guardian::toDTO).orElse(null)
		);

		mock.perform(MockMvcRequestBuilders.post("/api/v1/students/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(studentDTO)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Student is created successfully"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.SUCCESS.name()));
		logger.info("Student is created successfully");

		// Testing for empty ID
		studentDTO.setId(null);
		mock.perform(MockMvcRequestBuilders.post("/api/v1/students/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(studentDTO)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Student is not valid"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()));
		logger.info("Student with ID is null is not valid and failed to be created!");

		// Testing for empty firstName
		studentDTO.setId(592152315L);
		studentDTO.setFirstName(null);
		mock.perform(MockMvcRequestBuilders.post("/api/v1/students/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(studentDTO)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Student is not valid"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()));
		logger.info("Student with firstName is null is not valid and failed to be created!");

		// Testing for empty lastName
		studentDTO.setFirstName("Vince Angelo");
		studentDTO.setLastName(null);
		mock.perform(MockMvcRequestBuilders.post("/api/v1/students/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(studentDTO)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Student is not valid"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.VALIDATION_ERROR.name()));
		logger.info("Student with lastName is null is not valid and failed to be created!");

		// Testing for existing student
		studentDTO.setId(1807176L);
		studentDTO.setLastName("Batecan");
		mock.perform(MockMvcRequestBuilders.post("/api/v1/students/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(studentDTO)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Student already exists"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(ExecutionStatus.FAILED.name()));
		logger.info("Student already exists and failed to be created!");
		logger.info("Student creation is tested successfully!");
	}

	@Test
	public void testDeleteStudent() throws Exception {

	}
}