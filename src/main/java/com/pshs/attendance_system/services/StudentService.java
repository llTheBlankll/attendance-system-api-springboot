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

import com.pshs.attendance_system.entities.GradeLevel;
import com.pshs.attendance_system.entities.Guardian;
import com.pshs.attendance_system.entities.Section;
import com.pshs.attendance_system.entities.Student;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Map;

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
	 * @param student the newly updated information of a student.
	 * @return ExecutionStatus
	 */
	ExecutionStatus updateStudent(Long studentId, Student student);

	/**
	 * Update Student first name
	 *
	 * @param studentId student id that will be updated
	 * @param firstName the new first name of the student
	 * @return ExecutionStatus
	 */
	ExecutionStatus updateStudentFirstName(Long studentId, String firstName);

	/**
	 * Update Student last name
	 *
	 * @param studentId student id that will be updated
	 * @param lastName the new last name of the student
	 * @return ExecutionStatus
	 */
	ExecutionStatus updateStudentLastName(Long studentId, String lastName);

	/**
	 * Update Student middle initial
	 *
	 * @param studentId student id that will be updated
	 * @param middleName the new middle initial of the student
	 * @return ExecutionStatus
	 */
	ExecutionStatus updateStudentMiddleInitial(Long studentId, String middleName);

	/**
	 * Update Student Grade Level
	 * @param studentId student id that will be updated
	 * @param gradeLevel the new grade level of the student
	 * @return ExecutionStatus
	 */
	ExecutionStatus updateStudentGradeLevel(Long studentId, int gradeLevel);

	/**
	 * Update Student Section
	 * @param studentId student id that will be updated
	 * @param section the new section of the student
	 * @return ExecutionStatus
	 */
	ExecutionStatus updateStudentSection(Long studentId, String section);

	
	ExecutionStatus updateStudentAddress(Long studentId, String address);

	ExecutionStatus updateStudentBirthDate(Long studentId, LocalDate birthDate);

	Student getStudentById(Long id);

	Student getStudentByGuardian(Guardian guardian);

	Student getStudentByGuardian(int guardianId);

	// Region: Retrieval

	Page<Student> getAllStudents(int page, int size);

	Page<Student> searchStudentsByFirstName(String firstName, int page, int size);

	Page<Student> searchStudentsByLastName(String lastName, int page, int size);

	Page<Student> searchStudentsByFirstAndLastName(String firstName, String lastName, int page, int size);

	Page<Student> searchStudentsByGradeLevel(int gradeLevel, int page, int size);
	Page<Student> searchStudentsByGradeLevel(GradeLevel gradeLevel, int page, int size);

	Page<Student> searchStudentsBySection(int sectionId, int page, int size);
	Page<Student> searchStudentsBySection(Section section, int page, int size);


	// End Region

	// Region: Statistics: Statistics

	// TODO: Implement student age column.
	Map<String, Double> getAverageAge();
	Long countStudents();
	Long countStudentsBySex(String sex);

	Long countStudentsInSection(Section section);
	Long countStudentsInSection(int sectionId);

	Long countStudentsInGradeLevel(GradeLevel gradeLevel);
	Long countStudentsInGradeLevel(int gradeLevelId);

	Long countStudentsInGradeLevelAndSection(GradeLevel gradeLevel, Section section);
	Long countStudentsInGradeLevelAndSection(int gradeLevelId, int sectionId);

	Long countStudentsByFirstNameInGradeLevelAndSection(String firstName, GradeLevel gradeLevel, Section section);
	Long countStudentsByFirstNameInGradeLevelAndSection(String firstName, int gradeLevelId, int sectionId);

	Long countStudentsByLastNameInGradeLevelAndSection(String lastName, GradeLevel gradeLevel, Section section);
	Long countStudentsByLastNameInGradeLevelAndSection(String lastName, int gradeLevelId, int sectionId);

	Long countStudentsByFirstAndLastNameInGradeLevelAndSection(String firstName, String lastName, GradeLevel gradeLevel, Section section);
	Long countStudentsByFirstAndLastNameInGradeLevelAndSection(String firstName, String lastName, int gradeLevelId, int sectionId);

	// End: Statistics
}