package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Section;
import com.pshs.attendance_system.entities.Teacher;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Teacher}
 */
public class TeacherDTO implements Serializable {
	private Integer id;
	private String firstName;
	private String lastName;
	private String sex;
	private List<Section> sections;

	public TeacherDTO(Integer id, String firstName, String lastName, String sex, List<Section> sections) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.sections = sections;
	}

	public Teacher toEntity() {
		return new Teacher(id, firstName, lastName, sex, sections);
	}

	public TeacherDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public TeacherDTO setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public TeacherDTO setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public TeacherDTO setSex(String sex) {
		this.sex = sex;
		return this;
	}

	public TeacherDTO setSections(List<Section> sections) {
		this.sections = sections;
		return this;
	}

	public List<Section> getSections() {
		return sections;
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