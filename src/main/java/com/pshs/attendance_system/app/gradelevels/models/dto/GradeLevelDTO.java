

package com.pshs.attendance_system.app.gradelevels.models.dto;

import com.pshs.attendance_system.app.gradelevels.models.entities.GradeLevel;
import com.pshs.attendance_system.app.strands.models.dto.StrandDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link GradeLevel}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeLevelDTO implements Serializable {
	private Integer id;
	private String name;
	private StrandDTO strand;

	public GradeLevel toEntity() {
		return new GradeLevel(
			id,
			name,
			strand.toEntity()
		);
	}
}