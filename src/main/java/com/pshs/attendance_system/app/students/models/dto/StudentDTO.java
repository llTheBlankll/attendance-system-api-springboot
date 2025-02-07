

package com.pshs.attendance_system.app.students.models.dto;

import com.pshs.attendance_system.app.gradelevels.models.dto.GradeLevelDTO;
import com.pshs.attendance_system.app.guardians.models.dto.GuardianDTO;
import com.pshs.attendance_system.app.guardians.models.entities.Guardian;
import com.pshs.attendance_system.app.sections.models.dto.SectionDTO;
import com.pshs.attendance_system.app.students.enums.Sex;
import com.pshs.attendance_system.app.students.models.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Student}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO implements Serializable {
	private Long id;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private String prefix;
	private GradeLevelDTO gradeLevel;
	private GuardianDTO guardian;
	private Sex sex;
	private SectionDTO section;
	private String address;
	private LocalDate birthdate;

	public Student toEntity() {
		return new Student(
			id,
			firstName,
			middleInitial,
			lastName,
			prefix,
			gradeLevel.toEntity(),
			sex,
			section.toEntity(),
			(guardian != null) ? guardian.toEntity() : null,
			address,
			birthdate
		);
	}
}