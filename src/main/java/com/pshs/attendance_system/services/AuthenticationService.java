

package com.pshs.attendance_system.services;

import com.pshs.attendance_system.models.dto.authentication.LoginDTO;
import com.pshs.attendance_system.models.entities.User;

public interface AuthenticationService {

	User createUser(User user);

	User signIn(LoginDTO loginDTO);

	User signOut(User user);

	User changePassword(User user, String newPassword);

	User resetPassword(User user);
}