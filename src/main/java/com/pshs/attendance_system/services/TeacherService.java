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

import com.pshs.attendance_system.entities.Teacher;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;

public interface TeacherService {

	/**
	 * Create a new teacher record.
	 *
	 * @param teacher Teacher Object that will be required to create a new teacher record.
	 * @return ExecutionStatus (SUCCESS, FAILURE, VALIDATION_ERROR)
	 */
	ExecutionStatus createTeacher(Teacher teacher);

	/**
	 * Delete a teacher record.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be deleted.
	 * @return ExecutionStatus (SUCCESS, NOT_FOUND)
	 */
	ExecutionStatus deleteTeacher(int teacherId);

	/**
	 * Update a teacher record, teacherId will be used as the identifier of teacher record that will be updated.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be updated.
	 * @param teacher Teacher Object that will be used to update the teacher record.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND, VALIDATION_ERROR)
	 */
	ExecutionStatus updateTeacher(int teacherId, Teacher teacher);

	/**
	 * Update a teacher's first name.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be updated.
	 * @param firstName New first name of the teacher.
	 * @return
	 */
	ExecutionStatus updateTeacherFirstName(int teacherId, String firstName);

	/**
	 * Update a teacher's last name.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be updated.
	 * @param lastName New last name of the teacher.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND, VALIDATION_ERROR)
	 */
	ExecutionStatus updateTeacherLastName(int teacherId, String lastName);

	/**
	 * Get a teacher record.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be retrieved.
	 * @return Teacher Object, return null if not found
	 */
	Teacher getTeacher(int teacherId);

	/**
	 * Get all teacher records.
	 *
	 * @param page Page
	 * @param size How many records per page it will show
	 * @return Page containing teacher records
	 */
	Page<Teacher> getAllTeachers(int page, int size);

	/**
	 * Search teacher records by first name.
	 *
	 * @param firstName First Name of the teacher.
	 * @param page Page
	 * @param size How many records per page it will show
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherByFirstName(String firstName, int page, int size);

	/**
	 * Search teacher records by last name.
	 *
	 * @param lastName Last Name of the teacher.
	 * @param page Page
	 * @param size How many records per page it will show
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherByLastName(String lastName, int page, int size);

	/**
	 * Search teacher by their first name and sexuality.
	 *
	 * @param firstName First name of the teacher that will be searched.
	 * @param sex Sexuality of the teacher that will be searched.
	 * @param page Page
	 * @param size How many records per page it will show
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherByFirstNameAndSex(String firstName, String sex, int page, int size);

	/**
	 * Search teacher by their last name and sexuality.
	 *
	 * @param lastName Last name of the teacher that will be searched.
	 * @param sex Sexuality of the teacher that will be searched.
	 * @param page Page
	 * @param size How many records per page it will show
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherByLastNameAndSex(String lastName, String sex, int page, int size);

	/**
	 * Search teacher by their first name and last name.
	 *
	 * @param firstName First name of the teacher that will be searched.
	 * @param lastName Last name of the teacher that will be searched.
	 * @param page Page
	 * @param size How many records per page it will show
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherByFirstNameAndLastName(String firstName, String lastName, int page, int size);

	// Region: Statistics

	/**
	 * Get the total number of teachers.
	 *
	 * @return Total number of teachers
	 */
	int getTeacherCount();
	// End Region: Statistics

}