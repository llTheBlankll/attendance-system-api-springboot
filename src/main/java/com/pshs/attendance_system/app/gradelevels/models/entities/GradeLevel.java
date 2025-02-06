

package com.pshs.attendance_system.app.gradelevels.models.entities;

import com.pshs.attendance_system.app.gradelevels.models.dto.GradeLevelDTO;
import com.pshs.attendance_system.app.strands.models.entities.Strand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grade_levels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeLevel {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_levels_id_gen")
	@SequenceGenerator(name = "grade_levels_id_gen", sequenceName = "grade_levels_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false, length = 128)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strand")
	private Strand strand;

	public GradeLevelDTO toDTO() {
		return new GradeLevelDTO(id, name, strand.toDTO());
	}
}