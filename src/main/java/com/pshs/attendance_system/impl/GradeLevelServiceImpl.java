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

import com.pshs.attendance_system.entities.GradeLevel;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.repositories.GradeLevelRepository;
import com.pshs.attendance_system.services.GradeLevelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
	 * @return Execution Status (SUCCESS or FAILURE)
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
	 * @return Execution Status (SUCCESS, FAILURE, NOT_FOUND)
	 */
	@Override
	public ExecutionStatus deleteGradeLevel(int gradeLevelId) {
		if (gradeLevelId <= 0) {
			return ExecutionStatus.FAILURE;
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
	 * @return Execution Status (SUCCESS, FAILURE, NOT_FOUND, or VALIDATION_ERROR)
	 */
	@Override
	public ExecutionStatus updateGradeLevel(int gradeLevelId, GradeLevel gradeLevel) {
		if (gradeLevelId <= 0) {
			return ExecutionStatus.FAILURE;
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
	public GradeLevel getGradeLevel(int gradeLevelId) {
		return gradeLevelRepository.findById(gradeLevelId).orElse(null);
	}

	/**
	 * Retrieve all grade level records
	 *
	 * @param page Page number
	 * @param size Number of records per page
	 * @return List of GradeLevel objects
	 */
	@Override
	public Page<GradeLevel> getAllGradeLevels(int page, int size) {
		return gradeLevelRepository.findAll(PageRequest.of(page, size));
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
	public Page<GradeLevel> searchGradeLevelsByName(String name, int page, int size) {
		return gradeLevelRepository.searchGradeLevelsByName(name, PageRequest.of(page, size));
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
	public Page<GradeLevel> searchGradeLevelsByNameAndStrand(String name, int strandId, int page, int size) {
		return gradeLevelRepository.searchGradeLevelsByNameAndStrand(name, strandId, PageRequest.of(page, size));
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
	public Page<GradeLevel> searchGradeLevelsByStrand(int strandId, int page, int size) {
		return gradeLevelRepository.searchGradeLevelsByStrand(strandId, PageRequest.of(page, size));
	}

	private boolean isGradeLevelValid(GradeLevel gradeLevel) {
		return gradeLevel.getName() != null || !gradeLevel.getName().isEmpty();
	}

	private ExecutionStatus gradeLevelNotFound(int gradeLevelId) {
		logger.debug("Grade Level with ID {} does not exist", gradeLevelId);
		return ExecutionStatus.NOT_FOUND;
	}

	private ExecutionStatus gradeLevelExists(int gradeLevelId) {
		logger.debug("Grade Level with ID {} already exists", gradeLevelId);
		return ExecutionStatus.FAILURE;
	}

	private ExecutionStatus gradeLevelValidationFailed(GradeLevel gradeLevel) {
		logger.debug("Grade Level {} validation failed", gradeLevel.getName());
		return ExecutionStatus.VALIDATION_ERROR;
	}
}