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

import com.pshs.attendance_system.dto.GuardianDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "guardians")
public class Guardian {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guardians_id_gen")
	@SequenceGenerator(name = "guardians_id_gen", sequenceName = "guardians_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "student_lrn")
	private Student studentLrn;

	@Column(name = "full_name", nullable = false)
	private String fullName;

	@Column(name = "contact_number", length = 32)
	private String contactNumber;

	public Guardian() {}

	public Guardian(Integer id, Student studentLrn, String fullName, String contactNumber) {
		this.id = id;
		this.studentLrn = studentLrn;
		this.fullName = fullName;
		this.contactNumber = contactNumber;
	}

	public GuardianDTO toDTO() {
		return new GuardianDTO(id, studentLrn.toDTO(), fullName, contactNumber);
	}

	public Integer getId() {
		return id;
	}

	public Guardian setId(Integer id) {
		this.id = id;
		return this;
	}

	public Student getStudentLrn() {
		return studentLrn;
	}

	public Guardian setStudentLrn(Student studentLrn) {
		this.studentLrn = studentLrn;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public Guardian setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public Guardian setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
		return this;
	}
}