package com.pshs.attendance_system.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "grade_levels")
public class GradeLevel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ColumnDefault("nextval('grade_levels_id_seq'::regclass)")
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "level", nullable = false, length = 128)
	private String level;

	@Column(name = "name", nullable = false, length = 128)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "strand")
	private Strand strand;

	public GradeLevel() {
	}

	public GradeLevel(Integer id, String level, String name, Strand strand) {
		this.id = id;
		this.level = level;
		this.name = name;
		this.strand = strand;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Strand getStrand() {
		return strand;
	}

	public void setStrand(Strand strand) {
		this.strand = strand;
	}

}