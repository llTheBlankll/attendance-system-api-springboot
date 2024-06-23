package com.pshs.attendance_system.dto.authentication;

import java.io.Serializable;

public class ChangePasswordDTO implements Serializable {

	private String oldPassword;
	private String newPassword;

	public ChangePasswordDTO() {
	}

	public ChangePasswordDTO(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public ChangePasswordDTO setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
		return this;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public ChangePasswordDTO setNewPassword(String newPassword) {
		this.newPassword = newPassword;
		return this;
	}
}