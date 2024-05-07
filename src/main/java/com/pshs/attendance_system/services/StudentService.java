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

	ExecutionStatus createStudent(Student student);

	ExecutionStatus deleteStudent(Student student);

	ExecutionStatus updateStudent(Long studentId, Student student);

	ExecutionStatus updateStudentFirstName(Long studentId, String firstName);

	ExecutionStatus updateStudentLastName(Long studentId, String lastName);

	ExecutionStatus updateStudentMiddleInitial(Long studentId, String middleName);

	ExecutionStatus updateStudentGradeLevel(Long studentId, int gradeLevel);

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