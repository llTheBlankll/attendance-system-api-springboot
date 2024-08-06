

package com.pshs.attendance_system.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pshs.attendance_system.models.dto.SectionDTO;
import com.pshs.attendance_system.models.dto.SectionStudentsDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sections")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

	public Section() {
	}

	public Section(Integer id, Teacher teacher, String room, Strand strand, GradeLevel gradeLevel, String sectionName, List<Student> students) {
		this.id = id;
		this.teacher = teacher;
		this.room = room;
		this.strand = strand;
		this.gradeLevel = gradeLevel;
		this.sectionName = sectionName;
		this.students = students;
	}

	public SectionDTO toDTO() {
		return new SectionDTO(id, teacher.toDTO(), room, strand.toDTO(), gradeLevel.toDTO(), sectionName);
	}

	public SectionStudentsDTO toStudentSectionDTO() {
		return new SectionStudentsDTO(id, teacher.toDTO(), room, strand.toDTO(), gradeLevel.toDTO(), sectionName, students.stream().map(Student::toDTO).toList());
	}

	public Integer getId() {
		return id;
	}

	public Section setId(Integer id) {
		this.id = id;
		return this;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Section setTeacher(Teacher teacher) {
		this.teacher = teacher;
		return this;
	}

	public String getRoom() {
		return room;
	}

	public Section setRoom(String room) {
		this.room = room;
		return this;
	}

	public Strand getStrand() {
		return strand;
	}

	public Section setStrand(Strand strand) {
		this.strand = strand;
		return this;
	}

	public GradeLevel getGradeLevel() {
		return gradeLevel;
	}

	public Section setGradeLevel(GradeLevel gradeLevel) {
		this.gradeLevel = gradeLevel;
		return this;
	}

	public String getSectionName() {
		return sectionName;
	}

	public Section setSectionName(String sectionName) {
		this.sectionName = sectionName;
		return this;
	}

	public List<Student> getStudents() {
		return students;
	}

	public Section setStudents(List<Student> students) {
		this.students = students;
		return this;
	}
}