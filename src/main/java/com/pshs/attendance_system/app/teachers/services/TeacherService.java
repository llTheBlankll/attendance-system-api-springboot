

package com.pshs.attendance_system.app.teachers.services;

import com.pshs.attendance_system.app.teachers.models.entities.Teacher;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
	 * @param teacher   Teacher Object that will be used to update the teacher record.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND, VALIDATION_ERROR)
	 */
	ExecutionStatus updateTeacher(int teacherId, Teacher teacher);

	/**
	 * Update a teacher's first name.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be updated.
	 * @param firstName New first name of the teacher.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND, VALIDATION_ERROR) {@link ExecutionStatus}
	 */
	ExecutionStatus updateTeacherFirstName(int teacherId, String firstName);

	/**
	 * Update a teacher's last name.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be updated.
	 * @param lastName  New last name of the teacher.
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
	 * Get a teacher record by user ID.
	 *
	 * @param userId User ID that will represent the teacher record that will be retrieved.
	 * @return Teacher Object, return null if not found
	 */
	Teacher getTeacherByUser(int userId);

	/**
	 * Get all teacher records.
	 *
	 * @param page Page
	 * @return Page containing teacher records
	 */
	Page<Teacher> getAllTeachers(Pageable page);

	/**
	 * Search teacher records by first name.
	 *
	 * @param firstName First Name of the teacher.
	 * @param page      Page
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherByFirstName(String firstName, Pageable page);

	/**
	 * Search teacher records by last name.
	 *
	 * @param lastName Last Name of the teacher.
	 * @param page     Page
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherByLastName(String lastName, Pageable page);

	/**
	 * Search teacher by their first name and sexuality.
	 *
	 * @param firstName First name of the teacher that will be searched.
	 * @param sex       Sexuality of the teacher that will be searched.
	 * @param page      Page
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherByFirstNameAndSex(String firstName, String sex, Pageable page);

	/**
	 * Search teacher by their last name and sexuality.
	 *
	 * @param lastName Last name of the teacher that will be searched.
	 * @param sex      Sexuality of the teacher that will be searched.
	 * @param page     Page
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherByLastNameAndSex(String lastName, String sex, Pageable page);

	/**
	 * Search teacher by their first name and last name.
	 *
	 * @param firstName First name of the teacher that will be searched.
	 * @param lastName  Last name of the teacher that will be searched.
	 * @param page      Page
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherByFirstNameAndLastName(String firstName, String lastName, Pageable page);

	/**
	 * Search teacher by their first name, last name, and sexuality.
	 *
	 * @param firstName First name of the teacher that will be searched.
	 * @param lastName  Last name of the teacher that will be searched.
	 * @param sex       Sexuality of the teacher that will be searched.
	 * @param page      Page
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherByFirstNameAndLastNameAndSex(String firstName, String lastName, String sex, Pageable page);

	/**
	 * Search teacher by their sexuality.
	 *
	 * @param sex  Sexuality of the teacher that will be searched.
	 * @param page Page
	 * @param size How many records per page it will show
	 * @return Page containing teacher records
	 */
	Page<Teacher> searchTeacherBySex(String sex, Pageable page);
	// Region: Statistics

	/**
	 * Get the total number of teachers.
	 *
	 * @return Total number of teachers
	 */
	int getTeacherCount();
	// End Region: Statistics

}