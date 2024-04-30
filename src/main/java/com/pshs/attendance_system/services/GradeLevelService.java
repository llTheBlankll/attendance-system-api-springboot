package com.pshs.attendance_system.services;

import com.pshs.attendance_system.entities.GradeLevel;
import com.pshs.attendance_system.enums.ExecutionStatus;

public interface GradeLevelService {

	ExecutionStatus createGradeLevel(GradeLevel gradeLevel);
	ExecutionStatus deleteGradeLevel(int gradeLevelId);
	ExecutionStatus updateGradeLevel(int gradeLevelId, GradeLevel gradeLevel);

}