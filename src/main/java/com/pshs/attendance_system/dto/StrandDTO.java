

package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Strand;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Strand}
 */
public class StrandDTO implements Serializable {
	private Integer id;
	private String name;

	public StrandDTO() {
	}

	public StrandDTO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Strand toEntity() {
		return new Strand()
			.setId(id)
			.setName(name);

	}

	public Integer getId() {
		return id;
	}

	public StrandDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public StrandDTO setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StrandDTO entity = (StrandDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.name, entity.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"name = " + name + ")";
	}
}