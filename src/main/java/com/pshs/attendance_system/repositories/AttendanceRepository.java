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

import com.pshs.attendance_system.entities.Attendance;
import com.pshs.attendance_system.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	@Query("select distinct a from Attendance a where a.student = :student order by a.date DESC LIMIT :limit")
	List<Attendance> findAttendancesByStudentOrderByDateDesc(@Param("student") @NonNull Student student, @Param("limit") @NonNull int limit, Sort sort);

	@Query("select a from Attendance a where a.student.id = :id")
	List<Attendance> getAttendancesByStudentId(@Param("id") @NonNull Long id, Sort sort);

	@Query("select a from Attendance a where a.student.id = :id")
	Page<Attendance> findAllAttendancesByStudentId(@Param("id") @NonNull Long id, Pageable pageable);

	@Query("""
		select count(distinct a) from Attendance a
		where a.status = :status and a.date between :dateStart and :dateEnd""")
	long countStudentsByStatusAndDateRange(@Param("status") @NonNull String status, @Param("dateStart") @NonNull LocalDate dateStart, @Param("dateEnd") @NonNull LocalDate dateEnd);

	@Query("select count(distinct a) from Attendance a where a.status = :status and a.date = :date")
	long countStudentsByStatusAndDate(@Param("status") @NonNull String status, @Param("date") LocalDate date);

	@Query("""
		select count(distinct a) from Attendance a
		where a.student.id = :id and a.status = :status and a.date = :date""")
	long countStudentAttendancesByStatusAndDate(@Param("id") @NonNull Long id, @Param("status") @NonNull String status, @Param("date") @NonNull LocalDate date);

	@Query("""
		select count(distinct a) from Attendance a
		where a.student.id = :id and a.status = :status and a.date between :dateStart and :dateEnd""")
	long countStudentAttendancesByStatusBetweenDate(@Param("id") @NonNull Long id, @Param("status") @NonNull String status, @Param("dateStart") @NonNull LocalDate dateStart, @Param("dateEnd") @NonNull LocalDate dateEnd);

	@Query("select count(distinct a) from Attendance a where a.time = :time and a.date = :date and a.status = :status")
	long countStudentsAttendancesTimeInByDateAndStatus(@Param("time") @NonNull LocalTime time, @Param("date") @NonNull LocalDate date, @Param("status") @NonNull String status);

	long countByTimeAndDate(@NonNull LocalTime time, @NonNull LocalDate date);

	@Query("""
		select count(distinct a) from Attendance a
		where a.timeOut = :timeOut and a.date = :date and a.status = :status""")
	long countStudentsAttendancesTimeOutBydateAndStatus(@Param("timeOut") @NonNull LocalTime timeOut, @Param("date") @NonNull LocalDate date, @Param("status") @NonNull String status);

	@Query("select a from Attendance a where a.student.id = :id and a.date = :date")
	Optional<Attendance> getAttendanceByStudentDate(@Param("id") @NonNull Long id, @Param("date") @NonNull LocalDate date);

	@Query("select (count(a) > 0) from Attendance a where a.student.id = :id and a.date = :date")
	boolean isAttendanceExistByStudentAndDate(@Param("id") Long id, @Param("date") LocalDate date);

	@Query("select a from Attendance a where a.student.id = :id and a.date = :date")
	Optional<Attendance> isStudentAttendanceExist(@Param("id") Long id, @Param("date") LocalDate date);

	@Transactional
	@Modifying
	@Query("update Attendance a set a.timeOut = :timeOut where a.id = :id")
	int updateAttendanceTimeOut(@Nullable @Param("timeOut") LocalTime timeOut, @NonNull @Param("id") Integer id);
}