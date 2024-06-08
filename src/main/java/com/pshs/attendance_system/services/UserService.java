

package com.pshs.attendance_system.services;

import com.pshs.attendance_system.entities.User;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;

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
	 * Search all users with the given username.
	 *
	 * @param username The username that will be searched.
	 * @param page     Page
	 * @param size     Shows how many users will it display.
	 * @return return the page object
	 */
	Page<User> searchUsersByUsername(String username, int page, int size);

	/**
	 * Search all users with the given email.
	 *
	 * @param email The email that will be searched.
	 * @param page  Page
	 * @param size  Shows how many users will it display.
	 * @return return the page object
	 */
	Page<User> searchUsersByEmail(String email, int page, int size);

	/**
	 * Search users with the given role.
	 *
	 * @param role The role of the user.
	 * @param page Page
	 * @param size Shows how many users will it display.
	 * @return return the page object
	 */
	Page<User> searchUsersByRole(String role, int page, int size);

	/**
	 * Search all users with the given username and email.
	 *
	 * @param username The username that will be searched.
	 * @param email    The email that will be searched.
	 * @param page     Page
	 * @param size     Shows how many users will it display.
	 * @return return the page object
	 */
	Page<User> searchUsersByUsernameAndEmail(String username, String email, int page, int size);

	/**
	 * Search all users with the given role and username.
	 *
	 * @param username The username that will be searched.
	 * @param role     The user with the given role.
	 * @param page     Page
	 * @param size     Shows how many users will it display.
	 * @return return the page object
	 */
	Page<User> searchUsersByUsernameAndRole(String username, String role, int page, int size);

	/**
	 * Search all users with the given role and email.
	 *
	 * @param email The email that will be searched.
	 * @param role  The user with the given role.
	 * @param page  Page
	 * @param size  Shows how many users will it display.
	 * @return return the page object
	 */
	Page<User> searchUsersByEmailAndRole(String email, String role, int page, int size);

	/**
	 * Search users by their username, email, and role.
	 *
	 * @param username The username of the user that will be searched
	 * @param email    The email of the user that will be searched.
	 * @param role     The role of the user that will be searced.
	 * @param page     The page
	 * @param size     The maximum size of a page.
	 * @return return the page of users.
	 */
	Page<User> searchUsersByUsernameAndEmailAndRole(String username, String email, String role, int page, int size);

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