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

package com.pshs.attendance_system.impl;

import com.pshs.attendance_system.entities.Guardian;
import com.pshs.attendance_system.entities.Student;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.repositories.GuardianRepository;
import com.pshs.attendance_system.services.GuardianService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
			return ExecutionStatus.FAILURE;
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
			return ExecutionStatus.FAILURE;
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
			return ExecutionStatus.FAILURE;
		}
	}

	/**
	 * Get all guardian records.
	 *
	 * @param page Page
	 * @param size How many records per page it will show
	 * @return Page containing guardian records
	 */
	@Override
	public Page<Guardian> getAllGuardian(int page, int size) {
		return guardianRepository.findAll(PageRequest.of(page, size));
	}

	/**
	 * Get the guardian record by the guardian id.
	 *
	 * @param guardianId Guardian ID
	 * @return Guardian Object, null if not found
	 */
	@Override
	public Guardian getGuardian(int guardianId) {
		return guardianRepository.findById(guardianId).orElse(null);
	}

	/**
	 * Get the guardian record of a student.
	 *
	 * @param student Student Object
	 * @return Guardian Object, null if not found
	 * @see Guardian
	 * @see Student
	 */
	@Override
	public Guardian getGuardianOfStudent(Student student) {
		return guardianRepository.findGuardianOfStudent(student).orElse(null);
	}

	/**
	 * Search guardian records by full name.
	 *
	 * @param fullName Full Name
	 * @param page     Page
	 * @param size     How many records per page it will show
	 * @return Page containing guardian records
	 * @see Guardian
	 */
	@Override
	public Page<Guardian> searchGuardianByFullName(String fullName, int page, int size) {
		return guardianRepository.searchGuardianByFullName(fullName, PageRequest.of(page, size));
	}

	/**
	 * Search guardian records by contact number.
	 *
	 * @param contactNumber Contact Number
	 * @param page          Page
	 * @param size          How many records per page it will show
	 * @return Page containing guardian records
	 * @see Guardian
	 */
	@Override
	public Page<Guardian> searchGuardianByContactNumber(String contactNumber, int page, int size) {
		return guardianRepository.searchGuardianByContactNumber(contactNumber, PageRequest.of(page, size));
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
		return ExecutionStatus.FAILURE;
	}
}