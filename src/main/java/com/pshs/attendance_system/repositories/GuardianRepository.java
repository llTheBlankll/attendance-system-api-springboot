

package com.pshs.attendance_system.repositories;

import com.pshs.attendance_system.entities.Guardian;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Integer> {

	@Query("select g from Guardian g where g.fullName like concat('%', :fullName, '%')")
	Page<Guardian> searchGuardianByFullName(@Param("fullName") @NonNull String fullName, Pageable pageable);

	@Query("select g from Guardian g where upper(g.contactNumber) like upper(concat('%', :contactNumber, '%'))")
	Page<Guardian> searchGuardianByContactNumber(@Param("contactNumber") @NonNull String contactNumber, Pageable pageable);

	@Query("select g from Guardian g where g.fullName = :fullName and g.contactNumber = :contactNumber")
	Page<Guardian> searchGuadianByFullNameAndContactNumber(@Param("fullName") String fullName, @Param("contactNumber") String contactNumber, Pageable pageable);
}