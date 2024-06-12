

package com.pshs.attendance_system.services;

import com.pshs.attendance_system.entities.GradeLevel;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GradeLevelService {

	/**
	 * Create a new grade level record
	 *
	 * @param gradeLevel The grade level object that will be created.
	 * @return Execution Status (SUCCESS or FAILURE)
	 */
	ExecutionStatus createGradeLevel(GradeLevel gradeLevel);

	/**
	 * Delete a grade level record
	 *
	 * @param gradeLevelId ID of the grade level to be deleted
	 * @return Execution Status (SUCCESS, FAILURE, NOT_FOUND)
	 */
	ExecutionStatus deleteGradeLevel(int gradeLevelId);

	/**
	 * Update a grade level record
	 *
	 * @param gradeLevelId ID of the grade level to be updated
	 * @param gradeLevel   Updated grade level object
	 * @return Execution Status (SUCCESS, FAILURE, NOT_FOUND, or VALIDATION_ERROR)
	 */
	ExecutionStatus updateGradeLevel(int gradeLevelId, GradeLevel gradeLevel);

	/**
	 * Retrieve a grade level record
	 *
	 * @param gradeLevelId ID of the grade level to be retrieved
	 * @return GradeLevel object, null if not found
	 */
	GradeLevel getGradeLevel(int gradeLevelId);

	/**
	 * Retrieve all grade level records
	 *
	 * @return List of GradeLevel objects
	 */
	Page<GradeLevel> getAllGradeLevels(Pageable page);

	/**
	 * Count all grade level records
	 *
	 * @return Number of grade level records, 0 if none
	 */
	int countAllGradeLevels();

	/**
	 * Check if a grade level record exists
	 *
	 * @param gradeLevelId ID of the grade level to be checked
	 * @return true if the grade level exists, false otherwise
	 */
	boolean isGradeLevelExist(int gradeLevelId);

	/**
	 * Check if a grade level record exists
	 *
	 * @param gradeLevel GradeLevel object to be checked
	 * @return true if the grade level exists, false otherwise
	 */
	boolean isGradeLevelExist(GradeLevel gradeLevel);

	/**
	 * Search grade levels by name in the database, and return the results in pages.
	 * ! Name Cannot be Empty. The page and size are optional and has already been set to 0 and 10 respectively.
	 *
	 * @param name Name of the grade level to be searched
	 * @param page Page number of the results
	 * @return Page of GradeLevel objects, none if not found
	 */
	Page<GradeLevel> searchGradeLevelsByName(String name, Pageable page);

	/**
	 * Search grade levels by strand in the database, and return the results in pages.
	 *
	 * @param strandId ID of the strand to be searched
	 * @param page     Page number of the results
	 * @return Page of GradeLevel objects, none if not found
	 */
	Page<GradeLevel> searchGradeLevelsByStrand(int strandId, Pageable page);

	/**
	 * Search grade levels by name and strand in the database, and return the results in pages.
	 * ! Name Cannot be Empty. The page and size are optional and has already been set to 0 and 10 respectively.
	 * ! Strand ID must be greater than 0 and valid. The page and size are optional and has already been set to 0 and 10 respectively.
	 *
	 * @param name     Name of the grade level to be searched
	 * @param strandId ID of the strand to be searched
	 * @param page     Page number of the results
	 * @return Page of GradeLevel objects, none if not found
	 */
	Page<GradeLevel> searchGradeLevelsByNameAndStrand(String name, int strandId, Pageable page);
}