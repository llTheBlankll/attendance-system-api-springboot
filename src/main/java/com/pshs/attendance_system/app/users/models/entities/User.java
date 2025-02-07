

package com.pshs.attendance_system.app.users.models.entities;

import com.pshs.attendance_system.app.users.models.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

	public UserDTO toDTO() {
		return new UserDTO(id, username, email, role, lastLogin, createdAt);
	}

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	/**
	 * Indicates whether the user's account has expired. An expired account cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user's account is valid (ie non-expired),
	 * <code>false</code> if no longer valid (ie expired)
	 */
	@Override
	public boolean isAccountNonExpired() {
		return !isExpired;
	}

	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
	 */
	@Override
	public boolean isAccountNonLocked() {
		return !isLocked;
	}

	/**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 *
	 * @return <code>true</code> if the user's credentials are valid (ie non-expired),
	 * <code>false</code> if no longer valid (ie expired)
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return !isCredentialsExpired;
	}

	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
	 */
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(
			(GrantedAuthority) () -> role
		);
	}
}