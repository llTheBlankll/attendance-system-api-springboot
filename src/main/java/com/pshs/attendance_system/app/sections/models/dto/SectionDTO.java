

package com.pshs.attendance_system.app.sections.models.dto;

import com.pshs.attendance_system.app.gradelevels.models.dto.GradeLevelDTO;
import com.pshs.attendance_system.app.strands.models.dto.StrandDTO;
import com.pshs.attendance_system.app.teachers.models.dto.TeacherDTO;
import com.pshs.attendance_system.app.sections.models.entities.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Section}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO implements Serializable {
	private Integer id;
	private TeacherDTO teacher;
	private String room;
	private StrandDTO strand;
	private GradeLevelDTO gradeLevel;
	private String sectionName;

	public Section toEntity() {
		return new Section(
			id,
			teacher.toEntity(),
			room,
			strand.toEntity(),
			gradeLevel.toEntity(),
			sectionName,
			null
		);
	}
}