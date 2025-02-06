

package com.pshs.attendance_system.app.rfid_credentials.models.dto;

import com.pshs.attendance_system.app.rfid_credentials.models.entities.RFIDCredential;
import com.pshs.attendance_system.app.students.models.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link RFIDCredential}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RFIDCredentialDTO implements Serializable {
	private Integer id;
	private StudentDTO lrn;
	private String hashedLrn;
	private String salt;

	public RFIDCredential toEntity() {
		return new RFIDCredential(
			id,
			lrn.toEntity(),
			hashedLrn,
			salt
		);
	}
}