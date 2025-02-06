package com.pshs.attendance_system.app.users.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchInput {

	private String email;
	private String username;
	private Role role;
}
