package com.pshs.attendance_system.repositories;

import com.pshs.attendance_system.entities.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Integer> {
}