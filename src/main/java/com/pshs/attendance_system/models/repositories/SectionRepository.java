

package com.pshs.attendance_system.models.repositories;

import com.pshs.attendance_system.models.entities.GradeLevel;
import com.pshs.attendance_system.models.entities.Section;
import com.pshs.attendance_system.models.entities.Strand;
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
public interface SectionRepository extends JpaRepository<Section, Integer> {
	@Query("select s from Section s where s.sectionName like concat('%', :sectionName, '%')")
	Page<Section> searchSectionBySectionName(@Param("sectionName") @NonNull String sectionName, Pageable pageable);

	@Query("select s from Section s where s.room like concat('%', :room, '%')")
	Page<Section> searchSectionByRoom(@Param("room") @NonNull String room, Pageable pageable);

	@Query("select s from Section s where s.gradeLevel.id = :id")
	Page<Section> getSectionByGradeLevel(@Param("id") @NonNull Integer id, Pageable pageable);

	@Query("select s from Section s where s.strand.id = :id")
	Page<Section> getSectionByStrand(@Param("id") @NonNull Integer id, Pageable pageable);

	@Query("select s from Section s where s.teacher.id = :id")
	Page<Section> getSectionByTeacher(@Param("id") @NonNull Integer id, Pageable pageable);

	@Transactional
	@Modifying
	@Query("update Section s set s.gradeLevel = :gradeLevel where s.id = :id")
	int updateSectionGradeLevel(@Param("gradeLevel") GradeLevel gradeLevel, @NonNull @Param("id") Integer id);

	@Query("select s.gradeLevel from Section s where s.id = :id")
	Optional<GradeLevel> getGradeLevelBySectionId(@Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("update Section s set s.sectionName = :sectionName where s.id = :id")
	int updateSectionName(@NonNull @Param("sectionName") String sectionName, @NonNull @Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("update Section s set s.strand = :strand where s.id = :id")
	int updateSectionStrand(@NonNull @Param("strand") Strand strand, @NonNull @Param("id") Integer id);

	@Query("select s.strand from Section s where s.id = :id")
	Optional<Strand> getStrandOfSectionId(@Param("id") @NonNull Integer id);

	@Transactional
	@Modifying
	@Query("update Section s set s.room = :room where s.id = :id")
	int updateSectionRoomName(@NonNull @Param("room") String room, @NonNull @Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("update Section s set s.teacher = :teacher where s.id = :id")
	int updateSectionTeacher(@NonNull @Param("teacher") Teacher teacher, @NonNull @Param("id") Integer id);
}