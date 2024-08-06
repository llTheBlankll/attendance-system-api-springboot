

package com.pshs.attendance_system.models.dto;

import com.pshs.attendance_system.models.entities.Section;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a Section with a list of students.
 * DTO for {@link Section}
 */
public class SectionStudentsDTO extends SectionDTO implements Serializable {

	private List<StudentDTO> students = new ArrayList<>();

	public SectionStudentsDTO() {
	}

	public SectionStudentsDTO(List<StudentDTO> students) {
		this.students = students;
	}

	public SectionStudentsDTO(Integer id, TeacherDTO teacher, String room, StrandDTO strand, GradeLevelDTO gradeLevel, String sectionName, List<StudentDTO> students) {
		super(id, teacher, room, strand, gradeLevel, sectionName);
		this.students = students;
	}

	public SectionStudentsDTO(Integer id, TeacherDTO teacher, String room, StrandDTO strand, GradeLevelDTO gradeLevel, String sectionName) {
		super(id, teacher, room, strand, gradeLevel, sectionName);
	}

	public SectionStudentsDTO(SectionDTO section, List<StudentDTO> students) {
		super(section.getId(), section.getTeacher(), section.getRoom(), section.getStrand(), section.getGradeLevel(), section.getSectionName());
		this.students = students;
	}

	/**
	 * Assigns the values of the given SectionDTO to this DTO.
	 * The list of students will be empty and must be set manually.
	 *
	 * @param section the SectionDTO to copy values from
	 */
	public SectionStudentsDTO(SectionDTO section) {
		super(section.getId(), section.getTeacher(), section.getRoom(), section.getStrand(), section.getGradeLevel(), section.getSectionName());
	}

	public Section toEntity() {
		return new Section()
			.setId(getId())
			.setTeacher(getTeacher().toEntity())
			.setRoom(getRoom())
			.setStrand(getStrand().toEntity())
			.setGradeLevel(getGradeLevel().toEntity())
			.setSectionName(getSectionName())
			.setStudents(students.stream().map(StudentDTO::toEntity).collect(Collectors.toList()));
	}

	public List<StudentDTO> getStudents() {
		return students;
	}

	public SectionStudentsDTO setStudents(List<StudentDTO> students) {
		this.students = students;
		return this;
	}
}