package com.pshs.attendance_system.repositories;

import com.pshs.attendance_system.entities.GradeLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeLevelRepository extends JpaRepository<GradeLevel, Integer> {
}