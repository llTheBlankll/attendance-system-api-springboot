package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.GradeLevel;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.GradeLevel}
 */
public class GradeLevelDTO implements Serializable {
	private Integer id;
	private String level;
	private String name;
	private StrandDTO strand;

	public GradeLevelDTO(Integer id, String level, String name, StrandDTO strand) {
		this.id = id;
		this.level = level;
		this.name = name;
		this.strand = strand;
	}

	public GradeLevel toEntity() {
		return new GradeLevel(id, level, name, strand.toEntity());
	}

	public GradeLevelDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public GradeLevelDTO setLevel(String level) {
		this.level = level;
		return this;
	}

	public GradeLevelDTO setName(String name) {
		this.name = name;
		return this;
	}

	public GradeLevelDTO setStrand(StrandDTO strand) {
		this.strand = strand;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public String getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public StrandDTO getStrand() {
		return strand;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GradeLevelDTO entity = (GradeLevelDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.level, entity.level) &&
			Objects.equals(this.name, entity.name) &&
			Objects.equals(this.strand, entity.strand);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, level, name, strand);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"level = " + level + ", " +
			"name = " + name + ", " +
			"strand = " + strand + ")";
	}
}