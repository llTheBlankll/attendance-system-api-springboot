package com.pshs.attendance_system.app.teachers.models.dto.transaction;

import com.pshs.attendance_system.app.students.enums.Sex;
import com.pshs.attendance_system.app.teachers.models.dto.TeacherDTO;
import com.pshs.attendance_system.app.teachers.models.entities.Teacher;
import com.pshs.attendance_system.app.users.models.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInput {
	private String firstName;
	private String lastName;
	private Sex sex;
	private UserDTO user;

	public TeacherInput(TeacherDTO teacherDTO) {
		this.firstName = teacherDTO.getFirstName();
		this.lastName = teacherDTO.getLastName();
		this.sex = teacherDTO.getSex();
		this.user = teacherDTO.getUser();
	}

	public TeacherDTO toTeacherDTO() {
		new TeacherDTO();
		return TeacherDTO.builder()
			.firstName(firstName)
			.lastName(lastName)
			.sex(sex)
			.user(user)
			.build();
	}

	public Teacher toEntity() {
		new Teacher();
		return Teacher.builder()
			.firstName(firstName)
			.lastName(lastName)
			.sex(sex)
			.user(user.toEntity())
			.build();
	}
}