

package com.pshs.attendance_system.app.strands.impl;

import com.pshs.attendance_system.app.strands.models.entities.Strand;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.strands.repositories.StrandRepository;
import com.pshs.attendance_system.app.strands.services.StrandService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class StrandServiceImpl implements StrandService {
	private final StrandRepository strandRepository;

	public StrandServiceImpl(StrandRepository strandRepository) {
		this.strandRepository = strandRepository;
	}

	/**
	 * Create a new strand record.
	 *
	 * @param strand Strand object to be created
	 * @return Execution AttendanceStatus (SUCCESS, FAILURE, or VALIDATION_ERROR)
	 */
	@Override
	public ExecutionStatus createStrand(Strand strand) {
		if (isExistingStrand(strand.getId())) {
			return strandExisted("Strand with ID " + strand.getId() + " already exists");
		}

		strandRepository.save(strand);
		log.debug("Strand with ID {} has been created", strand.getId());
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Delete a strand record.
	 *
	 * @param strandId ID of the strand to be deleted
	 * @return Execution AttendanceStatus (SUCCESS, FAILURE, or NOT_FOUND)
	 */
	@Override
	public ExecutionStatus deleteStrand(int strandId) {
		if (!isExistingStrand(strandId)) {
			return strandExisted("Strand with ID " + strandId + " does not exist");
		}

		strandRepository.deleteById(strandId);
		log.debug("Strand with ID {} has been deleted", strandId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update a strand record.
	 *
	 * @param strandId ID of the strand to be updated
	 * @param strand   Updated strand object
	 * @return Execution AttendanceStatus (SUCCESS, FAILURE, NOT_FOUND, or VALIDATION_ERROR)
	 */
	@Override
	public ExecutionStatus updateStrand(int strandId, Strand strand) {
		if (!isExistingStrand(strandId)) {
			return strandExisted("Strand with ID " + strandId + " does not exist");
		}

		strandRepository.save(strand);
		log.debug("Strand with ID {} has been updated", strandId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Retrieve a strand record.
	 *
	 * @param strandId ID of the strand to be retrieved
	 * @return Strand object if found, otherwise null
	 */
	@Override
	public Strand getStrand(int strandId) {
		return strandRepository.findById(strandId).orElse(null);
	}

	/**
	 * Retrieve all strand records.
	 *
	 * @return The page of strand records
	 */
	@Override
	public Page<Strand> getAllStrands(Pageable pageable) {
		return strandRepository.findAll(pageable);
	}

	/**
	 * Check if the strand exists in the database.
	 *
	 * @param strandId Strand ID
	 * @return True if the strand exists, otherwise false.
	 */
	private boolean isExistingStrand(int strandId) {
		return strandRepository.existsById(strandId);
	}

	/**
	 * This function is called when the strand already exists in the database.
	 * Was created to avoid code duplication.
	 *
	 * @param message Message to be logged
	 * @return ExecutionStatus.VALIDATION_ERROR
	 */
	private ExecutionStatus strandExisted(String message) {
		log.warn(message);
		return ExecutionStatus.VALIDATION_ERROR;
	}
}