package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Student;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Student}
 */
public class StudentDTO implements Serializable {
	private Long id;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private GradeLevelDTO gradeLevel;
	private String sex;
	private SectionDTO section;
	private String address;
	private LocalDate birthdate;
	private List<GuardianDTO> guardians;

	public StudentDTO(Long id, String firstName, String middleInitial, String lastName, GradeLevelDTO gradeLevel, String sex, SectionDTO section, String address, LocalDate birthdate, List<GuardianDTO> guardians) {
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

	public Student toEntity() {
		return new Student(
			id,
			firstName,
			middleInitial,
			lastName,
			gradeLevel.toEntity(),
			sex,
			section.toEntity(),
			address,
			birthdate,
			guardians.stream().map(GuardianDTO::toEntity).collect(Collectors.toList())
			);
	}

	public StudentDTO setId(Long id) {
		this.id = id;
		return this;
	}

	public StudentDTO setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public StudentDTO setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
		return this;
	}

	public StudentDTO setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public StudentDTO setGradeLevel(GradeLevelDTO gradeLevel) {
		this.gradeLevel = gradeLevel;
		return this;
	}

	public StudentDTO setSex(String sex) {
		this.sex = sex;
		return this;
	}

	public StudentDTO setSection(SectionDTO section) {
		this.section = section;
		return this;
	}

	public StudentDTO setAddress(String address) {
		this.address = address;
		return this;
	}

	public StudentDTO setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
		return this;
	}

	public StudentDTO setGuardians(List<GuardianDTO> guardians) {
		this.guardians = guardians;
		return this;
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

	public List<GuardianDTO> getGuardians() {
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