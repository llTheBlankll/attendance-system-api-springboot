package com.pshs.attendance_system.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "strand")
public class Strand {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "strand_id_gen")
	@SequenceGenerator(name = "strand_id_gen", sequenceName = "strand_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "strand")
	private Set<GradeLevel> gradeLevels = new LinkedHashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<GradeLevel> getGradeLevels() {
		return gradeLevels;
	}

	public void setGradeLevels(Set<GradeLevel> gradeLevels) {
		this.gradeLevels = gradeLevels;
	}

}