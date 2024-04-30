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
import java.time.LocalTime;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Attendance}
 */
public class AttendanceDTO implements Serializable {
	private Integer id;
	private StudentDTO student;
	private String status;
	private LocalDate date;
	private LocalTime time;
	private LocalTime timeOut;

	public AttendanceDTO() {
	}

	public AttendanceDTO(Integer id, StudentDTO student, String status, LocalDate date, LocalTime time, LocalTime timeOut) {
		this.id = id;
		this.student = student;
		this.status = status;
		this.date = date;
		this.time = time;
		this.timeOut = timeOut;
	}

	public Integer getId() {
		return id;
	}

	public AttendanceDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public AttendanceDTO setStudent(StudentDTO student) {
		this.student = student;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public AttendanceDTO setStatus(String status) {
		this.status = status;
		return this;
	}

	public LocalDate getDate() {
		return date;
	}

	public AttendanceDTO setDate(LocalDate date) {
		this.date = date;
		return this;
	}

	public LocalTime getTime() {
		return time;
	}

	public AttendanceDTO setTime(LocalTime time) {
		this.time = time;
		return this;
	}

	public LocalTime getTimeOut() {
		return timeOut;
	}

	public AttendanceDTO setTimeOut(LocalTime timeOut) {
		this.timeOut = timeOut;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AttendanceDTO entity = (AttendanceDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.student, entity.student) &&
			Objects.equals(this.status, entity.status) &&
			Objects.equals(this.date, entity.date) &&
			Objects.equals(this.time, entity.time) &&
			Objects.equals(this.timeOut, entity.timeOut);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, student, status, date, time, timeOut);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"student = " + student + ", " +
			"status = " + status + ", " +
			"date = " + date + ", " +
			"time = " + time + ", " +
			"timeOut = " + timeOut + ")";
	}
}