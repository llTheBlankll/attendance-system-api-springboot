

package com.pshs.attendance_system.app.guardians.models.dto;

import com.pshs.attendance_system.app.guardians.models.entities.Guardian;
import com.pshs.attendance_system.app.students.models.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Guardian}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuardianDTO implements Serializable {
	private Integer id;
	private String fullName;
	private String contactNumber;
	@JsonIgnore
	private StudentDTO student;

	public Guardian toEntity() {
		Guardian guardian = new Guardian();
		guardian.setId(id);
		guardian.setFullName(fullName);
		guardian.setContactNumber(contactNumber);
		return guardian;
	}
}