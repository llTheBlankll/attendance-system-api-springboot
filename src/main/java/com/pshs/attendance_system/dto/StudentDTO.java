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

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Student}
 */
public class StudentDTO implements Serializable {
	private Long id;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private GradeLevelDTO gradeLevel;
	private String sex;
	private String address;
	private LocalDate birthdate;

	public StudentDTO() {
	}

	public StudentDTO(Long id, String firstName, String middleInitial, String lastName, GradeLevelDTO gradeLevel, String sex, String address, LocalDate birthdate) {
		this.id = id;
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.gradeLevel = gradeLevel;
		this.sex = sex;
		this.address = address;
		this.birthdate = birthdate;
	}

	public Long getId() {
		return id;
	}

	public StudentDTO setId(Long id) {
		this.id = id;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public StudentDTO setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public StudentDTO setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public StudentDTO setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public GradeLevelDTO getGradeLevel() {
		return gradeLevel;
	}

	public StudentDTO setGradeLevel(GradeLevelDTO gradeLevel) {
		this.gradeLevel = gradeLevel;
		return this;
	}

	public String getSex() {
		return sex;
	}

	public StudentDTO setSex(String sex) {
		this.sex = sex;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public StudentDTO setAddress(String address) {
		this.address = address;
		return this;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public StudentDTO setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StudentDTO entity = (StudentDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.firstName, entity.firstName) &&
			Objects.equals(this.middleInitial, entity.middleInitial) &&
			Objects.equals(this.lastName, entity.lastName) &&
			Objects.equals(this.gradeLevel, entity.gradeLevel) &&
			Objects.equals(this.sex, entity.sex) &&
			Objects.equals(this.address, entity.address) &&
			Objects.equals(this.birthdate, entity.birthdate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, middleInitial, lastName, gradeLevel, sex, address, birthdate);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"firstName = " + firstName + ", " +
			"middleInitial = " + middleInitial + ", " +
			"lastName = " + lastName + ", " +
			"gradeLevel = " + gradeLevel + ", " +
			"sex = " + sex + ", " +
			"address = " + address + ", " +
			"birthdate = " + birthdate + ")";
	}
}