package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Section;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Section}
 */
public class SectionDTO implements Serializable {
	private final Integer id;
	private final TeacherDTO teacher;
	private final String room;
	private final String sectionName;

	public SectionDTO(Integer id, TeacherDTO teacher, String room, String sectionName) {
		this.id = id;
		this.teacher = teacher;
		this.room = room;
		this.sectionName = sectionName;
	}

	public Section toEntity() {
		return new Section(id, teacher.toEntity(), room, sectionName);
	}

	public Integer getId() {
		return id;
	}

	public TeacherDTO getTeacher() {
		return teacher;
	}

	public String getRoom() {
		return room;
	}

	public String getSectionName() {
		return sectionName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SectionDTO entity = (SectionDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.teacher, entity.teacher) &&
			Objects.equals(this.room, entity.room) &&
			Objects.equals(this.sectionName, entity.sectionName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, teacher, room, sectionName);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"teacher = " + teacher + ", " +
			"room = " + room + ", " +
			"sectionName = " + sectionName + ")";
	}
}