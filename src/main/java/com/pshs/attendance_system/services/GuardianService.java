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

import com.pshs.attendance_system.entities.Guardian;
import com.pshs.attendance_system.entities.Student;
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
	 * @param guardian Updated Guardian Object
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND, VALIDATION_ERROR)
	 */
	ExecutionStatus updateGuardian(int guardianId, Guardian guardian);

	/**
	 * Get all guardian records.
	 *
	 * @param page Page
	 * @param size How many records per page it will show
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
	 * Get the guardian record of a student.
	 *
	 * @param student Student Object
	 * @return Guardian Object, null if not found
	 */
	Guardian getGuardianOfStudent(Student student);

	/**
	 * Search guardian records by full name.
	 *
	 * @param fullName Full Name
	 * @param page Page
	 * @param size How many records per page it will show
	 * @return Page containing guardian records
	 */
	Page<Guardian> searchGuardianByFullName(String fullName, Pageable page);

	/**
	 * Search guardian records by contact number.
	 *
	 * @param contactNumber Contact Number
	 * @param page Page
	 * @param size How many records per page it will show
	 * @return Page containing guardian records
	 */
	Page<Guardian> searchGuardianByContactNumber(String contactNumber, Pageable page);

	/**
	 * Search the guardian by full name and contact number.
	 *
	 * @param fullName The full name of the guardian
	 * @param contactNumber The contact number of the guardian
	 * @param page The pageable object
	 * @return Page containing guardian records
	 */
	Page<Guardian> searchGuardianByFullNameAndContactNumber(String fullName, String contactNumber, Pageable page);
}