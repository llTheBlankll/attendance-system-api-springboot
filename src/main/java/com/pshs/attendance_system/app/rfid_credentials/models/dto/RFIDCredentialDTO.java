

package com.pshs.attendance_system.app.rfid_credentials.models.dto;

import com.pshs.attendance_system.app.rfid_credentials.models.entities.RFIDCredential;
import com.pshs.attendance_system.app.students.models.dto.StudentDTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link RFIDCredential}
 */
public class RFIDCredentialDTO implements Serializable {
	private Integer id;
	private StudentDTO lrn;
	private String hashedLrn;
	private String salt;

	public RFIDCredentialDTO() {
	}

	public RFIDCredentialDTO(Integer id, StudentDTO lrn, String hashedLrn, String salt) {
		this.id = id;
		this.lrn = lrn;
		this.hashedLrn = hashedLrn;
		this.salt = salt;
	}

	public RFIDCredential toEntity() {
		return new RFIDCredential()
			.setId(id)
			.setLrn(lrn.toEntity())
			.setHashedLrn(hashedLrn)
			.setSalt(salt);
	}

	public Integer getId() {
		return id;
	}

	public RFIDCredentialDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public StudentDTO getLrn() {
		return lrn;
	}

	public RFIDCredentialDTO setLrn(StudentDTO lrn) {
		this.lrn = lrn;
		return this;
	}

	public String getHashedLrn() {
		return hashedLrn;
	}

	public RFIDCredentialDTO setHashedLrn(String hashedLrn) {
		this.hashedLrn = hashedLrn;
		return this;
	}

	public String getSalt() {
		return salt;
	}

	public RFIDCredentialDTO setSalt(String salt) {
		this.salt = salt;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RFIDCredentialDTO entity = (RFIDCredentialDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.lrn, entity.lrn) &&
			Objects.equals(this.hashedLrn, entity.hashedLrn) &&
			Objects.equals(this.salt, entity.salt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, lrn, hashedLrn, salt);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"lrn = " + lrn + ", " +
			"hashedLrn = " + hashedLrn + ", " +
			"salt = " + salt + ")";
	}
}