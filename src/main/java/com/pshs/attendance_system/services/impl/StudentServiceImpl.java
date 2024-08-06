

package com.pshs.attendance_system.services.impl;

import com.pshs.attendance_system.models.entities.GradeLevel;
import com.pshs.attendance_system.models.entities.Section;
import com.pshs.attendance_system.models.entities.Student;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.models.repositories.StudentRepository;
import com.pshs.attendance_system.services.GradeLevelService;
import com.pshs.attendance_system.services.SectionService;
import com.pshs.attendance_system.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);
	private final StudentRepository studentRepository;
	private final GradeLevelService gradeLevelService;
	private final SectionService sectionService;

	public StudentServiceImpl(StudentRepository studentRepository, GradeLevelService gradeLevelService, SectionService sectionService) {
		this.studentRepository = studentRepository;
		this.gradeLevelService = gradeLevelService;
		this.sectionService = sectionService;
	}


	/**
	 * Create a new student record by providing a student object
	 *
	 * @param student Student Object
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus createStudent(Student student) {
		// Validate the Student and check if it exists.
		if (!isStudentValid(student)) {
			logger.debug("Student is not valid: {}", student.toString());
			return ExecutionStatus.VALIDATION_ERROR;
		} else if (isStudentExist(student)) {
			logger.debug("Student already exists: {}", student);
			return ExecutionStatus.FAILED;
		}

		studentRepository.save(student);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Delete student record by providing a student object
	 *
	 * @param student Student Object that will be deleted
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus deleteStudent(Student student) {
		if (isStudentValid(student)) {
			logger.debug("Student  is not valid: {}", student);
			return ExecutionStatus.VALIDATION_ERROR;
		}

		if (!isStudentExist(student)) {
			logger.debug("Student ID does not exist: {}", student.getId());
			return ExecutionStatus.FAILED;
		}

		studentRepository.delete(student);
		logger.debug("Student deleted: {}", student);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Delete student record by providing a student id
	 *
	 * @param id student ID that will be deleted
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus deleteStudent(int id) {
		if (isStudentExist((long) id)) {
			logger.debug("Cannot delete Student ID because it does not exist: {}", id);
			return ExecutionStatus.FAILED;
		}

		studentRepository.deleteById((long) id);
		logger.debug("Student ID deleted: {}", id);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update student record by providing a student object and student id
	 *
	 * @param studentId student id that will be updated
	 * @param student   the newly updated information of a student.
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus updateStudent(Long studentId, Student student) {
		if (studentId <= 0) {
			logger.debug("Student ID is invalid: {}", studentId);
			return ExecutionStatus.VALIDATION_ERROR;
		}

		if (!isStudentExist(studentId)) {
			logger.debug("Cannot update Student ID because it does not exist: {}", studentId);
			return ExecutionStatus.FAILED;
		}

		// Update the student ID
		student.setId(studentId);
		// Update the student record
		studentRepository.save(student);
		logger.debug("Student updated: {}", student);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update Student Grade Level
	 *
	 * @param studentId  student id that will be updated
	 * @param gradeLevel the new grade level of the student
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus updateStudentGradeLevel(Long studentId, int gradeLevel) {
		if (studentId <= 0) {
			logger.debug("Student ID is invalid: {}", studentId);
			return ExecutionStatus.VALIDATION_ERROR;
		}

		if (!isStudentExist(studentId)) {
			logger.debug("Cannot update Student ID because it does not exist: {}", studentId);
			return ExecutionStatus.FAILED;
		}

		Student student = studentRepository.findById(studentId).orElse(null);
		if (student == null) {
			logger.debug("Student ID does not exist: {}", studentId);
			return ExecutionStatus.FAILED;
		}

		// Check if Grade Level exists
		if (gradeLevelService.isGradeLevelExist(gradeLevel)) {
			logger.debug("Grade Level does not exist: {}", gradeLevel);
			return ExecutionStatus.FAILED;
		} else {
			Optional<GradeLevel> gradeLevelOptional = gradeLevelService.getGradeLevel(gradeLevel);

			gradeLevelOptional.ifPresent(student::setGradeLevel);

			studentRepository.save(student);
			logger.debug("Student updated: {}", student);
			return ExecutionStatus.SUCCESS;
		}
	}

	/**
	 * Update Student Section
	 *
	 * @param studentId student id that will be updated
	 * @param section   the new section of the student
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus updateStudentSection(Long studentId, int section) {
		if (studentId <= 0 || section <= 0) {
			logger.debug("Student ID or Section ID is invalid: {} {}", studentId, section);
			return ExecutionStatus.VALIDATION_ERROR;
		}

		// Check if a student exists
		if (!isStudentExist(studentId)) {
			logger.debug("Cannot update Student ID because it does not exist: {}", studentId);
			return ExecutionStatus.FAILED;
		}

		// Check if a section exists
		if (!sectionService.isSectionExist(section)) {
			logger.debug("Section does not exist: {}", section);
			return ExecutionStatus.FAILED;
		}

		studentRepository.updateStudentSection(sectionService.getSection(section), studentId);
		logger.debug("Student updated: {}", studentId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Get the student record by providing the student id
	 *
	 * @param id Student ID
	 * @return {@link Student}
	 */
	@Override
	public Student getStudentById(Long id) {
		return studentRepository.findById(id).orElse(null);
	}

	/**
	 * Get the student record by providing the guardian id
	 *
	 * @param guardianId Guardian ID
	 * @return {@link Student}
	 */
	@Override
	public Student getStudentByGuardian(int guardianId) {
		return studentRepository.getStudentByGuardianId(guardianId).orElse(null);
	}

	/**
	 * Get all the students in the database
	 *
	 * @param page Page
	 * @return return the page object
	 */
	@Override
	public Page<Student> getAllStudents(Pageable page) {
		return studentRepository.findAll(page);
	}

	/**
	 * Search Students by their first name
	 *
	 * @param firstName First name that will be searched
	 * @param page      Page
	 * @return return the page object
	 */
	@Override
	public Page<Student> searchStudentsByFirstName(String firstName, Pageable page) {
		return studentRepository.searchStudentsByFirstName(firstName, page);
	}

	/**
	 * Search students by their last name
	 *
	 * @param lastName Last name that will be searched
	 * @param page     Page
	 * @return return the page object
	 */
	@Override
	public Page<Student> searchStudentsByLastName(String lastName, Pageable page) {
		return studentRepository.searchStudentsByLastName(lastName, page);
	}

	/**
	 * Search students by their first and last name
	 *
	 * @param firstName First name
	 * @param lastName  Last name
	 * @param page      Page
	 * @return return the page object
	 */
	@Override
	public Page<Student> searchStudentsByFirstAndLastName(String firstName, String lastName, Pageable page) {
		return studentRepository.searchStudentsByFirstNameAndLastName(firstName, lastName, page);
	}

	/**
	 * Search students by their grade level.
	 *
	 * @param gradeLevel the grade level to search for
	 * @param page       the page number of the results
	 * @return a page of Student objects that match the search criteria
	 */
	@Override
	public Page<Student> getStudentsInGradeLevel(int gradeLevel, Pageable page) {
		return studentRepository.searchStudentsByGradeLevelById(gradeLevel, page);
	}

	/**
	 * Search students by their section ID.
	 *
	 * @param sectionId the ID of the section to search for
	 * @param page      the page number of the results
	 * @return a page of Student objects that match the search criteria
	 */
	@Override
	public Page<Student> getStudentsInSection(int sectionId, Pageable page) {
		return studentRepository.searchStudentsBySection(sectionService.getSection(sectionId), page);
	}

	/**
	 * Calculates the average age of all students in the system.
	 * TODO: Implement student age column.
	 *
	 * @return the average age of all students
	 */
	@Override
	public Double getAverageAge() {
		return 0.0;
	}

	/**
	 * Counts the total number of students in the system.
	 *
	 * @return the total number of students
	 */
	@Override
	public Long countStudents() {
		return studentRepository.count();
	}

	/**
	 * Counts the number of students in the system with the specified sex.
	 *
	 * @param sex the sex of the students to count (either "M" for male or "F" for female)
	 * @return the number of students with the specified sex
	 */
	@Override
	public Long countStudentsBySex(String sex) {
		return studentRepository.countStudentsBySex(sex);
	}

	/**
	 * Counts the number of students in the specified section.
	 *
	 * @param section the section to count the students in
	 * @return the number of students in the section
	 */
	@Override
	public Long countStudentsInSection(Section section) {
		return studentRepository.countStudentsInSection(section);
	}

	/**
	 * Counts the number of students in the section with the specified section ID.
	 *
	 * @param sectionId the ID of the section to count the students in
	 * @return the number of students in the section
	 */
	@Override
	public Long countStudentsInSection(int sectionId) {
		return studentRepository.countStudentsInSectionById(sectionId);
	}

	/**
	 * Counts the number of students in the specified grade level.
	 *
	 * @param gradeLevel the grade level to count the students in
	 * @return the number of students in the grade level
	 */
	@Override
	public Long countStudentsInGradeLevel(GradeLevel gradeLevel) {
		return studentRepository.countStudentsInGradeLevel(gradeLevel);
	}

	/**
	 * Counts the number of students in the grade level with the specified grade level ID.
	 *
	 * @param gradeLevelId the ID of the grade level to count the students in
	 * @return the number of students in the grade level
	 */
	@Override
	public Long countStudentsInGradeLevel(int gradeLevelId) {
		return studentRepository.countStudentsInGradeLevelById(gradeLevelId);
	}

	/**
	 * Counts the number of students in the specified grade level and section.
	 *
	 * @param gradeLevel the grade level to count the students in
	 * @param section    the section to count the students in
	 * @return the number of students in the grade level and section
	 */
	@Override
	public Long countStudentsInGradeLevelAndSection(Optional<GradeLevel> gradeLevel, Optional<Section> section) {
		return studentRepository.countStudentsInGradeLevelAndSection(gradeLevel, section);
	}

	/**
	 * Counts the number of students in the specified grade level and section.
	 *
	 * @param gradeLevelId the ID of the grade level to count the students in
	 * @param sectionId    the ID of the section to count the students in
	 * @return the number of students in the grade level and section
	 */
	@Override
	public Long countStudentsInGradeLevelAndSection(int gradeLevelId, int sectionId) {
		return studentRepository.countStudentsInGradeLevelAndSection(gradeLevelService.getGradeLevel(gradeLevelId), sectionService.getSection(sectionId));
	}

	private boolean isStudentExist(Student student) {
		if (student.getId() == null) {
			return false;
		}

		return studentRepository.existsById(student.getId());
	}

	@Override
	public boolean isStudentExist(Long studentId) {
		return studentRepository.existsById(studentId);
	}

	private boolean isStudentValid(Student student) {
		if (student.getId() == null) {
			return false;
		}

		if (student.getGradeLevel() == null) {
			return false;
		}

		if (student.getSection() == null) {
			return false;
		}

		if (student.getFirstName() == null) {
			return false;
		}

		return student.getLastName() != null;
	}
}