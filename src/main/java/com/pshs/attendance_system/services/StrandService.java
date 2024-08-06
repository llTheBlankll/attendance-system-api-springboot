

package com.pshs.attendance_system.services;

import com.pshs.attendance_system.models.entities.Strand;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StrandService {

	/**
	 * Create a new strand record.
	 *
	 * @param strand Strand object to be created
	 * @return Execution AttendanceStatus (SUCCESS, FAILURE, or VALIDATION_ERROR)
	 */
	ExecutionStatus createStrand(Strand strand);

	/**
	 * Delete a strand record.
	 *
	 * @param strandId ID of the strand to be deleted
	 * @return Execution AttendanceStatus (SUCCESS, FAILURE, or NOT_FOUND)
	 */
	ExecutionStatus deleteStrand(int strandId);

	/**
	 * Update a strand record.
	 *
	 * @param strandId ID of the strand to be updated
	 * @param strand   Updated strand object
	 * @return Execution AttendanceStatus (SUCCESS, FAILURE, NOT_FOUND, or VALIDATION_ERROR)
	 */
	ExecutionStatus updateStrand(int strandId, Strand strand);

	/**
	 * Retrieve a strand record.
	 *
	 * @param strandId ID of the strand to be retrieved
	 * @return Strand object if found, otherwise null
	 */
	Strand getStrand(int strandId);

	/**
	 * Retrieve all strand records.
	 *
	 * @return The page of strand records
	 */
	Page<Strand> getAllStrands(Pageable pageable);
}