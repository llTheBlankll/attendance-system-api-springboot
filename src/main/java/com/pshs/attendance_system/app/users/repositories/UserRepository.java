

package com.pshs.attendance_system.app.users.repositories;

import com.pshs.attendance_system.app.users.models.dto.UserSearchInput;
import com.pshs.attendance_system.app.users.models.entities.User;
import com.pshs.attendance_system.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Transactional
	@Modifying
	@Query("update User u set u.isLocked = :isLocked where u.id = :id")
	int updateUserLockStatus(@NonNull @Param("isLocked") Boolean isLocked, @Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("update User u set u.isEnabled = :isEnabled where u.id = :id")
	int updateUserEnableStatus(@NonNull @Param("isEnabled") Boolean isEnabled, @Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("update User u set u.isExpired = :isExpired where u.id = :id")
	int updateIsExpiredById(@NonNull @Param("isExpired") Boolean isExpired, @Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("update User u set u.isCredentialsExpired = :isCredentialsExpired where u.id = :id")
	int updateIsCredentialsExpiredById(@NonNull @Param("isCredentialsExpired") Boolean isCredentialsExpired, @NonNull @Param("id") Integer id);

	int countByRole(@NonNull String role);

	@Query("select u from User u where u.username = :username")
	Optional<User> findByUsername(@Param("username") @NonNull String username);

	@Transactional
	@Modifying
	@Query("update User u set u.lastLogin = :lastLogin where u.id = :id")
	void updateUserLastLoginTime(@NonNull @Param("lastLogin") Instant lastLogin, @NonNull @Param("id") Integer id);

	@Query("select (count(u) > 0) from User u where u.username = :username")
	boolean existsByUsername(@Param("username") @NonNull String username);

	@Query("""
		    SELECT u FROM User u
		    WHERE (:#{#input.email} IS NULL OR u.email LIKE CONCAT('%', :#{#input.email}, '%'))
		    AND (:#{#input.username} IS NULL OR u.username LIKE CONCAT('%', :#{#input.username}, '%'))
		    AND (:#{#input.role} IS NULL OR u.role = :#{#input.role})
		""")
	Page<User> search(@Param("input") UserSearchInput input, Pageable pageable);
}