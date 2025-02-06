

package com.pshs.attendance_system.app.gradelevels.models.entities;

import com.pshs.attendance_system.app.gradelevels.models.dto.GradeLevelDTO;
import com.pshs.attendance_system.app.strands.models.entities.Strand;
import jakarta.persistence.*;

@Entity
@Table(name = "grade_levels")
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

	public GradeLevel() {
	}

	public GradeLevel(Integer id, String name, Strand strand) {
		this.id = id;
		this.name = name;
		this.strand = strand;
	}

	public GradeLevelDTO toDTO() {
		return new GradeLevelDTO(id, name, strand.toDTO());
	}

	public Integer getId() {
		return id;
	}

	public GradeLevel setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public GradeLevel setName(String name) {
		this.name = name;
		return this;
	}

	public Strand getStrand() {
		return strand;
	}

	public GradeLevel setStrand(Strand strand) {
		this.strand = strand;
		return this;
	}
}