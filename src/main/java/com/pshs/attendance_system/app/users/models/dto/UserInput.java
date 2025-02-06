

package com.pshs.attendance_system.app.users.models.dto;

import com.pshs.attendance_system.app.users.models.entities.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInput implements Serializable {

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
}