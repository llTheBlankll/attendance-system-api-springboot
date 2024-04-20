package com.pshs.attendance_system.repositories;

import com.pshs.attendance_system.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
}