

package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Guardian;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Guardian}
 */
public class GuardianDTO implements Serializable {
	private Integer id;
	private String fullName;
	private String contactNumber;

	public GuardianDTO() {
	}

	public GuardianDTO(Integer id, String fullName, String contactNumber) {
		this.id = id;
		this.fullName = fullName;
		this.contactNumber = contactNumber;
	}

	public Guardian toEntity() {
		return new Guardian()
			.setId(id)
			.setFullName(fullName)
			.setContactNumber(contactNumber);
	}

	public Integer getId() {
		return id;
	}

	public GuardianDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public GuardianDTO setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public GuardianDTO setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GuardianDTO that = (GuardianDTO) o;
		return Objects.equals(id, that.id) && Objects.equals(fullName, that.fullName) && Objects.equals(contactNumber, that.contactNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fullName, contactNumber);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "GuardianDTO{" +
			"id=" + id +
			", fullName='" + fullName + '\'' +
			", contactNumber='" + contactNumber + '\'' +
			'}';
	}
}