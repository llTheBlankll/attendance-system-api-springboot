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

package com.pshs.attendance_system.entities;

import com.pshs.attendance_system.dto.RFIDCredentialDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "rfid_credentials")
public class RFIDCredential {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rfid_credentials_id_gen")
	@SequenceGenerator(name = "rfid_credentials_id_gen", sequenceName = "rfid_credentials_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "lrn", nullable = false)
	private Student lrn;

	@Column(name = "hashed_lrn", nullable = false, length = 32)
	private String hashedLrn;

	@Column(name = "salt", nullable = false, length = 16)
	private String salt;

	public RFIDCredential() {
	}

	public RFIDCredential(Integer id, Student lrn, String hashedLrn, String salt) {
		this.id = id;
		this.lrn = lrn;
		this.hashedLrn = hashedLrn;
		this.salt = salt;
	}

	public RFIDCredentialDTO toDTO() {
		return new RFIDCredentialDTO(id, lrn.toDTO(), hashedLrn, salt);
	}

	public Integer getId() {
		return id;
	}

	public RFIDCredential setId(Integer id) {
		this.id = id;
		return this;
	}

	public Student getLrn() {
		return lrn;
	}

	public RFIDCredential setLrn(Student lrn) {
		this.lrn = lrn;
		return this;
	}

	public String getHashedLrn() {
		return hashedLrn;
	}

	public RFIDCredential setHashedLrn(String hashedLrn) {
		this.hashedLrn = hashedLrn;
		return this;
	}

	public String getSalt() {
		return salt;
	}

	public RFIDCredential setSalt(String salt) {
		this.salt = salt;
		return this;
	}
}