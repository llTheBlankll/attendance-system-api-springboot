

package com.pshs.attendance_system.app.authentication.services;

import com.pshs.attendance_system.app.authentication.models.dto.LoginDTO;
import com.pshs.attendance_system.app.users.models.entities.User;

public interface AuthenticationService {

	User createUser(User user);

	User signIn(LoginDTO loginDTO);

	User signOut(User user);

	User changePassword(User user, String newPassword);

	User resetPassword(User user);
}