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

package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.User}
 */
public class UserDTO implements Serializable {
	private Integer id;
	private String username;
	private String password;
	private String email;
	private String role;
	private Instant lastLogin;
	private Instant createdAt;

	public UserDTO() {
	}

	public UserDTO(Integer id, String username, String password, String email, String role, Instant lastLogin, Instant createdAt) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.lastLogin = lastLogin;
		this.createdAt = createdAt;
	}

	public User toEntity() {
		return new User()
			.setId(id)
			.setUsername(username)
			.setPassword(password)
			.setEmail(email)
			.setRole(role)
			.setLastLogin(lastLogin)
			.setCreatedAt(createdAt);

	}

	public Integer getId() {
		return id;
	}

	public UserDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public UserDTO setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserDTO setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserDTO setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getRole() {
		return role;
	}

	public UserDTO setRole(String role) {
		this.role = role;
		return this;
	}

	public Instant getLastLogin() {
		return lastLogin;
	}

	public UserDTO setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
		return this;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public UserDTO setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDTO entity = (UserDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.username, entity.username) &&
			Objects.equals(this.password, entity.password) &&
			Objects.equals(this.email, entity.email) &&
			Objects.equals(this.role, entity.role) &&
			Objects.equals(this.lastLogin, entity.lastLogin) &&
			Objects.equals(this.createdAt, entity.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, email, role, lastLogin, createdAt);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"username = " + username + ", " +
			"password = " + password + ", " +
			"email = " + email + ", " +
			"role = " + role + ", " +
			"lastLogin = " + lastLogin + ", " +
			"createdAt = " + createdAt + ")";
	}
}