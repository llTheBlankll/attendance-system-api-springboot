package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.User}
 */
public class UserDTO implements Serializable {
	private Integer id;
	private String username;
	private String password;
	private String email;
	private String roleId;
	private LocalDateTime lastLogin;
	private LocalDateTime createdAt;

	public UserDTO(Integer id, String username, String password, String email, String roleId, LocalDateTime lastLogin, LocalDateTime createdAt) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.roleId = roleId;
		this.lastLogin = lastLogin;
		this.createdAt = createdAt;
	}

	public User toEntity() {
		return new User(id, username, password, email, roleId, lastLogin, createdAt);
	}

	public UserDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public UserDTO setUsername(String username) {
		this.username = username;
		return this;
	}

	public UserDTO setPassword(String password) {
		this.password = password;
		return this;
	}

	public UserDTO setEmail(String email) {
		this.email = email;
		return this;
	}

	public UserDTO setRoleId(String roleId) {
		this.roleId = roleId;
		return this;
	}

	public UserDTO setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
		return this;
	}

	public UserDTO setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getRoleId() {
		return roleId;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDTO entity = (UserDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.username, entity.username) &&
			Objects.equals(this.password, entity.password) &&
			Objects.equals(this.email, entity.email) &&
			Objects.equals(this.roleId, entity.roleId) &&
			Objects.equals(this.lastLogin, entity.lastLogin) &&
			Objects.equals(this.createdAt, entity.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, email, roleId, lastLogin, createdAt);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"username = " + username + ", " +
			"password = " + password + ", " +
			"email = " + email + ", " +
			"roleId = " + roleId + ", " +
			"lastLogin = " + lastLogin + ", " +
			"createdAt = " + createdAt + ")";
	}
}