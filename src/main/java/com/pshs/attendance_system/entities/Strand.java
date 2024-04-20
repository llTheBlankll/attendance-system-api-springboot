package com.pshs.attendance_system.entities;

import com.pshs.attendance_system.dto.StrandDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
	private List<GradeLevel> gradeLevels = new ArrayList<>();

	public Strand() {
	}

	public Strand(Integer id, String name, List<GradeLevel> gradeLevels) {
		this.id = id;
		this.name = name;
		this.gradeLevels = gradeLevels;
	}

	public Strand(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public StrandDTO toDTO() {
		return new StrandDTO(id, name);
	}

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

	public List<GradeLevel> getGradeLevels() {
		return gradeLevels;
	}

	public void setGradeLevels(List<GradeLevel> gradeLevels) {
		this.gradeLevels = gradeLevels;
	}

}