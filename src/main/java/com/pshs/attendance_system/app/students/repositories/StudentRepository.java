

package com.pshs.attendance_system.app.students.repositories;

import com.pshs.attendance_system.app.gradelevels.models.entities.GradeLevel;
import com.pshs.attendance_system.app.guardians.models.entities.Guardian;
import com.pshs.attendance_system.app.sections.models.entities.Section;
import com.pshs.attendance_system.app.students.models.entities.Student;
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
public interface StudentRepository extends JpaRepository<Student, Long> {
	@Transactional
	@Modifying
	@Query("update Student s set s.section = :section where s.id = :id")
	void updateStudentSection(@NonNull @Param("section") Optional<Section> section, @NonNull @Param("id") Long id);

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
	Page<Student> searchStudentsBySection(@Param("section") @NonNull Optional<Section> section, Pageable pageable);

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
	long countStudentsInGradeLevelAndSection(@Param("gradeLevel") Optional<GradeLevel> gradeLevel, @Param("section") Optional<Section> section);

	@Query("select s from Student s where s.guardian = ?1")
	@NonNull
	Optional<Student> getStudentByGuardian(@NonNull Guardian guardian);

	@Query("select s from Student s where s.guardian.id = ?1")
	Optional<Student> getStudentByGuardianId(@NonNull Integer id);
}