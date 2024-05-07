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

import com.pshs.attendance_system.entities.RFIDCredential;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.RFIDCredential}
 */
public class RFIDCredentialDTO implements Serializable {
	private Integer id;
	private StudentDTO lrn;
	private String hashedLrn;
	private String salt;

	public RFIDCredentialDTO() {
	}

	public RFIDCredentialDTO(Integer id, StudentDTO lrn, String hashedLrn, String salt) {
		this.id = id;
		this.lrn = lrn;
		this.hashedLrn = hashedLrn;
		this.salt = salt;
	}

	public RFIDCredential toEntity() {
		return new RFIDCredential()
			.setId(id)
			.setLrn(lrn.toEntity())
			.setHashedLrn(hashedLrn)
			.setSalt(salt);
	}

	public Integer getId() {
		return id;
	}

	public RFIDCredentialDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public StudentDTO getLrn() {
		return lrn;
	}

	public RFIDCredentialDTO setLrn(StudentDTO lrn) {
		this.lrn = lrn;
		return this;
	}

	public String getHashedLrn() {
		return hashedLrn;
	}

	public RFIDCredentialDTO setHashedLrn(String hashedLrn) {
		this.hashedLrn = hashedLrn;
		return this;
	}

	public String getSalt() {
		return salt;
	}

	public RFIDCredentialDTO setSalt(String salt) {
		this.salt = salt;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RFIDCredentialDTO entity = (RFIDCredentialDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.lrn, entity.lrn) &&
			Objects.equals(this.hashedLrn, entity.hashedLrn) &&
			Objects.equals(this.salt, entity.salt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, lrn, hashedLrn, salt);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"lrn = " + lrn + ", " +
			"hashedLrn = " + hashedLrn + ", " +
			"salt = " + salt + ")";
	}
}