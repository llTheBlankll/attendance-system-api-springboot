

package com.pshs.attendance_system.services;

import com.pshs.attendance_system.models.entities.GradeLevel;
import com.pshs.attendance_system.models.entities.Section;
import com.pshs.attendance_system.models.entities.Student;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {

	/**
	 * Create a new student record by providing a student object
	 *
	 * @param student Student Object
	 * @return ExecutionStatus
	 */
	ExecutionStatus createStudent(Student student);

	/**
	 * Delete student record by providing a student object
	 *
	 * @param student Student Object that will be deleted
	 * @return ExecutionStatus
	 */
	ExecutionStatus deleteStudent(Student student);

	/**
	 * Delete student record by providing a student id
	 *
	 * @param id student ID that will be deleted
	 * @return ExecutionStatus
	 */
	ExecutionStatus deleteStudent(int id);

	/**
	 * Update student record by providing a student object and student id
	 *
	 * @param studentId student id that will be updated
	 * @param student   the newly updated information of a student.
	 * @return ExecutionStatus
	 */
	ExecutionStatus updateStudent(Long studentId, Student student);

	/**
	 * Update Student Grade Level
	 *
	 * @param studentId  student id that will be updated
	 * @param gradeLevel the new grade level of the student
	 * @return ExecutionStatus
	 */
	ExecutionStatus updateStudentGradeLevel(Long studentId, int gradeLevel);

	/**
	 * Update Student Section
	 *
	 * @param studentId student id that will be updated
	 * @param section   the new section of the student
	 * @return ExecutionStatus
	 */
	ExecutionStatus updateStudentSection(Long studentId, int section);

	/**
	 * Get the student record by providing the student id
	 *
	 * @param id Student ID
	 * @return {@link Student}
	 */
	Student getStudentById(Long id);

	/**
	 * Get the student record by providing the guardian id
	 *
	 * @param guardianId Guardian ID
	 * @return {@link Student}
	 */
	Student getStudentByGuardian(int guardianId);

	// Region: Retrieval

	/**
	 * Get all the students in the database
	 *
	 * @param page Page
	 * @return return the page object
	 */
	Page<Student> getAllStudents(Pageable page);

	/**
	 * Search Students by their first name
	 *
	 * @param firstName First name that will be searched
	 * @param page      Page
	 * @return return the page object
	 */
	Page<Student> searchStudentsByFirstName(String firstName, Pageable page);

	/**
	 * Search students by their last name
	 *
	 * @param lastName Last name that will be searched
	 * @param page     Page
	 * @return return the page object
	 */
	Page<Student> searchStudentsByLastName(String lastName, Pageable page);

	/**
	 * Search students by their first and last name
	 *
	 * @param firstName First name
	 * @param lastName  Last name
	 * @param page      Page
	 * @return return the page object
	 */
	Page<Student> searchStudentsByFirstAndLastName(String firstName, String lastName, Pageable page);

	/**
	 * Search students by their grade level.
	 *
	 * @param gradeLevel the grade level to search for
	 * @param page       the page number of the results
	 * @return a page of Student objects that match the search criteria
	 */
	Page<Student> getStudentsInGradeLevel(int gradeLevel, Pageable page);

	/**
	 * Search students by their section ID.
	 *
	 * @param sectionId the ID of the section to search for
	 * @param page      the page number of the results
	 * @return a page of Student objects that match the search criteria
	 */
	Page<Student> getStudentsInSection(int sectionId, Pageable page);

	// End Region

	// Region: Statistics: Statistics

	/**
	 * Calculates the average age of all students in the system.
	 * TODO: Implement student age column.
	 *
	 * @return the average age of all students
	 */
	Double getAverageAge();

	/**
	 * Counts the total number of students in the system.
	 *
	 * @return the total number of students
	 */
	Long countStudents();

	/**
	 * Counts the number of students in the system with the specified sex.
	 *
	 * @param sex the sex of the students to count (either "M" for male or "F" for female)
	 * @return the number of students with the specified sex
	 */
	Long countStudentsBySex(String sex);

	/**
	 * Counts the number of students in the specified section.
	 *
	 * @param section the section to count the students in
	 * @return the number of students in the section
	 */
	Long countStudentsInSection(Section section);

	/**
	 * Counts the number of students in the section with the specified section ID.
	 *
	 * @param sectionId the ID of the section to count the students in
	 * @return the number of students in the section
	 */
	Long countStudentsInSection(int sectionId);

	/**
	 * Counts the number of students in the specified grade level.
	 *
	 * @param gradeLevel the grade level to count the students in
	 * @return the number of students in the grade level
	 */
	Long countStudentsInGradeLevel(GradeLevel gradeLevel);

	/**
	 * Counts the number of students in the grade level with the specified grade level ID.
	 *
	 * @param gradeLevelId the ID of the grade level to count the students in
	 * @return the number of students in the grade level
	 */
	Long countStudentsInGradeLevel(int gradeLevelId);

	/**
	 * Counts the number of students in the specified grade level and section.
	 *
	 * @param gradeLevel the grade level to count the students in
	 * @param section    the section to count the students in
	 * @return the number of students in the grade level and section
	 */
	Long countStudentsInGradeLevelAndSection(Optional<GradeLevel> gradeLevel, Optional<Section> section);

	/**
	 * Counts the number of students in the specified grade level and section.
	 *
	 * @param gradeLevelId the ID of the grade level to count the students in
	 * @param sectionId    the ID of the section to count the students in
	 * @return the number of students in the grade level and section
	 */
	Long countStudentsInGradeLevelAndSection(int gradeLevelId, int sectionId);

	boolean isStudentExist(Long studentId);

	// End Region
}