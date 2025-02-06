

package com.pshs.attendance_system.security;

import com.pshs.attendance_system.app.users.models.entities.User;
import com.pshs.attendance_system.app.users.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public JWTUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}
}