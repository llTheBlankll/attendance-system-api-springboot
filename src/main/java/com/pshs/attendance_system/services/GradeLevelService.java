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
	Page<GradeLevel> getAllGradeLevels(int page, int size);

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