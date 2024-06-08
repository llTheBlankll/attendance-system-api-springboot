

package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.User;

import java.io.Serializable;

public class UserCreationDTO extends UserDTO implements Serializable {

	private String password;
	private boolean isLocked;
	private boolean isEnabled;
	private boolean isExpired;
	private boolean isCredentialsExpired;

	public UserCreationDTO(boolean isLocked, boolean isEnabled, boolean isExpired, boolean isCredentialsExpired) {
		this.isLocked = isLocked;
		this.isEnabled = isEnabled;
		this.isExpired = isExpired;
		this.isCredentialsExpired = isCredentialsExpired;
	}

	public UserCreationDTO(UserDTO user, boolean isLocked, boolean isEnabled, boolean isExpired, boolean isCredentialsExpired) {
		super(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getLastLogin(), user.getCreatedAt());
		this.isLocked = isLocked;
		this.isEnabled = isEnabled;
		this.isExpired = isExpired;
		this.isCredentialsExpired = isCredentialsExpired;
	}

	public User toEntity() {
		return new User()
			.setId(getId())
			.setUsername(getUsername())
			.setPassword(password)
			.setEmail(getEmail())
			.setRole(getRole())
			.setLastLogin(getLastLogin())
			.setCreatedAt(getCreatedAt())
			.setLocked(isLocked)
			.setEnabled(isEnabled)
			.setExpired(isExpired)
			.setCredentialsExpired(isCredentialsExpired);
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