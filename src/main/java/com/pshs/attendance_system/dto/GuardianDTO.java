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

import com.pshs.attendance_system.entities.Guardian;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Guardian}
 */
public class GuardianDTO implements Serializable {
	private Integer id;
	private StudentDTO studentLrn;
	private String fullName;
	private String contactNumber;

	public GuardianDTO() {
	}

	public GuardianDTO(Integer id, StudentDTO studentLrn, String fullName, String contactNumber) {
		this.id = id;
		this.studentLrn = studentLrn;
		this.fullName = fullName;
		this.contactNumber = contactNumber;
	}

	public Guardian toEntity() {
		return new Guardian()
			.setId(id)
			.setStudentLrn(studentLrn.toEntity())
			.setFullName(fullName)
			.setContactNumber(contactNumber);
	}

	public Integer getId() {
		return id;
	}

	public GuardianDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public StudentDTO getStudentLrn() {
		return studentLrn;
	}

	public GuardianDTO setStudentLrn(StudentDTO studentLrn) {
		this.studentLrn = studentLrn;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public GuardianDTO setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public GuardianDTO setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GuardianDTO entity = (GuardianDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.studentLrn, entity.studentLrn) &&
			Objects.equals(this.fullName, entity.fullName) &&
			Objects.equals(this.contactNumber, entity.contactNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, studentLrn, fullName, contactNumber);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"studentLrn = " + studentLrn + ", " +
			"fullName = " + fullName + ", " +
			"contactNumber = " + contactNumber + ")";
	}
}