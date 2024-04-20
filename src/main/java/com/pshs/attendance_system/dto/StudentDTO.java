package com.pshs.attendance_system.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Student}
 */
public class StudentDTO implements Serializable {
	private final Long id;
	private final String firstName;
	private final String middleInitial;
	private final String lastName;
	private final GradeLevelDTO gradeLevel;
	private final String sex;
	private final SectionDTO section;
	private final String address;
	private final LocalDate birthdate;
	private final Set<GuardianDTO> guardians;

	public StudentDTO(Long id, String firstName, String middleInitial, String lastName, GradeLevelDTO gradeLevel, String sex, SectionDTO section, String address, LocalDate birthdate, Set<GuardianDTO> guardians) {
		this.id = id;
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.gradeLevel = gradeLevel;
		this.sex = sex;
		this.section = section;
		this.address = address;
		this.birthdate = birthdate;
		this.guardians = guardians;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public GradeLevelDTO getGradeLevel() {
		return gradeLevel;
	}

	public String getSex() {
		return sex;
	}

	public SectionDTO getSection() {
		return section;
	}

	public String getAddress() {
		return address;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public Set<GuardianDTO> getGuardians() {
		return guardians;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StudentDTO entity = (StudentDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.firstName, entity.firstName) &&
			Objects.equals(this.middleInitial, entity.middleInitial) &&
			Objects.equals(this.lastName, entity.lastName) &&
			Objects.equals(this.gradeLevel, entity.gradeLevel) &&
			Objects.equals(this.sex, entity.sex) &&
			Objects.equals(this.section, entity.section) &&
			Objects.equals(this.address, entity.address) &&
			Objects.equals(this.birthdate, entity.birthdate) &&
			Objects.equals(this.guardians, entity.guardians);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, middleInitial, lastName, gradeLevel, sex, section, address, birthdate, guardians);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"firstName = " + firstName + ", " +
			"middleInitial = " + middleInitial + ", " +
			"lastName = " + lastName + ", " +
			"gradeLevel = " + gradeLevel + ", " +
			"sex = " + sex + ", " +
			"section = " + section + ", " +
			"address = " + address + ", " +
			"birthdate = " + birthdate + ", " +
			"guardians = " + guardians + ")";
	}
}