

package com.pshs.attendance_system.impl;

import com.pshs.attendance_system.entities.User;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.repositories.UserRepository;
import com.pshs.attendance_system.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Create a new user with the given user object.
	 *
	 * @param user User Object
	 * @return ExecutionStatus (SUCCESS, FAILURE, VALIDATION_ERROR)
	 */
	@Override
	public User createUser(User user) {
		if (validateUser(user) && isUserExists(user.getId())) {
			logger.debug("User {} already exists.", user.getId());
			return user;
		}

		logger.debug("User {} has been created.", user.getId());
		return userRepository.save(user);
	}

	/**
	 * Update a user's last login time with the given user id and last login time.
	 *
	 * @param userId    User ID that needs to be updated
	 * @param lastLogin New last login time of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND) {@link ExecutionStatus}
	 */
	@Override
	public ExecutionStatus updateUserLastLogin(int userId, Instant lastLogin) {
		if (!isUserExists(userId)) {
			return userNotFoundLog(userId);
		}

		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isPresent()) {
			userRepository.updateUserLastLoginTime(lastLogin, userId);
			return ExecutionStatus.SUCCESS;
		}

		return ExecutionStatus.FAILURE;
	}

	/**
	 * Delete a user with the given user id.
	 *
	 * @param userId User ID
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 */
	@Override
	public ExecutionStatus deleteUser(int userId) {
		// Is user negative? then nahh
		if (userId <= 0) {
			return userInvalidId(userId);
		}

		// Does this person exists? If no, then return not found.
		if (!isUserExists(userId)) {
			return userNotFoundLog(userId);
		}

		// delete the user!
		userRepository.deleteById(userId);
		logger.debug("User {} has been deleted.", userId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update a user with the given user id and user object.
	 *
	 * @param userId User ID
	 * @param user   User Object
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 */
	@Override
	public ExecutionStatus updateUser(int userId, User user) {
		if (!isUserExists(userId)) {
			return userNotFoundLog(userId);
		}

		user.setId(userId);
		userRepository.save(user);
		logger.debug("User {} details has been updated.", userId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update a user's password with the given user id and password.
	 *
	 * @param userId   User ID that needs to be updated
	 * @param password New password of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 */
	@Override
	public ExecutionStatus updateUserPassword(int userId, String password) {
		if (!isUserExists(userId)) {
			return userNotFoundLog(userId);
		}

		Optional<User> userOptional = userRepository.findById(userId);

		// Get the value of the user
		User user = userOptional.orElse(null);

		// If null, return FAILURE.
		if (user == null) {
			return ExecutionStatus.FAILURE;
		}

		// Set the password and save it.
		// TODO: Hash the password
		user.setPassword(password);
		userRepository.save(user);
		logger.debug("User {} password has been updated.", userId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update the user's locked status with the given user id.
	 *
	 * @param userId   User ID that needs to be updated
	 * @param isLocked New locked status of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 * If the status of the isLocked is the same as the current status,
	 * the method will return SUCCESS but nothing will query.
	 */
	@Override
	public ExecutionStatus changeUserLockStatus(int userId, boolean isLocked) {
		// If the user id is negative, return failure.
		if (userId <= 0) {
			return userInvalidId(userId);
		}

		// If the user does not exist, return not found.
		if (!isUserExists(userId)) {
			return userNotFoundLog(userId);
		}

		// Update the user
		int status = userRepository.updateUserLockStatus(isLocked, userId);

		if (status <= 0) {
			logger.debug("Failed to update user {} lock status.", userId);
			return ExecutionStatus.FAILURE;
		}

		logger.debug("User {} lock status has been updated.", userId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update the user's enabled status with the given user id.
	 *
	 * @param userId    User ID that needs to be updated
	 * @param isEnabled New enabled status of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 * If the status of the isEnabled is the same as the current status,
	 * the method will return SUCCESS but nothing will query.
	 */
	@Override
	public ExecutionStatus changeUserEnabledStatus(int userId, boolean isEnabled) {
		// If the user id is negative, return failure.
		if (userId <= 0) {
			return userInvalidId(userId);
		}

		// If the user does not exist, return not found.
		if (!isUserExists(userId)) {
			return userNotFoundLog(userId);
		}

		// Update the user
		int status = userRepository.updateUserEnableStatus(isEnabled, userId);

		if (status <= 0) {
			logger.debug("Failed to update user {} enabled status.", userId);
			return ExecutionStatus.FAILURE;
		}

		logger.debug("User {} enabled status has been updated.", userId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update the user's expired status with the given user id.
	 *
	 * @param userId    User ID that needs to be updated
	 * @param isExpired New expired status of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 * If the status of the isExpired is the same as the current status,
	 * the method will return SUCCESS but nothing will query.
	 */
	@Override
	public ExecutionStatus changeUserExpiredStatus(int userId, boolean isExpired) {
		// If the user id is negative, return failure.
		if (userId <= 0) {
			return userInvalidId(userId);
		}

		// If the user does not exist, return not found.
		if (!isUserExists(userId)) {
			return userNotFoundLog(userId);
		}

		// Update the user
		int status = userRepository.updateIsExpiredById(isExpired, userId);

		if (status <= 0) {
			logger.debug("Failed to update user {} expired status.", userId);
			return ExecutionStatus.FAILURE;
		}

		logger.debug("User {} expired status has been updated.", userId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update the user's credentials expired status with the given user id.
	 *
	 * @param userId               User ID that needs to be updated
	 * @param isCredentialsExpired New credentials expired status of the user.
	 * @return ExecutionStatus (SUCCESS, FAILURE, NOT_FOUND)
	 * If the status of the isCredentialsExpired is the same as the current status,
	 * the method will return SUCCESS but nothing will query.
	 */
	@Override
	public ExecutionStatus changeUserCredentialsExpiredStatus(int userId, boolean isCredentialsExpired) {

		// If the user id is negative, return failure.
		if (userId <= 0) {
			return userInvalidId(userId);
		}

		// If the user does not exist, return not found.
		if (!isUserExists(userId)) {
			return userNotFoundLog(userId);
		}

		// Update the user
		int status = userRepository.updateIsCredentialsExpiredById(isCredentialsExpired, userId);

		if (status <= 0) {
			logger.debug("Failed to update user {} credentials is expired status", userId);
			return ExecutionStatus.FAILURE;
		}

		logger.debug("User {} credentials expired status", userId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Get a user with the given user id.
	 *
	 * @param userId User ID
	 * @return User Object
	 */
	@Override
	public User getUser(int userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		return userOptional.orElse(null);
	}

	/**
	 * Get a user with the given username.
	 *
	 * @param username The username of the user.
	 * @return User Object
	 */
	@Override
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	/**
	 * Get all the users in existence within the database.
	 *
	 * @param page Page
	 * @param size Shows how many users will it display.
	 * @return return the page object
	 */
	@Override
	public Page<User> getAllUsers(int page, int size) {
		return userRepository.findAll(PageRequest.of(page, size));
	}

	/**
	 * Search all users with the given username.
	 *
	 * @param username The username that will be searched.
	 * @param page     Page
	 * @param size     Shows how many users will it display.
	 * @return return the page object
	 */
	@Override
	public Page<User> searchUsersByUsername(String username, int page, int size) {
		return userRepository.searchUsersByUsername(username, PageRequest.of(page, size));
	}

	/**
	 * Search all users with the given email.
	 *
	 * @param email The email that will be searched.
	 * @param page  Page
	 * @param size  Shows how many users will it display.
	 * @return return the page object
	 */
	@Override
	public Page<User> searchUsersByEmail(String email, int page, int size) {
		return userRepository.searchUsersByEmail(email, PageRequest.of(page, size));
	}

	@Override
	public Page<User> searchUsersByRole(String role, int page, int size) {
		return userRepository.searchUsersByRole(role, PageRequest.of(page, size));
	}

	@Override
	public Page<User> searchUsersByUsernameAndEmail(String username, String email, int page, int size) {
		return userRepository.searchUsersByUsernameAndEmail(username, email, PageRequest.of(page, size));
	}

	/**
	 * Search all users with the given role and username.
	 *
	 * @param username The username that will be searched.
	 * @param role     The user with the given role.
	 * @param page     Page
	 * @param size     Shows how many users will it display.
	 * @return return the page object
	 */
	@Override
	public Page<User> searchUsersByUsernameAndRole(String username, String role, int page, int size) {
		return userRepository.searchUsersByUsernameAndRole(username, role, PageRequest.of(page, size));
	}

	/**
	 * Search all users with the given role and email.
	 *
	 * @param email The email that will be searched.
	 * @param role  The user with the given role.
	 * @param page  Page
	 * @param size  Shows how many users will it display.
	 * @return return the page object
	 */
	@Override
	public Page<User> searchUsersByEmailAndRole(String email, String role, int page, int size) {
		return userRepository.searchUsersByEmailAndRole(email, role, PageRequest.of(page, size));
	}

	@Override
	public Page<User> searchUsersByUsernameAndEmailAndRole(String username, String email, String role, int page, int size) {
		return userRepository.searchUsersByUsernameAndEmailAndRole(username, email, role, PageRequest.of(page, size));
	}

	/**
	 * Count all the users in existence within the database.
	 *
	 * @return The number of users in the database.
	 */
	@Override
	public int countAllUsers() {
		return userRepository.findAll().size();
	}

	/**
	 * Count all the users with the given role in existence within the database.
	 *
	 * @param role The role of the user.
	 * @return The number of users with the given role in the database.
	 */
	@Override
	public int countAllUsersByRole(String role) {
		return userRepository.countByRole(role);
	}

	/**
	 * Checks if the user id exists in the database.
	 *
	 * @param userId The user id that will be checked
	 * @return true if the user exists, otherwise false
	 */
	private boolean isUserExists(int userId) {
		return userRepository.existsById(userId);
	}

	/**
	 * THis will validate the user object. The user object should not be null
	 * and the username and email should not be null.
	 *
	 * @param user User object that will be validated
	 * @return true if the user is valid, otherwise false
	 */
	private boolean validateUser(User user) {
		return user != null && user.getUsername() != null && user.getEmail() != null;
	}

	/**
	 * This will log that the user is not found.
	 *
	 * @param userId The user id that is not found
	 * @return ExecutionStatus.NOT_FOUND
	 */
	private ExecutionStatus userNotFoundLog(int userId) {
		logger.warn("User {} not found.", userId);
		return ExecutionStatus.NOT_FOUND;
	}

	/**
	 * This will log that the user id is invalid.
	 *
	 * @param userId The user id that is invalid
	 * @return ExecutionStatus.FAILURE
	 */
	private ExecutionStatus userInvalidId(int userId) {
		logger.warn("Invalid user id {}.", userId);
		return ExecutionStatus.FAILURE;
	}
}