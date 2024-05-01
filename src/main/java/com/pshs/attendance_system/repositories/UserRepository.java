/*
 * COPYRIGHT (C) 2024 VINCE ANGELO BATECAN
 *
 * PERMISSION IS HEREBY GRANTED, FREE OF CHARGE, TO STUDENTS, FACULTY, AND STAFF OF PUNTURIN SENIOR HIGH SCHOOL TO USE THIS SOFTWARE FOR EDUCATIONAL PURPOSES ONLY.
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL COPIES OR SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 *
 * MODIFICATIONS:
 *
 * ANY MODIFICATIONS OR DERIVATIVE WORKS OF THE SOFTWARE SHALL BE CONSIDERED PART OF THE SOFTWARE AND SHALL BE SUBJECT TO THE TERMS AND CONDITIONS OF THIS LICENSE.
 * ANY PERSON OR ENTITY MAKING MODIFICATIONS TO THE SOFTWARE SHALL ASSIGN AND TRANSFER ALL RIGHT, TITLE, AND INTEREST IN AND TO SUCH MODIFICATIONS TO VINCE ANGELO BATECAN.
 * VINCE ANGELO BATECAN SHALL OWN ALL INTELLECTUAL PROPERTY RIGHTS IN AND TO SUCH MODIFICATIONS.
 *
 * NO COMMERCIAL USE:
 *
 * THE SOFTWARE SHALL NOT BE SOLD, RENTED, LEASED, OR OTHERWISE COMMERCIALLY EXPLOITED. THE SOFTWARE IS INTENDED FOR PERSONAL, NON-COMMERCIAL USE ONLY WITHIN PUNTURIN SENIOR HIGH SCHOOL.
 *
 * NO WARRANTIES:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
}