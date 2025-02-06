

package com.pshs.attendance_system.app.strands.models.entities;

import com.pshs.attendance_system.app.strands.models.dto.StrandDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "strands")
public class Strand {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "strand_id_gen")
	@SequenceGenerator(name = "strand_id_gen", sequenceName = "strand_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	public Strand() {
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

	public Strand setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Strand setName(String name) {
		this.name = name;
		return this;
	}
}