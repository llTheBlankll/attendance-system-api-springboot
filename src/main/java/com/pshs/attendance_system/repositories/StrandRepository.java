package com.pshs.attendance_system.repositories;

import com.pshs.attendance_system.entities.Strand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrandRepository extends JpaRepository<Strand, Integer> {
}