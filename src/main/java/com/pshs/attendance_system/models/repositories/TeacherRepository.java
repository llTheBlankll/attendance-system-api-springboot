

package com.pshs.attendance_system.models.repositories;

import com.pshs.attendance_system.models.entities.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

	@Query("select t from Teacher t where t.user.id = :id")
	Optional<Teacher> findByUserId(@Param("id") Integer id);
}