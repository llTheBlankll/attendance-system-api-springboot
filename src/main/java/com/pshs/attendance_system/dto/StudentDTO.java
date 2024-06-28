

package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Guardian;
import com.pshs.attendance_system.entities.Student;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Student}
 */
public class StudentDTO implements Serializable {
	private Long id;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private String prefix;
	private GradeLevelDTO gradeLevel;
	private GuardianDTO guardian;
	private String sex;
	private SectionDTO section;
	private String address;
	private LocalDate birthdate;

	public StudentDTO() {
	}

	public StudentDTO(Long id, String firstName, String middleInitial, String lastName, String prefix, GradeLevelDTO gradeLevel, String sex, SectionDTO section, String address, LocalDate birthdate, GuardianDTO guardian) {
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

	public Student toEntity() {
		return new Student()
			.setId(id)
			.setFirstName(firstName)
			.setMiddleInitial(middleInitial)
			.setLastName(lastName)
			.setGradeLevel(gradeLevel.toEntity())
			.setSection(section.toEntity())
			.setSex(sex)
			.setAddress(address)
			.setBirthdate(birthdate)
			.setGuardian((this.guardian == null) ? new Guardian() : this.guardian.toEntity());
	}

	public Long getId() {
		return id;
	}

	public StudentDTO setId(Long id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public StudentDTO setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public StudentDTO setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public StudentDTO setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getPrefix() {
		return prefix;
	}

	public StudentDTO setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public GradeLevelDTO getGradeLevel() {
		return gradeLevel;
	}

	public StudentDTO setGradeLevel(GradeLevelDTO gradeLevel) {
		this.gradeLevel = gradeLevel;
		return this;
	}

	public GuardianDTO getGuardian() {
		return guardian;
	}

	public StudentDTO setGuardian(GuardianDTO guardian) {
		this.guardian = guardian;
		return this;
	}

	public String getSex() {
		return sex;
	}

	public StudentDTO setSex(String sex) {
		this.sex = sex;
		return this;
	}

	public SectionDTO getSection() {
		return section;
	}

	public StudentDTO setSection(SectionDTO section) {
		this.section = section;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public StudentDTO setAddress(String address) {
		this.address = address;
		return this;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public StudentDTO setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
		return this;
	}
}