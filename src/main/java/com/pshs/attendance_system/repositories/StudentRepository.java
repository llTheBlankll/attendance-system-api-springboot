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

package com.pshs.attendance_system.repositories;

import com.pshs.attendance_system.entities.GradeLevel;
import com.pshs.attendance_system.entities.Section;
import com.pshs.attendance_system.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	@Transactional
	@Modifying
	@Query("update Student s set s.section = :section where s.id = :id")
	void updateStudentSection(@NonNull @Param("section") Section section, @NonNull @Param("id") Long id);

	@Query("select s from Student s where s.firstName like concat('%', :firstName, '%')")
	Page<Student> searchStudentsByFirstName(@Param("firstName") @NonNull String firstName, Pageable pageable);

	@Query("select s from Student s where s.lastName like concat('%', :lastName, '%')")
	Page<Student> searchStudentsByLastName(@Param("lastName") @NonNull String lastName, Pageable pageable);

	@Query("""
		select s from Student s
		where s.firstName like concat('%', :firstName, '%') and s.lastName like concat('%', :lastName, '%')""")
	Page<Student> searchStudentsByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName, Pageable pageable);

	@Query("select s from Student s where s.gradeLevel.id = :id")
	Page<Student> searchStudentsByGradeLevelById(@Param("id") Integer id, Pageable pageable);

	@Query("select s from Student s where s.gradeLevel = :gradeLevel")
	Page<Student> searchStudentsByGradeLevel(@Param("gradeLevel") GradeLevel gradeLevel, Pageable pageable);

	@Query("select s from Student s where s.section = :section")
	Page<Student> searchStudentsBySection(@Param("section") @NonNull Section section, Pageable pageable);

	@Query("select count(s) from Student s where s.sex = :sex")
	long countStudentsBySex(@Param("sex") @NonNull String sex);

	@Query("select count(s) from Student s where s.section = :section")
	long countStudentsInSection(@Param("section") @NonNull Section section);

	@Query("select count(distinct s) from Student s where s.section.id = :id")
	long countStudentsInSectionById(@Param("id") @NonNull Integer id);

	@Query("select count(s) from Student s where s.gradeLevel = :gradeLevel")
	long countStudentsInGradeLevel(@Param("gradeLevel") @NonNull GradeLevel gradeLevel);

	@Query("select count(s) from Student s where s.gradeLevel.id = :id")
	long countStudentsInGradeLevelById(@Param("id") Integer id);

	@Query("select count(s) from Student s where s.gradeLevel = :gradeLevel and s.section = :section")
	long countStudentsInGradeLevelAndSection(@Param("gradeLevel") GradeLevel gradeLevel, @Param("section") Section section);
}