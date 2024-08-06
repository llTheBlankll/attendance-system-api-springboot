

package com.pshs.attendance_system.models.repositories;

import com.pshs.attendance_system.models.entities.Strand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrandRepository extends JpaRepository<Strand, Integer> {
}