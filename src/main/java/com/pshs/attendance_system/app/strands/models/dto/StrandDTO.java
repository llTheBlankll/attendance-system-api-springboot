

package com.pshs.attendance_system.app.strands.models.dto;

import com.pshs.attendance_system.app.strands.models.entities.Strand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Strand}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StrandDTO implements Serializable {
	private Integer id;
	private String name;

	public Strand toEntity() {
		return new Strand()
			.setId(id)
			.setName(name);

	}
}