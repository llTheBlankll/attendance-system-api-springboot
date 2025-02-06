

package com.pshs.attendance_system.app.teachers.models.dto;

import com.pshs.attendance_system.app.teachers.models.entities.Teacher;
import com.pshs.attendance_system.app.users.models.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Teacher}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO implements Serializable {
	private Integer id;
	private String firstName;
	private String lastName;
	private String sex;
	private UserDTO user;

	public TeacherDTO(Teacher teacher) {
		this.id = teacher.getId();
		this.firstName = teacher.getFirstName();
		this.lastName = teacher.getLastName();
		this.sex = teacher.getSex();
		this.user = teacher.getUser().toDTO();
	}

	public Teacher toEntity() {
		return new Teacher()
			.setId(id)
			.setFirstName(firstName)
			.setLastName(lastName)
			.setSex(sex)
			.setUser(user.toEntity());
	}
}