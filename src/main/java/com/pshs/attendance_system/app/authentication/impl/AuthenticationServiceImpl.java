

package com.pshs.attendance_system.app.authentication.impl;

import com.pshs.attendance_system.app.authentication.exceptions.InvalidUserException;
import com.pshs.attendance_system.app.authentication.models.dto.LoginDTO;
import com.pshs.attendance_system.app.users.models.entities.User;
import com.pshs.attendance_system.app.authentication.services.AuthenticationService;
import com.pshs.attendance_system.app.users.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

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
	public User changePassword(User user, String newPassword) {
		User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (loggedUser == null) {
			throw new InvalidUserException("The password of the user cannot be change because the user is not logged in.");
		}

		// * Check if the logged user is the same as the user that is trying to change the password.
		if (!Objects.equals(loggedUser.getId(), user.getId()) && !Objects.equals(loggedUser.getUsername(), user.getUsername())) {
			throw new InvalidUserException("The password of the user cannot be change because the user is not logged in.");
		}

		loggedUser.setPassword(passwordEncoder.encode(newPassword));
		userService.updateUserPassword(user.getId(), newPassword);
	}

	@Override
	public User resetPassword(User user) {
		return null;
	}
}