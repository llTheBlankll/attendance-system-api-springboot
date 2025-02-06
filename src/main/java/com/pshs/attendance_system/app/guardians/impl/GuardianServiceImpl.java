

package com.pshs.attendance_system.app.guardians.impl;

import com.pshs.attendance_system.app.guardians.models.entities.Guardian;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.guardians.repositories.GuardianRepository;
import com.pshs.attendance_system.app.guardians.services.GuardianService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuardianServiceImpl implements GuardianService {

	private static final Logger logger = LogManager.getLogger(GuardianServiceImpl.class);
	private final GuardianRepository guardianRepository;


	public GuardianServiceImpl(GuardianRepository guardianRepository) {
		this.guardianRepository = guardianRepository;
	}

	/**
	 * Create a new guardian record.
	 *
	 * @param guardian Guardian Object
	 * @return ExecutionStatus (SUCCESS, FAILURE, VALIDATION_ERROR)
	 * @see ExecutionStatus
	 */
	@Override
	public ExecutionStatus createGuardian(Guardian guardian) {
		// Validate the guardian object
		if (isGuardianNotValid(guardian)) {
			return guardianValidationFailedLog(guardian);
		}

		try {
			// Check if guardian already exists
			if (isGuardianExist(guardian.getId())) {
				logger.debug("Guardian {} already exists.", guardian.getFullName());
				return ExecutionStatus.VALIDATION_ERROR;
			}

			// Save the guardian record
			guardianRepository.save(guardian);
			return ExecutionStatus.SUCCESS;
		} catch (Exception e) {
			// Log the error
			logger.debug("Failed to create guardian record.", e);
			return ExecutionStatus.FAILED;
		}
	}

	/**
	 * Delete the guardian record. Requires the guardian id.
	 *
	 * @param guardianId Guardian ID
	 * @return ExecutionStatus (SUCCESS, NOT_FOUND)
	 * @see ExecutionStatus
	 */
	@Override
	public ExecutionStatus deleteGuardian(int guardianId) {
		if (guardianId <= 0) {
			return guardianInvalidIdLog(guardianId);
		}

		try {
			// Check if guardian exists
			if (!isGuardianExist(guardianId)) {
				return guardianNotFoundLog(guardianId);
			}

			// Delete the guardian record
			guardianRepository.deleteById(guardianId);
			return ExecutionStatus.SUCCESS;
		} catch (Exception e) {
			// Log the error
			logger.debug("Failed to delete guardian record.", e);
			return ExecutionStatus.FAILED;
		}
	}

	/**
	 * Update the guardian record. Requires the guardian id, and a new guardian object with the updated values.
	 *
	 * @param guardianId Guardian ID
	 * @param guardian   Updated Guardian Object
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND, VALIDATION_ERROR)
	 * @see ExecutionStatus
	 */
	@Override
	public ExecutionStatus updateGuardian(int guardianId, Guardian guardian) {
		// Validate the guardian object
		if (isGuardianNotValid(guardian)) {
			return guardianValidationFailedLog(guardian);
		}

		if (guardianId <= 0) {
			return guardianInvalidIdLog(guardianId);
		}

		try {
			// Check if guardian exists
			if (!isGuardianExist(guardianId)) {
				return guardianNotFoundLog(guardianId);
			}

			// Update the guardian record
			guardian.setId(guardianId);
			guardianRepository.save(guardian);
			return ExecutionStatus.SUCCESS;
		} catch (Exception e) {
			// Log the error
			logger.debug("Failed to update guardian record.", e);
			return ExecutionStatus.FAILED;
		}
	}

	/**
	 * Get all guardian records.
	 *
	 * @param page Page
	 * @return Page containing guardian records
	 */
	@Override
	public Page<Guardian> getAllGuardian(Pageable page) {
		return guardianRepository.findAll(page);
	}

	/**
	 * Get the guardian record by the guardian id.
	 *
	 * @param guardianId Guardian ID
	 * @return Guardian Object, null if not found
	 */
	@Override
	public Optional<Guardian> getGuardian(int guardianId) {
		return guardianRepository.findById(guardianId);
	}

	/**
	 * Search guardian records by full name.
	 *
	 * @param fullName Full Name
	 * @param page     Page
	 * @return Page containing guardian records
	 * @see Guardian
	 */
	@Override
	public Page<Guardian> searchGuardianByFullName(String fullName, Pageable page) {
		return guardianRepository.searchGuardianByFullName(fullName, page);
	}

	/**
	 * Search guardian records by contact number.
	 *
	 * @param contactNumber Contact Number
	 * @param page          Page
	 * @return Page containing guardian records
	 * @see Guardian
	 */
	@Override
	public Page<Guardian> searchGuardianByContactNumber(String contactNumber, Pageable page) {
		return guardianRepository.searchGuardianByContactNumber(contactNumber, page);
	}

	/**
	 * Search the guardian by full name and contact number.
	 *
	 * @param fullName      The full name of the guardian
	 * @param contactNumber The contact number of the guardian
	 * @param page          The pageable object
	 * @return Page containing guardian records
	 */
	@Override
	public Page<Guardian> searchGuardianByFullNameAndContactNumber(String fullName, String contactNumber, Pageable page) {
		return guardianRepository.searchGuadianByFullNameAndContactNumber(fullName, contactNumber, page);
	}

	/**
	 * Validate if the guardian is not valid.
	 *
	 * @param guardian The Guardian Object that will be validated
	 * @return true if the guardian object is not valid, false otherwise
	 * @see Guardian
	 */
	private boolean isGuardianNotValid(Guardian guardian) {
		if (guardian.getFullName() == null || guardian.getFullName().isEmpty()) {
			return true;
		} else return guardian.getContactNumber() == null || guardian.getContactNumber().isEmpty();
	}

	/**
	 * Check if the guardian exists.
	 *
	 * @param guardianId The Guardian ID that will be checked
	 * @return true if the guardian exists, false otherwise
	 * @see Guardian
	 */
	private boolean isGuardianExist(int guardianId) {
		return guardianRepository.existsById(guardianId);
	}

	/**
	 * A log message when guardian validation failed.
	 *
	 * @param guardian The Guardian Object that failed the validation
	 * @return ExecutionStatus.VALIDATION_ERROR
	 * @see Guardian
	 */
	private ExecutionStatus guardianValidationFailedLog(Guardian guardian) {
		logger.debug("Guardian {} validation failed.", guardian.getFullName());
		return ExecutionStatus.VALIDATION_ERROR;
	}

	/**
	 * A log message when guardian not found.
	 *
	 * @param guardianId The Guardian ID that was not found
	 * @return ExecutionStatus.NOT_FOUND
	 * @see Guardian
	 */
	private ExecutionStatus guardianNotFoundLog(int guardianId) {
		logger.debug("Guardian with id {} not found.", guardianId);
		return ExecutionStatus.NOT_FOUND;
	}

	/**
	 * A log message when guardian has an invalid ID.
	 *
	 * @param guardianId The Guardian ID that has an invalid ID
	 * @return ExecutionStatus.FAILURE
	 * @see Guardian
	 */
	private ExecutionStatus guardianInvalidIdLog(int guardianId) {
		logger.debug("Invalid guardian id {}.", guardianId);
		return ExecutionStatus.FAILED;
	}
}