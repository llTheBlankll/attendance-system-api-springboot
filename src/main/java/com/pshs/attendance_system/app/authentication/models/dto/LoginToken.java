

package com.pshs.attendance_system.app.authentication.models.dto;

import com.pshs.attendance_system.app.users.models.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginToken implements Serializable {

	private String username;
	private String token;
	private String role;
	private Date expiration;
	private UserDTO user;
}