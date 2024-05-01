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

public class UserCreationDTO extends UserDTO implements Serializable {

	private boolean isLocked;
	private boolean isEnabled;
	private boolean isExpired;
	private boolean isCredentialsExpired;

	public UserCreationDTO(boolean isLocked, boolean isEnabled, boolean isExpired, boolean isCredentialsExpired) {
		this.isLocked = isLocked;
		this.isEnabled = isEnabled;
		this.isExpired = isExpired;
		this.isCredentialsExpired = isCredentialsExpired;
	}

	public UserCreationDTO(UserDTO user, boolean isLocked, boolean isEnabled, boolean isExpired, boolean isCredentialsExpired) {
		super(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRole(), user.getLastLogin(), user.getCreatedAt());
		this.isLocked = isLocked;
		this.isEnabled = isEnabled;
		this.isExpired = isExpired;
		this.isCredentialsExpired = isCredentialsExpired;
	}

	public User toEntity() {
		return new User()
				.setId(getId())
				.setUsername(getUsername())
				.setPassword(getPassword())
				.setEmail(getEmail())
				.setRole(getRole())
				.setLastLogin(getLastLogin())
				.setCreatedAt(getCreatedAt())
				.setLocked(isLocked)
				.setEnabled(isEnabled)
				.setExpired(isExpired)
				.setCredentialsExpired(isCredentialsExpired);
	}

	public boolean isLocked() {
		return isLocked;
	}

	public UserCreationDTO setLocked(boolean locked) {
		isLocked = locked;
		return this;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public UserCreationDTO setEnabled(boolean enabled) {
		isEnabled = enabled;
		return this;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public UserCreationDTO setExpired(boolean expired) {
		isExpired = expired;
		return this;
	}

	public boolean isCredentialsExpired() {
		return isCredentialsExpired;
	}

	public UserCreationDTO setCredentialsExpired(boolean credentialsExpired) {
		isCredentialsExpired = credentialsExpired;
		return this;
	}
}