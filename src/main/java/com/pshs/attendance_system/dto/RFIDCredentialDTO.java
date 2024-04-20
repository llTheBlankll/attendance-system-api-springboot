package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.RFIDCredential;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link RFIDCredential}
 */
public class RFIDCredentialDTO implements Serializable {
	private final Integer id;
	private final StudentDTO lrn;
	private final String hashedLrn;
	private final String salt;

	public RFIDCredentialDTO(Integer id, StudentDTO lrn, String hashedLrn, String salt) {
		this.id = id;
		this.lrn = lrn;
		this.hashedLrn = hashedLrn;
		this.salt = salt;
	}

	public RFIDCredential toEntity() {
		return new RFIDCredential(id, lrn.toEntity(), hashedLrn, salt);
	}

	public Integer getId() {
		return id;
	}

	public StudentDTO getLrn() {
		return lrn;
	}

	public String getHashedLrn() {
		return hashedLrn;
	}

	public String getSalt() {
		return salt;
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