package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Teacher;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Teacher}
 */
public class TeacherDTO implements Serializable {
	private final Integer id;
	private final String firstName;
	private final String lastName;
	private final String sex;

	public TeacherDTO(Integer id, String firstName, String lastName, String sex) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
	}

	public Teacher toEntity() {
		return new Teacher(id, firstName, lastName, sex);
	}

	public Integer getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSex() {
		return sex;
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