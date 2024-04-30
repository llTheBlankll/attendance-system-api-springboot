package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Section;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Section}
 */
public class SectionDTO implements Serializable {
	private Integer id;
	private TeacherDTO teacher;
	private GradeLevelDTO gradeLevel;
	private StrandDTO strand;
	private String room;
	private String sectionName;
	private List<StudentDTO> students = Collections.emptyList();

	public SectionDTO(Integer id, TeacherDTO teacher, GradeLevelDTO gradeLevel, StrandDTO strand, String room, String sectionName) {
		this.id = id;
		this.teacher = teacher;
		this.gradeLevel = gradeLevel;
		this.strand = strand;
		this.room = room;
		this.sectionName = sectionName;
	}

	public List<StudentDTO> getStudents() {
		return students;
	}

	public SectionDTO setStudents(List<StudentDTO> students) {
		this.students = students;
		return this;
	}

	public Section toEntity() {
		return new Section(id, teacher.toEntity(), room, strand.toEntity(), gradeLevel.toEntity(), sectionName);
	}

	public SectionDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public SectionDTO setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
		return this;
	}

	public SectionDTO setGradeLevel(GradeLevelDTO gradeLevel) {
		this.gradeLevel = gradeLevel;
		return this;
	}

	public SectionDTO setStrand(StrandDTO strand) {
		this.strand = strand;
		return this;
	}

	public SectionDTO setRoom(String room) {
		this.room = room;
		return this;
	}

	public SectionDTO setSectionName(String sectionName) {
		this.sectionName = sectionName;
		return this;
	}

	public GradeLevelDTO getGradeLevel() {
		return gradeLevel;
	}

	public StrandDTO getStrand() {
		return strand;
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