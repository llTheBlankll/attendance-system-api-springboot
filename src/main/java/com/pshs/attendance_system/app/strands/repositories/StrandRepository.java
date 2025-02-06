

package com.pshs.attendance_system.app.strands.repositories;

import com.pshs.attendance_system.app.strands.models.entities.Strand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrandRepository extends JpaRepository<Strand, Integer> {
}