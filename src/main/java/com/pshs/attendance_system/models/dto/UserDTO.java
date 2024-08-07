

package com.pshs.attendance_system.models.dto;

import com.pshs.attendance_system.models.entities.User;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link User}
 */
public class UserDTO implements Serializable {
	private Integer id;

	@NotBlank(message = "Username is required")
	private String username;
	private String email;
	private String role;
	private Instant lastLogin;
	private Instant createdAt;

	public UserDTO() {
	}

	public UserDTO(Integer id, String username, String email, String role, Instant lastLogin, Instant createdAt) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
		this.lastLogin = lastLogin;
		this.createdAt = createdAt;
	}

	public User toEntity() {
		return new User()
			.setId(id)
			.setUsername(username)
			.setEmail(email)
			.setRole(role)
			.setLastLogin(lastLogin)
			.setCreatedAt(createdAt);
	}

	public Integer getId() {
		return id;
	}

	public UserDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public UserDTO setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserDTO setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getRole() {
		return role;
	}

	public UserDTO setRole(String role) {
		this.role = role;
		return this;
	}

	public Instant getLastLogin() {
		return lastLogin;
	}

	public UserDTO setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
		return this;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public UserDTO setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDTO entity = (UserDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.username, entity.username) &&
			Objects.equals(this.email, entity.email) &&
			Objects.equals(this.role, entity.role) &&
			Objects.equals(this.lastLogin, entity.lastLogin) &&
			Objects.equals(this.createdAt, entity.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, email, role, lastLogin, createdAt);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"username = " + username + ", " +
			"email = " + email + ", " +
			"role = " + role + ", " +
			"lastLogin = " + lastLogin + ", " +
			"createdAt = " + createdAt + ")";
	}
}