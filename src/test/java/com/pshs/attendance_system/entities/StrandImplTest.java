

package com.pshs.attendance_system.entities;

import com.pshs.attendance_system.repositories.StrandRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class StrandImplTest {


	private static final Logger logger = LogManager.getLogger(StrandImplTest.class);
	private final StrandRepository strandRepository;


	@Autowired
	public StrandImplTest(StrandRepository strandRepository) {
		this.strandRepository = strandRepository;
	}

	@Test
	@Transactional
	@Rollback
	void testCreateStrand() {
		Strand strand = new Strand(
			null,
			"STEM TEST"
		);

		try {
			assert strandRepository.save(strand).equals(strand);
			logger.debug("Strand with ID {} has been created", strand.getId());
		} catch (Exception e) {
			logger.error("Error creating strand", e);
		}
	}

	@Test
	@Transactional
	@Rollback
	void testDeleteStrand() {
		Strand strand = new Strand(
			null,
			"STEM TEST"
		);

		try {
			strandRepository.save(strand);
			strandRepository.deleteById(strand.getId());

			// Check if exists
			assert strandRepository.existsById(strand.getId());
			logger.debug("Strand with ID {} has been deleted", strand.getId());
		} catch (Exception e) {
			logger.error("Error deleting strand", e);
		}
	}
}