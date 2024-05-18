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
import com.pshs.attendance_system.entities.Guardian;
import com.pshs.attendance_system.entities.Section;
import com.pshs.attendance_system.entities.Student;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.repositories.StudentRepository;
import com.pshs.attendance_system.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StudentServiceImpl implements StudentService {

	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);
	private final StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
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
			logger.debug("Student is not valid: {}", student);
			return ExecutionStatus.VALIDATION_ERROR;
		} else if (isStudentExist(student)) {
			logger.debug("Student already exists: {}", student);
			return ExecutionStatus.FAILURE;
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
	}

	/**
	 * Delete student record by providing a student id
	 *
	 * @param id student ID that will be deleted
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus deleteStudent(int id) {
		return null;
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
		return null;
	}

	/**
	 * Update Student first name
	 *
	 * @param studentId student id that will be updated
	 * @param firstName the new first name of the student
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus updateStudentFirstName(Long studentId, String firstName) {
		return null;
	}

	/**
	 * Update Student last name
	 *
	 * @param studentId student id that will be updated
	 * @param lastName  the new last name of the student
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus updateStudentLastName(Long studentId, String lastName) {
		return null;
	}

	/**
	 * Update Student middle initial
	 *
	 * @param studentId  student id that will be updated
	 * @param middleName the new middle initial of the student
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus updateStudentMiddleInitial(Long studentId, String middleName) {
		return null;
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
		return null;
	}

	/**
	 * Update Student Section
	 *
	 * @param studentId student id that will be updated
	 * @param section   the new section of the student
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus updateStudentSection(Long studentId, String section) {
		return null;
	}

	/**
	 * Update Student Address with the given address by providing the student id
	 *
	 * @param studentId Student id that will be updated
	 * @param address   the new updated address
	 * @return {@link ExecutionStatus}
	 */
	@Override
	public ExecutionStatus updateStudentAddress(Long studentId, String address) {
		return null;
	}

	/**
	 * Update Student Birthdate by providing the student id
	 *
	 * @param studentId student id that will be updated
	 * @param birthDate the new birthdate of the student
	 * @return {@link ExecutionStatus}
	 */
	@Override
	public ExecutionStatus updateStudentBirthDate(Long studentId, LocalDate birthDate) {
		return null;
	}

	/**
	 * Get the student record by providing the student id
	 *
	 * @param id Student ID
	 * @return {@link Student}
	 */
	@Override
	public Student getStudentById(Long id) {
		return null;
	}

	/**
	 * Get the student record by providing the student guardian.
	 *
	 * @param guardian Guardian of the student
	 * @return {@link Student}
	 */
	@Override
	public Student getStudentByGuardian(Guardian guardian) {
		return null;
	}

	/**
	 * Get the student record by providing the guardian id
	 *
	 * @param guardianId Guardian ID
	 * @return {@link Student}
	 */
	@Override
	public Student getStudentByGuardian(int guardianId) {
		return null;
	}

	/**
	 * Get all the students in the database
	 *
	 * @param page Page
	 * @param size Shows how many student will it display.
	 * @return return the page object
	 */
	@Override
	public Page<Student> getAllStudents(int page, int size) {
		return null;
	}

	/**
	 * Search Students by their first name
	 *
	 * @param firstName First name that will be searched
	 * @param page      Page
	 * @param size      Shows how many student will it display.
	 * @return return the page object
	 */
	@Override
	public Page<Student> searchStudentsByFirstName(String firstName, int page, int size) {
		return null;
	}

	/**
	 * Search students by their last name
	 *
	 * @param lastName Last name that will be searched
	 * @param page     Page
	 * @param size     Shows how many student will it display.
	 * @return return the page object
	 */
	@Override
	public Page<Student> searchStudentsByLastName(String lastName, int page, int size) {
		return null;
	}

	/**
	 * Search students by their first and last name
	 *
	 * @param firstName First name
	 * @param lastName  Last name
	 * @param page      Page
	 * @param size      Shows how many student will it display.
	 * @return return the page object
	 */
	@Override
	public Page<Student> searchStudentsByFirstAndLastName(String firstName, String lastName, int page, int size) {
		return null;
	}

	/**
	 * Search students by their grade level.
	 *
	 * @param gradeLevel the grade level to search for
	 * @param page       the page number of the results
	 * @param size       the number of results per page
	 * @return a page of Student objects that match the search criteria
	 */
	@Override
	public Page<Student> searchStudentsByGradeLevel(int gradeLevel, int page, int size) {
		return null;
	}

	/**
	 * Search students by their grade level.
	 *
	 * @param gradeLevel the grade level to search for
	 * @param page       the page number of the results
	 * @param size       the number of results per page
	 * @return a page of Student objects that match the search criteria
	 */
	@Override
	public Page<Student> searchStudentsByGradeLevel(GradeLevel gradeLevel, int page, int size) {
		return null;
	}

	/**
	 * Search students by their section ID.
	 *
	 * @param sectionId the ID of the section to search for
	 * @param page      the page number of the results
	 * @param size      the number of results per page
	 * @return a page of Student objects that match the search criteria
	 */
	@Override
	public Page<Student> searchStudentsBySection(int sectionId, int page, int size) {
		return null;
	}

	/**
	 * Search students by their section.
	 *
	 * @param section the section to search for
	 * @param page    the page number of the results
	 * @param size    the number of results per page
	 * @return a page of Student objects that match the search criteria
	 */
	@Override
	public Page<Student> searchStudentsBySection(Section section, int page, int size) {
		return null;
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
		return 0;
	}

	/**
	 * Counts the number of students in the system with the specified sex.
	 *
	 * @param sex the sex of the students to count (either "M" for male or "F" for female)
	 * @return the number of students with the specified sex
	 */
	@Override
	public Long countStudentsBySex(String sex) {
		return 0;
	}

	/**
	 * Counts the number of students in the specified section.
	 *
	 * @param section the section to count the students in
	 * @return the number of students in the section
	 */
	@Override
	public Long countStudentsInSection(Section section) {
		return 0;
	}

	/**
	 * Counts the number of students in the section with the specified section ID.
	 *
	 * @param sectionId the ID of the section to count the students in
	 * @return the number of students in the section
	 */
	@Override
	public Long countStudentsInSection(int sectionId) {
		return 0;
	}

	/**
	 * Counts the number of students in the specified grade level.
	 *
	 * @param gradeLevel the grade level to count the students in
	 * @return the number of students in the grade level
	 */
	@Override
	public Long countStudentsInGradeLevel(GradeLevel gradeLevel) {
		return 0;
	}

	/**
	 * Counts the number of students in the grade level with the specified grade level ID.
	 *
	 * @param gradeLevelId the ID of the grade level to count the students in
	 * @return the number of students in the grade level
	 */
	@Override
	public Long countStudentsInGradeLevel(int gradeLevelId) {
		return 0;
	}

	/**
	 * Counts the number of students in the specified grade level and section.
	 *
	 * @param gradeLevel the grade level to count the students in
	 * @param section    the section to count the students in
	 * @return the number of students in the grade level and section
	 */
	@Override
	public Long countStudentsInGradeLevelAndSection(GradeLevel gradeLevel, Section section) {
		return 0;
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
		return 0;
	}

	/**
	 * Counts the number of students in the specified grade level and section with the given first name.
	 *
	 * @param firstName  the first name of the students to count
	 * @param gradeLevel the grade level to count the students in
	 * @param section    the section to count the students in
	 * @return the number of students with the given first name in the specified grade level and section
	 */
	@Override
	public Long countStudentsByFirstNameInGradeLevelAndSection(String firstName, GradeLevel gradeLevel, Section section) {
		return 0;
	}

	/**
	 * Counts the number of students in the specified grade level and section with the given first name.
	 *
	 * @param firstName    the first name of the students to count
	 * @param gradeLevelId the ID of the grade level to count the students in
	 * @param sectionId    the ID of the section to count the students in
	 * @return the number of students with the given first name in the specified grade level and section
	 */
	@Override
	public Long countStudentsByFirstNameInGradeLevelAndSection(String firstName, int gradeLevelId, int sectionId) {
		return 0;
	}

	/**
	 * Counts the number of students in the specified grade level and section with the given last name.
	 *
	 * @param lastName   the last name of the students to count
	 * @param gradeLevel the grade level to count the students in
	 * @param section    the section to count the students in
	 * @return the number of students with the given last name in the specified grade level and section
	 */
	@Override
	public Long countStudentsByLastNameInGradeLevelAndSection(String lastName, GradeLevel gradeLevel, Section section) {
		return 0;
	}

	/**
	 * Counts the number of students in the specified grade level and section with the given last name.
	 *
	 * @param lastName     the last name of the students to count
	 * @param gradeLevelId the ID of the grade level to count the students in
	 * @param sectionId    the ID of the section to count the students in
	 * @return the number of students with the given last name in the specified grade level and section
	 */
	@Override
	public Long countStudentsByLastNameInGradeLevelAndSection(String lastName, int gradeLevelId, int sectionId) {
		return 0;
	}

	/**
	 * Counts the number of students in the specified grade level and section with the given first and last name.
	 *
	 * @param firstName  the first name of the students to count
	 * @param lastName   the last name of the students to count
	 * @param gradeLevel the grade level to count the students in
	 * @param section    the section to count the students in
	 * @return the number of students with the given first and last name in the specified grade level and section
	 */
	@Override
	public Long countStudentsByFirstAndLastNameInGradeLevelAndSection(String firstName, String lastName, GradeLevel gradeLevel, Section section) {
		return 0;
	}

	/**
	 * Counts the number of students in the specified grade level and section with the given first and last name.
	 *
	 * @param firstName    the first name of the students to count
	 * @param lastName     the last name of the students to count
	 * @param gradeLevelId the ID of the grade level to count the students in
	 * @param sectionId    the ID of the section to count the students in
	 * @return the number of students with the given first and last name in the specified grade level and section
	 */
	@Override
	public Long countStudentsByFirstAndLastNameInGradeLevelAndSection(String firstName, String lastName, int gradeLevelId, int sectionId) {
		return 0;
	}

	private boolean isStudentExist(Student student) {
		if (student.getId() == null) {
			return false;
		}

		return studentRepository.existsById(student.getId());
	}

	private boolean isStudentValid(Student student) {
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