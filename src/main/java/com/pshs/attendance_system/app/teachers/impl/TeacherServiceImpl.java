

package com.pshs.attendance_system.app.teachers.impl;

import com.pshs.attendance_system.app.teachers.models.dto.TeacherSearchInput;
import com.pshs.attendance_system.app.teachers.models.entities.Teacher;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.teachers.repositories.TeacherRepository;
import com.pshs.attendance_system.app.teachers.services.TeacherService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TeacherServiceImpl implements TeacherService {

	private final TeacherRepository teacherRepository;

	public TeacherServiceImpl(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}

	/**
	 * Create a new teacher record.
	 *
	 * @param teacher Teacher Object that will be required to create a new teacher record.
	 * @return ExecutionStatus (SUCCESS, FAILURE, VALIDATION_ERROR)
	 */
	@Override
	public ExecutionStatus createTeacher(Teacher teacher) {
		if (isTeacherNotValid(teacher)) {
			return teacherValidationLog(teacher);
		}

		teacherRepository.save(teacher);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Delete a teacher record.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be deleted.
	 * @return ExecutionStatus (SUCCESS, NOT_FOUND)
	 */
	@Override
	public ExecutionStatus deleteTeacher(int teacherId) {
		if (teacherId <= 0) {
			return teacherInvalidIdLog(teacherId);
		}

		if (isTeacherNotExists(teacherId)) {
			return teacherNotFoundLog(teacherId);
		}

		teacherRepository.deleteById(teacherId);
		log.debug("Teacher ID: {} has been deleted.", teacherId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update a teacher record, teacherId will be used as the identifier of teacher record that will be updated.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be updated.
	 * @param teacher   Teacher Object that will be used to update the teacher record.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND, VALIDATION_ERROR)
	 */
	@Override
	public ExecutionStatus updateTeacher(int teacherId, Teacher teacher) {
		if (teacherId <= 0) {
			return teacherInvalidIdLog(teacherId);
		}

		if (isTeacherNotExists(teacherId)) {
			return teacherNotFoundLog(teacherId);
		}

		if (isTeacherNotValid(teacher)) {
			return teacherValidationLog(teacher);
		}

		teacherRepository.save(teacher);
		log.debug("Teacher ID: {} has been updated.", teacherId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update a teacher's first name.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be updated.
	 * @param firstName New first name of the teacher.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND, VALIDATION_ERROR)
	 */
	@Override
	public ExecutionStatus updateTeacherFirstName(int teacherId, String firstName) {
		if (teacherId <= 0) {
			return teacherInvalidIdLog(teacherId);
		}

		if (isTeacherNotExists(teacherId)) {
			return teacherNotFoundLog(teacherId);
		}

		if (firstName.isEmpty()) {
			log.debug("First name is empty.");
			return ExecutionStatus.VALIDATION_ERROR;
		}

		int affectedRows = teacherRepository.updateTeacherFirstName(firstName, teacherId);
		if (affectedRows > 0) {
			log.debug("Teacher ID: {} first name has been changed.", teacherId);
			return ExecutionStatus.SUCCESS;
		}

		log.debug("Failed to update teacher ID: {} first name.", teacherId);
		return ExecutionStatus.FAILED;
	}

	/**
	 * Update a teacher's last name.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be updated.
	 * @param lastName  New last name of the teacher.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND, VALIDATION_ERROR)
	 */
	@Override
	public ExecutionStatus updateTeacherLastName(int teacherId, String lastName) {
		if (teacherId <= 0) {
			return teacherInvalidIdLog(teacherId);
		}

		if (isTeacherNotExists(teacherId)) {
			return teacherNotFoundLog(teacherId);
		}

		if (lastName.isEmpty()) {
			log.debug("Last name is empty.");
			return ExecutionStatus.VALIDATION_ERROR;
		}

		int affectedRows = teacherRepository.updateTeacherLastName(lastName, teacherId);
		if (affectedRows > 0) {
			log.debug("Teacher ID: {} last name has been changed.", teacherId);
			return ExecutionStatus.SUCCESS;
		}

		log.debug("Failed to update teacher ID: {} last name.", teacherId);
		return ExecutionStatus.FAILED;
	}

	/**
	 * Get a teacher record.
	 *
	 * @param teacherId Teacher ID that will represent the teacher record that will be retrieved.
	 * @return Teacher Object, return null if not found
	 */
	@Override
	public Teacher getTeacher(int teacherId) {
		return teacherId > 0 ? teacherRepository.findById(teacherId).orElse(null) : null;
	}

	/**
	 * Get a teacher record by user ID.
	 *
	 * @param userId User ID that will represent the teacher record that will be retrieved.
	 * @return Teacher Object, return null if not found
	 */
	@Override
	public Teacher getTeacherByUser(int userId) {
		return teacherRepository.findByUserId(userId).orElse(null);
	}

	/**
	 * Get all teacher records.
	 *
	 * @param page Page
	 * @return Page containing teacher records
	 */
	@Override
	public Page<Teacher> getAllTeachers(Pageable page) {
		return teacherRepository.findAll(page);
	}

	@Override
	public Page<Teacher> search(TeacherSearchInput searchInput, Pageable pageable) {
		return teacherRepository.search(searchInput, pageable);
	}

	/**
	 * Get the total number of teachers.
	 *
	 * @return Total number of teachers
	 */
	@Override
	public int getTeacherCount() {
		return teacherRepository.findAll().size();
	}

	private boolean isTeacherNotExists(int teacherId) {
		return !teacherRepository.existsById(teacherId);
	}

	private boolean isTeacherNotValid(Teacher teacher) {
		return teacher == null || teacher.getFirstName() == null || teacher.getLastName() == null || teacher.getSex() == null;
	}

	private ExecutionStatus teacherInvalidIdLog(int teacherId) {
		log.debug("Teacher ID: {} is invalid.", teacherId);
		return ExecutionStatus.FAILED;
	}

	private ExecutionStatus teacherNotFoundLog(int teacherId) {
		log.debug("Teacher ID: {} not found.", teacherId);
		return ExecutionStatus.NOT_FOUND;
	}

	private ExecutionStatus teacherValidationLog(Teacher teacher) {
		log.debug("Teacher object: {} is invalid.", teacher);
		return ExecutionStatus.VALIDATION_ERROR;
	}
}