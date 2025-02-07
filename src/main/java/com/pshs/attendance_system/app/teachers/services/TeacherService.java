

package com.pshs.attendance_system.app.teachers.services;

import com.pshs.attendance_system.app.teachers.models.dto.TeacherSearchInput;
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

	Page<Teacher> search(TeacherSearchInput searchInput, Pageable pageable);

	// Region: Statistics

	/**
	 * Get the total number of teachers.
	 *
	 * @return Total number of teachers
	 */
	int getTeacherCount();
	// End Region: Statistics

}