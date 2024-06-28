

package com.pshs.attendance_system.repositories;

import com.pshs.attendance_system.entities.User;
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

	@Query("select u from User u where u.username = :username")
	Page<User> searchUsersByUsername(@Param("username") @NonNull String username, Pageable pageable);

	@Query("select u from User u where u.email = :email")
	Page<User> searchUsersByEmail(@Param("email") @NonNull String email, Pageable pageable);

	@Query("""
		select u from User u
		where u.username like concat('%', :username, '%') and u.role like concat('%', :role, '%')""")
	Page<User> searchUsersByUsernameAndRole(@Param("username") @NonNull String username, @Param("role") @NonNull String role, Pageable pageable);

	@Query("select u from User u where u.email like concat('%', :email, '%') and u.role like concat('%', :role, '%')")
	Page<User> searchUsersByEmailAndRole(@Param("email") @NonNull String email, @Param("role") @NonNull String role, Pageable pageable);

	int countByRole(@NonNull String role);

	@Query("""
		select u from User u
		where u.username like concat('%', :username, '%') and u.email like concat('%', :email, '%') and upper(u.role) like upper(concat('%', :role, '%'))""")
	Page<User> searchUsersByUsernameAndEmailAndRole(@Param("username") @NonNull String username, @Param("email") @NonNull String email, @Param("role") @NonNull String role, Pageable pageable);

	@Query("select u from User u where upper(u.role) like upper(concat('%', :role, '%'))")
	Page<User> searchUsersByRole(@Param("role") @NonNull String role, Pageable pageable);

	@Query("""
		select u from User u
		where u.username like concat('%', :username, '%') and u.email like concat('%', :email, '%')""")
	Page<User> searchUsersByUsernameAndEmail(@Param("username") @NonNull String username, @Param("email") @NonNull String email, Pageable pageable);

	@Query("select u from User u where u.username = :username")
	Optional<User> findByUsername(@Param("username") @NonNull String username);

	@Transactional
	@Modifying
	@Query("update User u set u.lastLogin = :lastLogin where u.id = :id")
	void updateUserLastLoginTime(@NonNull @Param("lastLogin") Instant lastLogin, @NonNull @Param("id") Integer id);

	@Query("select (count(u) > 0) from User u where u.username = :username")
	boolean existsByUsername(@Param("username") @NonNull String username);
}