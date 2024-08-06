

package com.pshs.attendance_system.services.impl;

import com.pshs.attendance_system.models.entities.GradeLevel;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.models.repositories.GradeLevelRepository;
import com.pshs.attendance_system.services.GradeLevelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GradeLevelServiceImpl implements GradeLevelService {

	private static final Logger logger = LogManager.getLogger(GradeLevelServiceImpl.class);
	private final GradeLevelRepository gradeLevelRepository;


	public GradeLevelServiceImpl(GradeLevelRepository gradeLevelRepository) {
		this.gradeLevelRepository = gradeLevelRepository;
	}

	/**
	 * Create a new grade level record
	 *
	 * @param gradeLevel GradeLevel object to be created
	 * @return Execution AttendanceStatus (SUCCESS or FAILURE)
	 */
	@Override
	public ExecutionStatus createGradeLevel(GradeLevel gradeLevel) {
		if (isGradeLevelValid(gradeLevel)) {
			return gradeLevelValidationFailed(gradeLevel);
		}

		if (isGradeLevelExist(gradeLevel)) {
			return gradeLevelExists(gradeLevel.getId());
		}

		gradeLevelRepository.save(gradeLevel);
		logger.debug("Grade Level with ID {} has been created", gradeLevel.getId());
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Delete a grade level record
	 *
	 * @param gradeLevelId ID of the grade level to be deleted
	 * @return Execution AttendanceStatus (SUCCESS, FAILURE, NOT_FOUND)
	 */
	@Override
	public ExecutionStatus deleteGradeLevel(int gradeLevelId) {
		if (gradeLevelId <= 0) {
			return ExecutionStatus.FAILED;
		}

		if (!isGradeLevelExist(gradeLevelId)) {
			return gradeLevelNotFound(gradeLevelId);
		}

		gradeLevelRepository.deleteById(gradeLevelId);
		logger.debug("Grade Level with ID {} has been deleted", gradeLevelId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update a grade level record
	 *
	 * @param gradeLevelId ID of the grade level to be updated
	 * @param gradeLevel   Updated grade level object
	 * @return Execution AttendanceStatus (SUCCESS, FAILURE, NOT_FOUND, or VALIDATION_ERROR)
	 */
	@Override
	public ExecutionStatus updateGradeLevel(int gradeLevelId, GradeLevel gradeLevel) {
		if (gradeLevelId <= 0) {
			return ExecutionStatus.FAILED;
		}

		if (!isGradeLevelExist(gradeLevelId)) {
			return gradeLevelNotFound(gradeLevelId);
		}

		if (!isGradeLevelValid(gradeLevel)) {
			return gradeLevelValidationFailed(gradeLevel);
		}

		gradeLevelRepository.save(gradeLevel);
		logger.debug("Grade Level with ID {} has been updated", gradeLevelId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Retrieve a grade level record
	 *
	 * @param gradeLevelId ID of the grade level to be retrieved
	 * @return GradeLevel object, null if not found
	 */
	@Override
	public Optional<GradeLevel> getGradeLevel(int gradeLevelId) {
		return gradeLevelRepository.findById(gradeLevelId);
	}

	/**
	 * Retrieve all grade level records
	 *
	 * @param page Page number
	 * @return List of GradeLevel objects
	 */
	@Override
	public Page<GradeLevel> getAllGradeLevels(Pageable page) {
		return gradeLevelRepository.findAll(page);
	}

	/**
	 * Count all grade level records
	 *
	 * @return Number of grade level records, 0 if none
	 */
	@Override
	public int countAllGradeLevels() {
		return (int) gradeLevelRepository.count();
	}

	/**
	 * Check if a grade level record exists
	 *
	 * @param gradeLevelId ID of the grade level to be checked
	 * @return true if the grade level exists, false otherwise
	 */
	@Override
	public boolean isGradeLevelExist(int gradeLevelId) {
		return gradeLevelRepository.existsById(gradeLevelId);
	}

	/**
	 * Check if a grade level record exists
	 *
	 * @param gradeLevel GradeLevel object to be checked
	 * @return true if the grade level exists, false otherwise
	 */
	@Override
	public boolean isGradeLevelExist(GradeLevel gradeLevel) {
		return gradeLevelRepository.existsById(gradeLevel.getId());
	}

	/**
	 * Search grade levels by name in the database, and return the results in pages.
	 * ! Name Cannot be Empty. The page and size are optional and has already been set to 0 and 10 respectively.
	 *
	 * @param name Name of the grade level to be searched
	 * @param page Page number of the results
	 * @param size Number of results per page
	 * @return Page of GradeLevel objects, none if not found
	 */
	@Override
	public Page<GradeLevel> searchGradeLevelsByName(String name, Pageable page) {
		return gradeLevelRepository.searchGradeLevelsByName(name, page);
	}

	/**
	 * Search grade levels by name and strand in the database, and return the results in pages.
	 * ! Name Cannot be Empty. The page and size are optional and has already been set to 0 and 10 respectively.
	 * ! Strand ID must be greater than 0 and valid. The page and size are optional and has already been set to 0 and 10 respectively.
	 *
	 * @param name     Name of the grade level to be searched
	 * @param strandId ID of the strand to be searched
	 * @param page     Page number of the results
	 * @param size     Number of results per page
	 * @return Page of GradeLevel objects, none if not found
	 */
	@Override
	public Page<GradeLevel> searchGradeLevelsByNameAndStrand(String name, int strandId, Pageable page) {
		return gradeLevelRepository.searchGradeLevelsByNameAndStrand(name, strandId, page);
	}

	/**
	 * Search grade levels by strand in the database, and return the results in pages.
	 *
	 * @param strandId ID of the strand to be searched
	 * @param page     Page number of the results
	 * @param size     Number of results per page
	 * @return Page of GradeLevel objects, none if not found
	 */
	@Override
	public Page<GradeLevel> searchGradeLevelsByStrand(int strandId, Pageable page) {
		return gradeLevelRepository.searchGradeLevelsByStrand(strandId, page);
	}

	private boolean isGradeLevelValid(GradeLevel gradeLevel) {
		return gradeLevel.getName().isEmpty();
	}

	private ExecutionStatus gradeLevelNotFound(int gradeLevelId) {
		logger.debug("Grade Level with ID {} does not exist", gradeLevelId);
		return ExecutionStatus.NOT_FOUND;
	}

	private ExecutionStatus gradeLevelExists(int gradeLevelId) {
		logger.debug("Grade Level with ID {} already exists", gradeLevelId);
		return ExecutionStatus.FAILED;
	}

	private ExecutionStatus gradeLevelValidationFailed(GradeLevel gradeLevel) {
		logger.debug("Grade Level {} validation failed", gradeLevel.getName());
		return ExecutionStatus.VALIDATION_ERROR;
	}
}