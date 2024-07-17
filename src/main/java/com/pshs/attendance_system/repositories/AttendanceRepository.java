

package com.pshs.attendance_system.repositories;

import com.pshs.attendance_system.entities.Attendance;
import com.pshs.attendance_system.enums.Status;
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

	@Query("select a from Attendance a where a.student.id = :id")
	List<Attendance> getAttendancesByStudentId(@Param("id") @NonNull Long id, Sort sort);

	@Query("select a from Attendance a where a.student.id = :id")
	Page<Attendance> findAllAttendancesByStudentId(@Param("id") @NonNull Long id, Pageable pageable);

	@Query("""
		select count(distinct a) from Attendance a
		where a.status = :status and a.date between :dateStart and :dateEnd""")
	long countStudentsByStatusAndDateRange(@Param("status") @NonNull Status status, @Param("dateStart") @NonNull LocalDate dateStart, @Param("dateEnd") @NonNull LocalDate dateEnd);

	@Query("select count(distinct a) from Attendance a where a.status = :status and a.date = :date")
	long countStudentsByStatusAndDate(@Param("status") @NonNull Status status, @Param("date") LocalDate date);

	@Query("""
		select count(distinct a) from Attendance a
		where a.student.id = :id and a.status = :status and a.date = :date""")
	long countStudentAttendancesByStatusAndDate(@Param("id") @NonNull Long id, @Param("status") @NonNull Status status, @Param("date") @NonNull LocalDate date);

	@Query("""
		select count(distinct a) from Attendance a
		where a.student.id = :id and a.status = :status and a.date between :dateStart and :dateEnd""")
	long countStudentAttendancesByStatusBetweenDate(@Param("id") @NonNull Long id, @Param("status") @NonNull Status status, @Param("dateStart") @NonNull LocalDate dateStart, @Param("dateEnd") @NonNull LocalDate dateEnd);

	@Query("select count(distinct a) from Attendance a where a.time = :time and a.date = :date and a.status = :status")
	long countStudentsAttendancesTimeInByDateAndStatus(@Param("time") @NonNull LocalTime time, @Param("date") @NonNull LocalDate date, @Param("status") @NonNull Status status);

	long countByTimeAndDate(@NonNull LocalTime time, @NonNull LocalDate date);

	@Query("""
		select count(distinct a) from Attendance a
		where a.timeOut = :timeOut and a.date = :date and a.status = :status""")
	long countStudentsAttendancesTimeOutBydateAndStatus(@Param("timeOut") @NonNull LocalTime timeOut, @Param("date") @NonNull LocalDate date, @Param("status") @NonNull Status status);

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

	@Query("select a from Attendance a where a.date = ?1")
	Page<Attendance> getAllAttendancesByDate(@NonNull LocalDate date, Pageable pageable);

	@Query("select count(distinct a) from Attendance a where a.date = :date and a.status = :status")
	long getAttendanceCountByDateAndStatus(@Param("date") @NonNull LocalDate date, @Param("status") @NonNull Status status);

	@Query(
		"""
			    SELECT COUNT(a) FROM Attendance a
			    JOIN a.student s
			    JOIN s.section sec
			    WHERE sec.id = :id
			    AND a.date BETWEEN :dateStart AND :dateEnd
			    AND a.status = :status
			"""
	)
	long countAttendanceInSection(@Param("id") @NonNull Integer id, @Param("dateStart") @NonNull LocalDate dateStart, @Param("dateEnd") @NonNull LocalDate dateEnd, @Param("status") @NonNull Status status);

	@Query("""
		select count(distinct a) from Attendance a
		where a.student.section.id = :id and a.date = :date and a.status = :status""")
	long countAttendanceInSectionByDate(@Param("id") Integer id, @Param("date") LocalDate date, @Param("status") Status status);

	@Query("select a from Attendance a where a.date = :date order by a.time, a.timeOut")
	List<Attendance> listAllAttendancesByDate(@Param("date") LocalDate date, Sort sort);

	@Query("select a from Attendance a where a.date = :date and a.student.section.id = :id")
	List<Attendance> getAttendanceByDateAndSection(@Param("date") @NonNull LocalDate date, @Param("id") @NonNull Integer id);

	@Query("""
		select a from Attendance a
		where a.student.section.id = :id and a.student.gradeLevel.id = :id1 and a.date = :date""")
	Page<Attendance> getAllSectionAndGradeLevelAttendanceByDate(@Param("id") @NonNull Integer id, @Param("id1") @NonNull Integer id1, @Param("date") @NonNull LocalDate date, Pageable pageable);
}