package com.pshs.attendance_system.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {
	@Id
	@Column(name = "lrn", nullable = false)
	private Long id;

	@Column(name = "first_name", nullable = false, length = 128)
	private String firstName;

	@Column(name = "middle_initial", length = Integer.MAX_VALUE)
	private String middleInitial;

	@Column(name = "last_name", nullable = false, length = 128)
	private String lastName;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "grade_level")
	private GradeLevel gradeLevel;

	@Column(name = "sex", length = 6)
	private String sex;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "section_id")
	private Section section;

	@Column(name = "address", length = Integer.MAX_VALUE)
	private String address;

	@Column(name = "birthdate", nullable = false)
	private LocalDate birthdate;

	@OneToMany(mappedBy = "studentLrn")
	private Set<Guardian> guardians = new LinkedHashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public GradeLevel getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(GradeLevel gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public Set<Guardian> getGuardians() {
		return guardians;
	}

	public void setGuardians(Set<Guardian> guardians) {
		this.guardians = guardians;
	}

}