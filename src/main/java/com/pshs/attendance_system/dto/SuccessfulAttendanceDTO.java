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

import com.pshs.attendance_system.enums.Status;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class SuccessfulAttendanceDTO implements Serializable {

	private StudentDTO student;
	private LocalDate date;
	private LocalTime time;
	private LocalTime timeOut;
	private Status status;
	private String message;
	private String hashedLrn;

	public SuccessfulAttendanceDTO(StudentDTO student, LocalDate date, LocalTime time, LocalTime timeOut, Status status, String message, String hashedLrn) {
		this.student = student;
		this.date = date;
		this.time = time;
		this.timeOut = timeOut;
		this.status = status;
		this.message = message;
		this.hashedLrn = hashedLrn;
	}

	public SuccessfulAttendanceDTO() {}

	public StudentDTO getStudent() {
		return student;
	}

	public SuccessfulAttendanceDTO setStudent(StudentDTO student) {
		this.student = student;
		return this;
	}

	public LocalDate getDate() {
		return date;
	}

	public SuccessfulAttendanceDTO setDate(LocalDate date) {
		this.date = date;
		return this;
	}

	public LocalTime getTime() {
		return time;
	}

	public SuccessfulAttendanceDTO setTime(LocalTime time) {
		this.time = time;
		return this;
	}

	public LocalTime getTimeOut() {
		return timeOut;
	}

	public SuccessfulAttendanceDTO setTimeOut(LocalTime timeOut) {
		this.timeOut = timeOut;
		return this;
	}

	public Status getStatus() {
		return status;
	}

	public SuccessfulAttendanceDTO setStatus(Status status) {
		this.status = status;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public SuccessfulAttendanceDTO setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getHashedLrn() {
		return hashedLrn;
	}

	public SuccessfulAttendanceDTO setHashedLrn(String hashedLrn) {
		this.hashedLrn = hashedLrn;
		return this;
	}
}