

package com.pshs.attendance_system.app.teachers.models.dto;

import com.pshs.attendance_system.app.students.enums.Sex;
import com.pshs.attendance_system.app.teachers.models.entities.Teacher;
import com.pshs.attendance_system.app.users.models.dto.UserDTO;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Teacher}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDTO implements Serializable {
	private Integer id;
	private String firstName;
	private String lastName;
	private Sex sex;
	private UserDTO user;

	public TeacherDTO(Teacher teacher) {
		this.id = teacher.getId();
		this.firstName = teacher.getFirstName();
		this.lastName = teacher.getLastName();
		this.sex = teacher.getSex();
		this.user = teacher.getUser().toDTO();
	}

	public Teacher toEntity() {
		return new Teacher(
			id,
			firstName,
			lastName,
			sex,
			user.toEntity()
		);
	}
}