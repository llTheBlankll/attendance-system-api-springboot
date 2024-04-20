package com.pshs.attendance_system.repositories;

import com.pshs.attendance_system.entities.RFIDCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RfidCredentialRepository extends JpaRepository<RFIDCredential, Integer> {
}