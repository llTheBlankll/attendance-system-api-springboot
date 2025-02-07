package com.pshs.attendance_system.app.gradelevels.models.dto;

import com.pshs.attendance_system.app.strands.models.dto.StrandInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeLevelInput {

	private String name;
	private StrandInput strandInput;
}
