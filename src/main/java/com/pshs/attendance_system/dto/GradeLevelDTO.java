

package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.GradeLevel;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.GradeLevel}
 */
public class GradeLevelDTO implements Serializable {
	private Integer id;
	private String name;
	private StrandDTO strand;

	public GradeLevelDTO() {
	}

	public GradeLevelDTO(Integer id, String name, StrandDTO strand) {
		this.id = id;
		this.name = name;
		this.strand = strand;
	}

	public GradeLevel toEntity() {
		return new GradeLevel()
			.setId(id)
			.setName(name)
			.setStrand(strand.toEntity());
	}

	public Integer getId() {
		return id;
	}

	public GradeLevelDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public GradeLevelDTO setName(String name) {
		this.name = name;
		return this;
	}

	public StrandDTO getStrand() {
		return strand;
	}

	public GradeLevelDTO setStrand(StrandDTO strand) {
		this.strand = strand;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GradeLevelDTO entity = (GradeLevelDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.name, entity.name) &&
			Objects.equals(this.strand, entity.strand);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, strand);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"name = " + name + ", " +
			"strand = " + strand + ")";
	}
}