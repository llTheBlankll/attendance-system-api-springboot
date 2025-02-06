package com.pshs.attendance_system.app.teachers.models.dto.transaction;

import com.pshs.attendance_system.app.teachers.models.dto.TeacherDTO;
import com.pshs.attendance_system.app.users.models.dto.UserDTO;
import com.pshs.attendance_system.app.teachers.models.entities.Teacher;
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
	private String sex;
	private UserDTO user;

	public TeacherInput(TeacherDTO teacherDTO) {
		this.firstName = teacherDTO.getFirstName();
		this.lastName = teacherDTO.getLastName();
		this.sex = teacherDTO.getSex();
		this.user = teacherDTO.getUser();
	}

	public TeacherDTO toTeacherDTO() {
		return new TeacherDTO()
			.setFirstName(firstName)
			.setLastName(lastName)
			.setSex(sex)
			.setUser(user);
	}

	public Teacher toEntity() {
		return new Teacher()
			.setFirstName(firstName)
			.setLastName(lastName)
			.setSex(sex)
			.setUser(user.toEntity());
	}
}