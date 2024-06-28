

package com.pshs.attendance_system.entities;

import com.pshs.attendance_system.dto.TeacherDTO;
import com.pshs.attendance_system.dto.UserDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teachers_id_gen")
	@SequenceGenerator(name = "teachers_id_gen", sequenceName = "teachers_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "first_name", length = 32)
	private String firstName;

	@Column(name = "last_name", length = 32)
	private String lastName;

	@Column(name = "sex", length = 8)
	private String sex;

	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@OneToOne(fetch = FetchType.EAGER)
	private User user;

	public Teacher(Integer id, String firstName, String lastName, String sex, User user) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.user = user;
	}

	public Teacher() {
	}

	public User getUser() {
		// If the teacher user is null, then return a new user to avoid NullPointerException.
		if (user == null) {
			return new User();
		}

		return user;
	}

	public Teacher setUser(User user) {
		this.user = user;
		return this;
	}

	public TeacherDTO toDTO() {
		return new TeacherDTO(id, firstName, lastName, sex, (user != null) ? user.toDTO() : new UserDTO());
	}

	public Integer getId() {
		return id;
	}

	public Teacher setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public Teacher setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Teacher setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getSex() {
		return sex;
	}

	public Teacher setSex(String sex) {
		this.sex = sex;
		return this;
	}
}