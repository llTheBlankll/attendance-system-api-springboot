

package com.pshs.attendance_system.models.dto;

import com.pshs.attendance_system.models.entities.Section;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Section}
 */
public class SectionDTO implements Serializable {
	private Integer id;
	private TeacherDTO teacher;
	private String room;
	private StrandDTO strand;
	private GradeLevelDTO gradeLevel;
	private String sectionName;

	public SectionDTO() {
	}

	public SectionDTO(Integer id, TeacherDTO teacher, String room, StrandDTO strand, GradeLevelDTO gradeLevel, String sectionName) {
		this.id = id;
		this.teacher = teacher;
		this.room = room;
		this.strand = strand;
		this.gradeLevel = gradeLevel;
		this.sectionName = sectionName;
	}

	public Section toEntity() {
		return new Section()
			.setId(id)
			.setTeacher(teacher.toEntity())
			.setRoom(room)
			.setStrand(strand.toEntity())
			.setGradeLevel(gradeLevel.toEntity())
			.setSectionName(sectionName);
	}

	public Integer getId() {
		return id;
	}

	public SectionDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public TeacherDTO getTeacher() {
		return teacher;
	}

	public SectionDTO setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
		return this;
	}

	public String getRoom() {
		return room;
	}

	public SectionDTO setRoom(String room) {
		this.room = room;
		return this;
	}

	public StrandDTO getStrand() {
		return strand;
	}

	public SectionDTO setStrand(StrandDTO strand) {
		this.strand = strand;
		return this;
	}

	public GradeLevelDTO getGradeLevel() {
		return gradeLevel;
	}

	public SectionDTO setGradeLevel(GradeLevelDTO gradeLevel) {
		this.gradeLevel = gradeLevel;
		return this;
	}

	public String getSectionName() {
		return sectionName;
	}

	public SectionDTO setSectionName(String sectionName) {
		this.sectionName = sectionName;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SectionDTO entity = (SectionDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.teacher, entity.teacher) &&
			Objects.equals(this.room, entity.room) &&
			Objects.equals(this.strand, entity.strand) &&
			Objects.equals(this.gradeLevel, entity.gradeLevel) &&
			Objects.equals(this.sectionName, entity.sectionName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, teacher, room, strand, gradeLevel, sectionName);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"teacher = " + teacher + ", " +
			"room = " + room + ", " +
			"strand = " + strand + ", " +
			"gradeLevel = " + gradeLevel + ", " +
			"sectionName = " + sectionName + ")";
	}
}