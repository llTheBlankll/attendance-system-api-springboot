

package com.pshs.attendance_system.dto;

import java.io.Serializable;
import java.util.Date;

public class LoginToken implements Serializable {

	private String username;
	private String token;
	private String role;
	private Date expiration;
	private UserDTO user;

	public LoginToken(String username, String token, String role, Date expiration, UserDTO user) {
		this.username = username;
		this.token = token;
		this.role = role;
		this.expiration = expiration;
		this.user = user;
	}

	public Date getExpiration() {
		return expiration;
	}

	public LoginToken setExpiration(Date expiration) {
		this.expiration = expiration;
		return this;
	}

	public UserDTO getUser() {
		return user;
	}

	public LoginToken setUser(UserDTO user) {
		this.user = user;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public LoginToken setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getToken() {
		return token;
	}

	public LoginToken setToken(String token) {
		this.token = token;
		return this;
	}

	public String getRole() {
		return role;
	}

	public LoginToken setRole(String role) {
		this.role = role;
		return this;
	}
}