package com.pshs.attendance_system.app.teachers.models.dto;

import com.pshs.attendance_system.app.students.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSearchInput {

	private String firstName;
	private String lastName;
	private Sex sex;
	
}
