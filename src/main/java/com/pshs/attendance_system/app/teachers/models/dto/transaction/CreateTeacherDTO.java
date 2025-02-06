package com.pshs.attendance_system.app.teachers.models.dto.transaction;

import com.pshs.attendance_system.app.teachers.models.dto.TeacherDTO;
import com.pshs.attendance_system.app.users.models.dto.UserDTO;
import com.pshs.attendance_system.app.teachers.models.entities.Teacher;

public class CreateTeacherDTO {
	private String firstName;
	private String lastName;
	private String sex;
	private UserDTO user;

	public CreateTeacherDTO() {
	}

	public CreateTeacherDTO(TeacherDTO teacherDTO) {
		this.firstName = teacherDTO.getFirstName();
		this.lastName = teacherDTO.getLastName();
		this.sex = teacherDTO.getSex();
		this.user = teacherDTO.getUser();
	}

	public CreateTeacherDTO(String firstName, String lastName, String sex, UserDTO user) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.user = user;
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

	public String getFirstName() {
		return firstName;
	}

	public CreateTeacherDTO setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public CreateTeacherDTO setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getSex() {
		return sex;
	}

	public CreateTeacherDTO setSex(String sex) {
		this.sex = sex;
		return this;
	}

	public UserDTO getUser() {
		return user;
	}

	public CreateTeacherDTO setUser(UserDTO user) {
		this.user = user;
		return this;
	}
}