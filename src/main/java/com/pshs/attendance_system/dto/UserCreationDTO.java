

package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

public class UserCreationDTO implements Serializable {

	@NotBlank(message = "Username is required")
	private String username;
	private String email;
	private String role;
	private Instant lastLogin;
	private Instant createdAt;

	@NotBlank(message = "Password is required")
	private String password;
	private boolean isLocked;
	private boolean isEnabled;
	private boolean isExpired;
	private boolean isCredentialsExpired;

	public UserCreationDTO() {
	}

	public User toEntity() {
		return new User()
			.setUsername(username)
			.setEmail(email)
			.setRole(role)
			.setLastLogin(lastLogin)
			.setCreatedAt(createdAt)
			.setPassword(password)
			.setLocked(isLocked)
			.setEnabled(isEnabled)
			.setExpired(isExpired)
			.setCredentialsExpired(isCredentialsExpired);
	}

	public @NotBlank(message = "Username is required") String getUsername() {
		return username;
	}

	public UserCreationDTO setUsername(@NotBlank(message = "Username is required") String username) {
		this.username = username;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserCreationDTO setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getRole() {
		return role;
	}

	public UserCreationDTO setRole(String role) {
		this.role = role;
		return this;
	}

	public Instant getLastLogin() {
		return lastLogin;
	}

	public UserCreationDTO setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
		return this;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public UserCreationDTO setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserCreationDTO setPassword(String password) {
		this.password = password;
		return this;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public UserCreationDTO setLocked(boolean locked) {
		isLocked = locked;
		return this;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public UserCreationDTO setEnabled(boolean enabled) {
		isEnabled = enabled;
		return this;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public UserCreationDTO setExpired(boolean expired) {
		isExpired = expired;
		return this;
	}

	public boolean isCredentialsExpired() {
		return isCredentialsExpired;
	}

	public UserCreationDTO setCredentialsExpired(boolean credentialsExpired) {
		isCredentialsExpired = credentialsExpired;
		return this;
	}
}