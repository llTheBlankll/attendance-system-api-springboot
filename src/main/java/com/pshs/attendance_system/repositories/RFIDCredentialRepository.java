

package com.pshs.attendance_system.repositories;

import com.pshs.attendance_system.entities.RFIDCredential;
import com.pshs.attendance_system.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RFIDCredentialRepository extends JpaRepository<RFIDCredential, Integer> {
	@Query("select r from RFIDCredential r where r.lrn = ?1")
	Optional<RFIDCredential> getRFIDCredentialByStudent(@NonNull Student lrn);

	@Query("select r from RFIDCredential r where r.lrn.id = ?1")
	Optional<RFIDCredential> getRFIDCredentialByStudentId(@NonNull Long id);

	@Query("select r from RFIDCredential r where r.hashedLrn = :hashedLrn")
	Optional<RFIDCredential> getRFIDCredentialByHash(@Param("hashedLrn") @NonNull String hashedLrn);
}