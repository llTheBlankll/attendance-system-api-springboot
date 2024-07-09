

package com.pshs.attendance_system.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pshs.attendance_system.dto.MessageResponse;
import com.pshs.attendance_system.dto.StrandDTO;
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

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Transactional
class StrandControllerTest {

	private static final Logger logger = LogManager.getLogger(StrandControllerTest.class);
	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	protected MockMvc mock;

	@BeforeEach
	void setUp() {
		logger.info("Strand Controller Test Started");
		// Register JavaTimeModule to ObjectMapper
		this.mapper.registerModule(
			new JavaTimeModule()
		);
	}

	@Test
	public void getAllStrands() throws Exception {
		logger.info("Testing getAllStrands");
		// Set Required Params
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.set("page", "0");
		params.set("size", "10");

		mock.perform(
				MockMvcRequestBuilders.get("/api/v1/strands/all").params(params)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void getStrandById() throws Exception {
		logger.info("Testing getStrandById");
		mock.perform(
				MockMvcRequestBuilders.get("/api/v1/strands/1")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		mock.perform(
				MockMvcRequestBuilders.get("/api/v1/strands/100")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void updateStrand() throws Exception {
		int STRAND_ID = 1;
		MessageResponse SUCCESS_MESSAGE = new MessageResponse("Strand updated successfully.", ExecutionStatus.SUCCESS);
		MessageResponse VALIDATION_ERROR_MESSAGE = new MessageResponse("Strand not found.", ExecutionStatus.VALIDATION_ERROR);

		StrandDTO strandDTO = new StrandDTO();
		strandDTO.setId(STRAND_ID);
		strandDTO.setName("Strand 1");

		logger.info("Testing updateStrand");

		// Success Test
		mock.perform(
				MockMvcRequestBuilders.put("/api/v1/strands/" + STRAND_ID)
					.contentType(MediaType.APPLICATION_JSON)
					.content(
						mapper.writeValueAsString(strandDTO)
					)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(SUCCESS_MESSAGE.getMessage()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(SUCCESS_MESSAGE.getStatus().toString()));

		// Not Found Test
		mock.perform(
				MockMvcRequestBuilders.put("/api/v1/strands/100")
					.contentType(MediaType.APPLICATION_JSON)
					.content(
						mapper.writeValueAsString(strandDTO)
					)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(VALIDATION_ERROR_MESSAGE.getMessage()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(VALIDATION_ERROR_MESSAGE.getStatus().toString()));
	}
}