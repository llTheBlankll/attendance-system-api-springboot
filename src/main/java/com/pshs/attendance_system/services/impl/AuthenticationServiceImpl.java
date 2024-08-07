

package com.pshs.attendance_system.services.impl;

import com.pshs.attendance_system.models.dto.authentication.LoginDTO;
import com.pshs.attendance_system.models.entities.User;
import com.pshs.attendance_system.services.AuthenticationService;
import com.pshs.attendance_system.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class);
	private final UserService userService;
	private final AuthenticationManager authManager;
	private final PasswordEncoder passwordEncoder;

	public AuthenticationServiceImpl(UserService userService, AuthenticationManager authManager, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.authManager = authManager;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userService.createUser(user);
	}

	@Override
	public User signIn(LoginDTO loginDTO) {
		authManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginDTO.getUsername(), loginDTO.getPassword()
			)
		);

		User user = userService.getUserByUsername(loginDTO.getUsername());

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		} else {
			return user;
		}
	}

	@Override
	public User signOut(User user) {
		return null;
	}

	@Override
	public User changePassword(User user, String newPassword) {
		return null;
	}

	@Override
	public User resetPassword(User user) {
		return null;
	}
}