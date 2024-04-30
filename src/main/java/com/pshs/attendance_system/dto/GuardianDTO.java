package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Guardian;
import com.pshs.attendance_system.entities.Student;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Guardian}
 */
public class GuardianDTO implements Serializable {
	private Integer id;
	private String fullName;
	private StudentDTO studentLrn;
	private String contactNumber;

	public GuardianDTO(Integer id, String fullName, StudentDTO studentLrn, String contactNumber) {
		this.id = id;
		this.fullName = fullName;
		this.studentLrn = studentLrn;
		this.contactNumber = contactNumber;
	}

	public Guardian toEntity() {
		return new Guardian(id, studentLrn.toEntity(), fullName, contactNumber);
	}

	public GuardianDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public GuardianDTO setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public GuardianDTO setStudentLrn(StudentDTO studentLrn) {
		this.studentLrn = studentLrn;
		return this;
	}

	public GuardianDTO setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
		return this;
	}

	public StudentDTO getStudentLrn() {
		return studentLrn;
	}

	public Integer getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GuardianDTO entity = (GuardianDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.fullName, entity.fullName) &&
			Objects.equals(this.contactNumber, entity.contactNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fullName, contactNumber);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"fullName = " + fullName + ", " +
			"contactNumber = " + contactNumber + ")";
	}
}