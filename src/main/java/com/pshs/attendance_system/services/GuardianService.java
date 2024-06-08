

package com.pshs.attendance_system.services;

import com.pshs.attendance_system.entities.Guardian;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GuardianService {

	/**
	 * Create a new guardian record.
	 *
	 * @param guardian Guardian Object
	 * @return ExecutionStatus (SUCCESS, FAILURE, VALIDATION_ERROR)
	 */
	ExecutionStatus createGuardian(Guardian guardian);

	/**
	 * Delete the guardian record. Requires the guardian id.
	 *
	 * @param guardianId Guardian ID
	 * @return ExecutionStatus (SUCCESS, NOT_FOUND)
	 */
	ExecutionStatus deleteGuardian(int guardianId);

	/**
	 * Update the guardian record. Requires the guardian id, and a new guardian object with the updated values.
	 *
	 * @param guardianId Guardian ID
	 * @param guardian   Updated Guardian Object
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND, VALIDATION_ERROR)
	 */
	ExecutionStatus updateGuardian(int guardianId, Guardian guardian);

	/**
	 * Get all guardian records.
	 *
	 * @param page Page
	 * @return Page containing guardian records
	 */
	Page<Guardian> getAllGuardian(Pageable page);

	/**
	 * Get the guardian record by the guardian id.
	 *
	 * @param guardianId Guardian ID
	 * @return Guardian Object, null if not found
	 */
	Guardian getGuardian(int guardianId);

	/**
	 * Search guardian records by full name.
	 *
	 * @param fullName Full Name
	 * @param page     Page
	 * @return Page containing guardian records
	 */
	Page<Guardian> searchGuardianByFullName(String fullName, Pageable page);

	/**
	 * Search guardian records by contact number.
	 *
	 * @param contactNumber Contact Number
	 * @param page          Page
	 * @return Page containing guardian records
	 */
	Page<Guardian> searchGuardianByContactNumber(String contactNumber, Pageable page);

	/**
	 * Search the guardian by full name and contact number.
	 *
	 * @param fullName      The full name of the guardian
	 * @param contactNumber The contact number of the guardian
	 * @param page          The pageable object
	 * @return Page containing guardian records
	 */
	Page<Guardian> searchGuardianByFullNameAndContactNumber(String fullName, String contactNumber, Pageable page);
}