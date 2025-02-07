

package com.pshs.attendance_system.app.sections.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pshs.attendance_system.app.gradelevels.models.entities.GradeLevel;
import com.pshs.attendance_system.app.sections.models.dto.SectionDTO;
import com.pshs.attendance_system.app.sections.models.dto.SectionStudentsDTO;
import com.pshs.attendance_system.app.strands.models.entities.Strand;
import com.pshs.attendance_system.app.students.models.entities.Student;
import com.pshs.attendance_system.app.teachers.models.entities.Teacher;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sections")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Section {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sections_id_gen")
	@SequenceGenerator(name = "sections_id_gen", sequenceName = "sections_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "teacher")
	private Teacher teacher;

	@Column(name = "room", nullable = false)
	private String room;

	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "strand")
	private Strand strand;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "grade_level", nullable = false)
	private GradeLevel gradeLevel;

	@Column(name = "section_name", nullable = false)
	private String sectionName;

	@OneToMany(mappedBy = "section", fetch = FetchType.EAGER)
	private List<Student> students = new ArrayList<>();

	public SectionDTO toDTO() {
		return new SectionDTO(id, teacher.toDTO(), room, strand.toDTO(), gradeLevel.toDTO(), sectionName);
	}

	public SectionStudentsDTO toStudentSectionDTO() {
		return new SectionStudentsDTO(id, teacher.toDTO(), room, strand.toDTO(), gradeLevel.toDTO(), sectionName, students.stream().map(Student::toDTO).toList());
	}
}