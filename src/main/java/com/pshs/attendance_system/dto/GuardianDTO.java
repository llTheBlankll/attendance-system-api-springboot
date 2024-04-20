package com.pshs.attendance_system.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Guardian}
 */
public class GuardianDTO implements Serializable {
	private final Integer id;
	private final String fullName;
	private final String contactNumber;

	public GuardianDTO(Integer id, String fullName, String contactNumber) {
		this.id = id;
		this.fullName = fullName;
		this.contactNumber = contactNumber;
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