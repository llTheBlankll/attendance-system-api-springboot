

package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Teacher;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Teacher}
 */
public class TeacherDTO implements Serializable {
	private Integer id;
	private String firstName;
	private String lastName;
	private String sex;
	private UserDTO user;

	public TeacherDTO() {
	}

	public TeacherDTO(Integer id, String firstName, String lastName, String sex, UserDTO user) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.user = user;
	}

	public Teacher toEntity() {
		return new Teacher()
			.setId(id)
			.setFirstName(firstName)
			.setLastName(lastName)
			.setSex(sex)
			.setUser(user.toEntity());
	}

	public UserDTO getUser() {
		return user;
	}

	public TeacherDTO setUser(UserDTO user) {
		this.user = user;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public TeacherDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public TeacherDTO setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public TeacherDTO setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getSex() {
		return sex;
	}

	public TeacherDTO setSex(String sex) {
		this.sex = sex;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TeacherDTO entity = (TeacherDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.firstName, entity.firstName) &&
			Objects.equals(this.lastName, entity.lastName) &&
			Objects.equals(this.sex, entity.sex);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, sex);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"firstName = " + firstName + ", " +
			"lastName = " + lastName + ", " +
			"sex = " + sex + ")";
	}
}