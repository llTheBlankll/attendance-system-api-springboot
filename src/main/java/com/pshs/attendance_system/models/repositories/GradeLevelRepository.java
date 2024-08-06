

package com.pshs.attendance_system.models.repositories;

import com.pshs.attendance_system.models.entities.GradeLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeLevelRepository extends JpaRepository<GradeLevel, Integer> {
	@Query("select g from GradeLevel g where g.name like concat('%', :name, '%')")
	Page<GradeLevel> searchGradeLevelsByName(@Param("name") @NonNull String name, Pageable pageable);

	@Query("select g from GradeLevel g where g.name like concat('%', :name, '%') and g.strand.id = :id")
	Page<GradeLevel> searchGradeLevelsByNameAndStrand(@Param("name") @NonNull String name, @Param("id") @NonNull Integer id, Pageable pageable);

	@Query("select g from GradeLevel g where g.strand.id = :id")
	Page<GradeLevel> searchGradeLevelsByStrand(@Param("id") @NonNull Integer id, Pageable pageable);
}