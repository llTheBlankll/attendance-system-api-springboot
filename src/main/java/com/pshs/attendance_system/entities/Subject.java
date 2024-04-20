package com.pshs.attendance_system.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjects_id_gen")
	@SequenceGenerator(name = "subjects_id_gen", sequenceName = "subjects_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", length = 128)
	private String name;

	@Column(name = "description", length = Integer.MAX_VALUE)
	private String description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}