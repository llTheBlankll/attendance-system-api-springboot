

package com.pshs.attendance_system.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pshs.attendance_system.models.dto.StudentDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

	@Column(name = "prefix", length = 4)
	private String prefix;

	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "grade_level")
	private GradeLevel gradeLevel;

	@Column(name = "sex", length = 6)
	private String sex;

	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "section_id")
	private Section section;

	@OneToOne(mappedBy = "student")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Guardian guardian;

	@Column(name = "address", length = Integer.MAX_VALUE)
	private String address;

	@Column(name = "birthdate", nullable = false)
	private LocalDate birthdate;

	public Student() {
	}

	@Override
	public String toString() {
		return "Student{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", middleInitial='" + middleInitial + '\'' +
			", lastName='" + lastName + '\'' +
			", prefix='" + prefix + '\'' +
			", gradeLevel=" + gradeLevel +
			", sex='" + sex + '\'' +
			", section=" + section +
			", guardian=" + guardian +
			", address='" + address + '\'' +
			", birthdate=" + birthdate +
			'}';
	}

	public Student(Long id, String firstName, String middleInitial, String lastName, String prefix, GradeLevel gradeLevel, String sex, Section section, String address, LocalDate birthdate, Guardian guardian) {
		this.id = id;
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.prefix = prefix;
		this.gradeLevel = gradeLevel;
		this.sex = sex;
		this.section = section;
		this.address = address;
		this.birthdate = birthdate;
		this.guardian = guardian;
	}

	public StudentDTO toDTO() {
		return new StudentDTO(id, firstName, middleInitial, lastName, prefix, gradeLevel.toDTO(), sex, section.toDTO(), address, birthdate, (guardian != null) ? guardian.toDTO() : null);
	}

	public Long getId() {
		return id;
	}

	public Student setId(Long id) {
		this.id = id;
		return this;
	}

	public Guardian getGuardian() {
		return guardian;
	}

	public Student setGuardian(Guardian guardian) {
		this.guardian = guardian;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public Student setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public Student setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Student setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getPrefix() {
		return prefix;
	}

	public Student setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public GradeLevel getGradeLevel() {
		return gradeLevel;
	}

	public Student setGradeLevel(GradeLevel gradeLevel) {
		this.gradeLevel = gradeLevel;
		return this;
	}

	public String getSex() {
		return sex;
	}

	public Student setSex(String sex) {
		this.sex = sex;
		return this;
	}

	public Section getSection() {
		return section;
	}

	public Student setSection(Section section) {
		this.section = section;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Student setAddress(String address) {
		this.address = address;
		return this;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public Student setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
		return this;
	}
}