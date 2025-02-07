

package com.pshs.attendance_system.app.teachers.repositories;

import com.pshs.attendance_system.app.students.enums.Sex;
import com.pshs.attendance_system.app.teachers.models.dto.TeacherSearchInput;
import com.pshs.attendance_system.app.teachers.models.entities.Teacher;
import com.pshs.attendance_system.app.users.models.dto.UserSearchInput;
import com.pshs.attendance_system.app.users.models.entities.User;
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

	@Query("select t from Teacher t where t.user.id = :id")
	Optional<Teacher> findByUserId(@Param("id") Integer id);

	@Query("""
		    SELECT t FROM Teacher t
		    WHERE (:#{#input.firstName} IS NULL OR t.firstName LIKE CONCAT('%', :#{#input.firstName}, '%'))
		    AND (:#{#input.lastName} IS NULL OR t.lastName LIKE CONCAT('%', :#{#input.lastName}, '%'))
		    AND (:#{#input.sex} IS NULL OR t.sex = :#{#input.sex})
		""")
	Page<Teacher> search(TeacherSearchInput input, Pageable pageable);
}