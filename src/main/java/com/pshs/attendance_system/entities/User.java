

package com.pshs.attendance_system.entities;

import com.pshs.attendance_system.dto.UserCreationDTO;
import com.pshs.attendance_system.dto.UserDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
	@SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "username", length = 64)
	private String username;

	@Column(name = "password", length = 64)
	private String password;

	@Column(name = "email", length = 128)
	private String email;

	@Column(name = "role", length = 48)
	private String role;

	@Column(name = "is_enabled", columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isEnabled = true;

	@Column(name = "is_expired", columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isExpired = false;

	@Column(name = "is_locked", columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isLocked = false;

	@Column(name = "is_credentials_expired", columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isCredentialsExpired = false;

	@Column(name = "last_login")
	private Instant lastLogin;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "created_at")
	private Instant createdAt;

	public User() {
	}

	public User(Integer id, String username, String password, String email, String role, Instant lastLogin, Instant createdAt) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.lastLogin = lastLogin;
		this.createdAt = createdAt;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public UserDTO toDTO() {
		return new UserDTO(id, username, email, role, lastLogin, createdAt);
	}

	public UserCreationDTO toUserCreationDTO() {
		return new UserCreationDTO(this.toDTO(), isEnabled, isExpired, isLocked, isCredentialsExpired);
	}

	public Integer getId() {
		return id;
	}

	public User setId(Integer id) {
		this.id = id;
		return this;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !isExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !isCredentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public Boolean getEnabled() {
		return isEnabled;
	}

	public User setEnabled(Boolean enabled) {
		isEnabled = enabled;
		return this;
	}

	public Boolean getExpired() {
		return isExpired;
	}

	public User setExpired(Boolean expired) {
		isExpired = expired;
		return this;
	}

	public Boolean getLocked() {
		return isLocked;
	}

	public User setLocked(Boolean locked) {
		isLocked = locked;
		return this;
	}

	public Boolean getCredentialsExpired() {
		return isCredentialsExpired;
	}

	public User setCredentialsExpired(Boolean credentialsExpired) {
		isCredentialsExpired = credentialsExpired;
		return this;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(
			(GrantedAuthority) () -> role
		);
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getRole() {
		return role;
	}

	public User setRole(String role) {
		this.role = role;
		return this;
	}

	public Instant getLastLogin() {
		return lastLogin;
	}

	public User setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
		return this;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public User setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
		return this;
	}
}