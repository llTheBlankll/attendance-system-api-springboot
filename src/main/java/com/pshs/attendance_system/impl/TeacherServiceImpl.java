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

import com.pshs.attendance_system.entities.Teacher;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.repositories.TeacherRepository;
import com.pshs.attendance_system.services.TeacherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

	private static final Logger logger = LogManager.getLogger(TeacherServiceImpl.class);
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

		if (!isTeacherNotExists(teacher.getId())) {
			logger.debug("Teacher ID: {} already exists.", teacher.getId());
			return ExecutionStatus.FAILURE;
		}
		teacherRepository.save(teacher);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Search teacher by their first name, last name, and sexuality.
	 *
	 * @param firstName First name of the teacher that will be searched.
	 * @param lastName  Last name of the teacher that will be searched.
	 * @param sex       Sexuality of the teacher that will be searched.
	 * @param page      Page
	 * @param size     How many records per page it will show
	 * @return Page containing teacher records
	 */
	@Override
	public Page<Teacher> searchTeacherByFirstNameAndLastNameAndSex(String firstName, String lastName, String sex, Pageable page) {
return teacherRepository.searchByFirstNameAndLastNameAndSex(firstName, lastName, sex, page);
	}

	/**
	 * Search teacher by their sexuality.
	 *
	 * @param sex  Sexuality of the teacher that will be searched.
	 * @param page Page
	 * @return Page containing teacher records
	 */
	@Override
	public Page<Teacher> searchTeacherBySex(String sex, Pageable page) {
		return teacherRepository.searchBySex(sex, page);
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
		logger.debug("Teacher ID: {} has been deleted.", teacherId);
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
		logger.debug("Teacher ID: {} has been updated.", teacherId);
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
			logger.debug("First name is empty.");
			return ExecutionStatus.VALIDATION_ERROR;
		}

		int affectedRows = teacherRepository.updateTeacherFirstName(firstName, teacherId);
		if (affectedRows > 0) {
			logger.debug("Teacher ID: {} first name has been changed.", teacherId);
			return ExecutionStatus.SUCCESS;
		}

		logger.debug("Failed to update teacher ID: {} first name.", teacherId);
		return ExecutionStatus.FAILURE;
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
			logger.debug("Last name is empty.");
			return ExecutionStatus.VALIDATION_ERROR;
		}

		int affectedRows = teacherRepository.updateTeacherLastName(lastName, teacherId);
		if (affectedRows > 0) {
			logger.debug("Teacher ID: {} last name has been changed.", teacherId);
			return ExecutionStatus.SUCCESS;
		}

		logger.debug("Failed to update teacher ID: {} last name.", teacherId);
		return ExecutionStatus.FAILURE;
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
	 * Get all teacher records.
	 *
	 * @param page Page
	 * @param size How many records per page it will show
	 * @return Page containing teacher records
	 */
	@Override
	public Page<Teacher> getAllTeachers(Pageable page) {
		return teacherRepository.findAll(page);
	}

	/**
	 * Search teacher records by first name.
	 *
	 * @param firstName First Name of the teacher.
	 * @param page      Page
	 * @param size      How many records per page it will show
	 * @return Page containing teacher records
	 */
	@Override
	public Page<Teacher> searchTeacherByFirstName(String firstName, Pageable page) {
		if (firstName.isEmpty()) return Page.empty();

		return teacherRepository.searchTeachersByFirstName(firstName, page);
	}

	/**
	 * Search teacher records by last name.
	 *
	 * @param lastName Last Name of the teacher.
	 * @param page     Page
	 * @param size     How many records per page it will show
	 * @return Page containing teacher records
	 */
	@Override
	public Page<Teacher> searchTeacherByLastName(String lastName, Pageable page) {
		if (lastName.isEmpty()) return Page.empty();

		return teacherRepository.searchTeachersByLastName(lastName, page);
	}

	/**
	 * Search teacher by their first name and sexuality.
	 *
	 * @param firstName First name of the teacher that will be searched.
	 * @param sex       Sexuality of the teacher that will be searched.
	 * @param page      Page
	 * @param size      How many records per page it will show
	 * @return Page containing teacher records
	 */
	@Override
	public Page<Teacher> searchTeacherByFirstNameAndSex(String firstName, String sex, Pageable page) {
		return teacherRepository.searchTeacherByFirstNameAndSex(firstName, sex, page);
	}

	/**
	 * Search teacher by their last name and sexuality.
	 *
	 * @param lastName Last name of the teacher that will be searched.
	 * @param sex      Sexuality of the teacher that will be searched.
	 * @param page     Page
	 * @param size     How many records per page it will show
	 * @return Page containing teacher records
	 */
	@Override
	public Page<Teacher> searchTeacherByLastNameAndSex(String lastName, String sex, Pageable page) {
		return teacherRepository.searchTeachersByLastNameAndSex(lastName, sex, page);
	}

	/**
	 * Search teacher by their first name and last name.
	 *
	 * @param firstName First name of the teacher that will be searched.
	 * @param lastName  Last name of the teacher that will be searched.
	 * @param page      Page
	 * @param size      How many records per page it will show
	 * @return Page containing teacher records
	 */
	@Override
	public Page<Teacher> searchTeacherByFirstNameAndLastName(String firstName, String lastName, Pageable page) {
		return teacherRepository.searchTeachersByFirstNameAndLastName(firstName, lastName, page);
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
		logger.debug("Teacher ID: {} is invalid.", teacherId);
		return ExecutionStatus.FAILURE;
	}

	private ExecutionStatus teacherNotFoundLog(int teacherId) {
		logger.debug("Teacher ID: {} not found.", teacherId);
		return ExecutionStatus.NOT_FOUND;
	}

	private ExecutionStatus teacherValidationLog(Teacher teacher) {
		logger.debug("Teacher object: {} is invalid.", teacher);
		return ExecutionStatus.VALIDATION_ERROR;
	}
}