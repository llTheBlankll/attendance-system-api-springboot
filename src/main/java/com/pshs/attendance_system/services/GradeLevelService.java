/*
 * Copyright (c) 2024  Vince Angelo Batecan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, and/or sublicense
 * copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * MODIFICATIONS:
 *
 * Any modifications or derivative works of the Software shall be considered part
 * of the Software and shall be subject to the terms and conditions of this license.
 * Any person or entity making modifications to the Software shall assign and
 * transfer all right, title, and interest in and to such modifications to  Vince Angelo Batecan.
 *  Vince Angelo Batecan shall own all intellectual property rights in and to such modifications.
 *
 * NO COMMERCIAL USE:
 *
 * The Software shall not be sold, rented, leased, or otherwise commercially exploited.
 * The Software is intended for personal, non-commercial use only.
 *
 * NO WARRANTIES:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.pshs.attendance_system.services;

import com.pshs.attendance_system.entities.GradeLevel;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;

public interface GradeLevelService {

	/**
	 * Create a new grade level record
	 *
	 * @param gradeLevel
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
	 * @param gradeLevel Updated grade level object
	 * @return Execution Status (SUCCESS, FAILURE, NOT_FOUND, or VALIDATION_ERROR)
	 */
	ExecutionStatus updateGradeLevel(int gradeLevelId, GradeLevel gradeLevel);

	/**
	 * Retrieve a grade level record
	 *
	 * @param gradeLevelId ID of the grade level to be retrieved
	 * @return GradeLevel object
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
	 * @param size Number of results per page
	 * @return Page of GradeLevel objects, none if not found
	 */
	Page<GradeLevel> searchGradeLevelsByName(String name, int page, int size);

	/**
	 * Search grade levels by level in the database, and return the results in pages.
	 * ! Level Cannot be Empty. The page and size are optional and has already been set to 0 and 10 respectively.
	 *
	 * @param level Level of the grade level to be searched
	 * @param page Page number of the results
	 * @param size Number of results per page
	 * @return Page of GradeLevel objects, none if not found.
	 */
	Page<GradeLevel> searchGradeLevelsByLevel(String level, int page, int size);

	/**
	 * Search grade levels by name and strand in the database, and return the results in pages.
	 * ! Name Cannot be Empty. The page and size are optional and has already been set to 0 and 10 respectively.
	 * ! Strand ID must be greater than 0 and valid. The page and size are optional and has already been set to 0 and 10 respectively.
	 *
	 * @param name Name of the grade level to be searched
	 * @param strandId ID of the strand to be searched
	 * @param page Page number of the results
	 * @param size Number of results per page
	 * @return Page of GradeLevel objects, none if not found
	 */
	Page<GradeLevel> searchGradeLevelsByNameAndStrand(String name, int strandId, int page, int size);
	Page<GradeLevel> searchGradeLevelsByLevelAndStrand(String level, int strandId, int page, int size);
}