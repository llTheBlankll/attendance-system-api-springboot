

package com.pshs.attendance_system.app.users.services;

import com.pshs.attendance_system.app.users.models.dto.UserSearchInput;
import com.pshs.attendance_system.app.users.models.entities.User;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

public interface UserService {

	/**
	 * Create a new user with the given user object.
	 *
	 * @param user User Object
	 * @return ExecutionStatus (SUCCESS, FAILURE, VALIDATION_ERROR)
	 */
	User createUser(User user);

	/**
	 * Delete a user with the given user id.
	 *
	 * @param userId User ID
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 */
	ExecutionStatus deleteUser(int userId);

	/**
	 * Update a user with the given user id and user object.
	 *
	 * @param userId User ID
	 * @param user   User Object
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 */
	ExecutionStatus updateUser(int userId, User user);

	/**
	 * Update a user's password with the given user id and password.
	 *
	 * @param userId   User ID that needs to be updated
	 * @param password New password of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 */
	ExecutionStatus updateUserPassword(int userId, String password);

	/**
	 * Update a user's last login time with the given user id and last login time.
	 *
	 * @param userId    User ID that needs to be updated
	 * @param lastLogin New last login time of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND) {@link ExecutionStatus}
	 */
	ExecutionStatus updateUserLastLogin(int userId, Instant lastLogin);

	/**
	 * Update the user's locked status with the given user id.
	 *
	 * @param userId   User ID that needs to be updated
	 * @param isLocked New locked status of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 * If the status of the isLocked is the same as the current status,
	 * the method will return SUCCESS but nothing will query.
	 */
	ExecutionStatus changeUserLockStatus(int userId, boolean isLocked);

	/**
	 * Update the user's enabled status with the given user id.
	 *
	 * @param userId    User ID that needs to be updated
	 * @param isEnabled New enabled status of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 * If the status of the isEnabled is the same as the current status,
	 * the method will return SUCCESS but nothing will query.
	 */
	ExecutionStatus changeUserEnabledStatus(int userId, boolean isEnabled);

	/**
	 * Update the user's expired status with the given user id.
	 *
	 * @param userId    User ID that needs to be updated
	 * @param isExpired New expired status of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 * If the status of the isExpired is the same as the current status,
	 * the method will return SUCCESS but nothing will query.
	 */
	ExecutionStatus changeUserExpiredStatus(int userId, boolean isExpired);

	/**
	 * Update the user's credentials expired status with the given user id.
	 *
	 * @param userId               User ID that needs to be updated
	 * @param isCredentialsExpired New credentials expired status of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 * If the status of the isCredentialsExpired is the same as the current status,
	 * the method will return SUCCESS but nothing will query.
	 */
	ExecutionStatus changeUserCredentialsExpiredStatus(int userId, boolean isCredentialsExpired);


	/**
	 * Get a user with the given user id.
	 *
	 * @param userId User ID
	 * @return User Object
	 */
	User getUser(int userId);

	/**
	 * Get a user with the given username.
	 *
	 * @param username The username of the user.
	 * @return User Object
	 */
	User getUserByUsername(String username);

	/**
	 * Get all the users in existence within the database.
	 *
	 * @param page Page
	 * @param size Shows how many users will it display.
	 * @return return the page object
	 */
	Page<User> getAllUsers(int page, int size);

	/**
	 * Search for users based on the given search input criteria.
	 *
	 * @param searchInput The criteria used to search for users, including fields such as username, email, and role.
	 * @param pageable    The pagination information, including page number and size.
	 * @return A page of users that match the search criteria.
	 * @throws IllegalArgumentException if the search input is invalid or null
	 */
	Page<User> search(UserSearchInput searchInput, Pageable pageable) throws IllegalArgumentException;

	/**
	 * Check if the provided password matches the user's password.
	 *
	 * @param userId The user ID
	 * @param password The password to check The password to check The password to check The password to check
	 * @return true if the password matches, false otherwise
	 */
	Boolean isUserPasswordMatch(int userId, String password);

	// Region: User Statistics

	/**
	 * Count all the users in existence within the database.
	 *
	 * @return The number of users in the database.
	 */
	int countAllUsers();

	/**
	 * Count all the users with the given role in existence within the database.
	 *
	 * @param role The role of the user.
	 * @return The number of users with the given role in the database.
	 */
	int countAllUsersByRole(String role);
	// End Region: User Statistics
}