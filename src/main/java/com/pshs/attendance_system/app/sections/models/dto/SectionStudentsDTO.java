

package com.pshs.attendance_system.app.sections.models.dto;

import com.pshs.attendance_system.app.gradelevels.models.dto.GradeLevelDTO;
import com.pshs.attendance_system.app.strands.models.dto.StrandDTO;
import com.pshs.attendance_system.app.students.models.dto.StudentDTO;
import com.pshs.attendance_system.app.teachers.models.dto.TeacherDTO;
import com.pshs.attendance_system.app.sections.models.entities.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a Section with a list of students.
 * DTO for {@link Section}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionStudentsDTO extends SectionDTO implements Serializable {

	private List<StudentDTO> students = new ArrayList<>();

	public SectionStudentsDTO(Integer id, TeacherDTO teacher, String room, StrandDTO strand, GradeLevelDTO gradeLevel, String sectionName, List<StudentDTO> students) {
		super(id, teacher, room, strand, gradeLevel, sectionName);
		this.students = students;
	}

	public SectionStudentsDTO(SectionDTO section, List<StudentDTO> students) {
		super(section.getId(), section.getTeacher(), section.getRoom(), section.getStrand(), section.getGradeLevel(), section.getSectionName());
		this.students = students;
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
}