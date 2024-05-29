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

import com.pshs.attendance_system.entities.Teacher;
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
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
	@Transactional
	@Modifying
	@Query("update Teacher t set t.firstName = :firstName where t.id = :id")
	int updateTeacherFirstName(@NonNull @Param("firstName") String firstName, @NonNull @Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("update Teacher t set t.lastName = :lastName where t.id = :id")
	int updateTeacherLastName(@NonNull @Param("lastName") String lastName, @NonNull @Param("id") Integer id);

	@Query("select t from Teacher t where t.firstName like concat('%', :firstName, '%')")
	Page<Teacher> searchTeachersByFirstName(@Param("firstName") @NonNull String firstName, Pageable pageable);

	@Query("select t from Teacher t where t.lastName like concat('%', :lastName, '%')")
	Page<Teacher> searchTeachersByLastName(@Param("lastName") @NonNull String lastName, Pageable pageable);

	@Query("select t from Teacher t where t.firstName like concat('%', :firstName, '%') and t.sex = :sex")
	Page<Teacher> searchTeacherByFirstNameAndSex(@Param("firstName") @NonNull String firstName, @Param("sex") @NonNull String sex, Pageable pageable);

	@Query("select t from Teacher t where t.lastName like concat('%', :lastName, '%') and t.sex = :sex")
	Page<Teacher> searchTeachersByLastNameAndSex(@Param("lastName") @NonNull String lastName, @Param("sex") @NonNull String sex, Pageable pageable);

	@Query("""
		select t from Teacher t
		where t.firstName like concat('%', :firstName, '%') and t.lastName like concat('%', :lastName, '%')""")
	Page<Teacher> searchTeachersByFirstNameAndLastName(@Param("firstName") @NonNull String firstName, @Param("lastName") @NonNull String lastName, Pageable pageable);

	@Query("""
		select t from Teacher t
		where t.firstName like concat('%', :firstName, '%') and t.lastName like concat('%', :lastName, '%') and t.sex like concat('%', :sex, '%')""")
	Page<Teacher> searchByFirstNameAndLastNameAndSex(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("sex") String sex, Pageable pageable);

	@Query("select t from Teacher t where t.sex like concat('%', :sex, '%')")
	Page<Teacher> searchBySex(@Param("sex") String sex, Pageable pageable);
}