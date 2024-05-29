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

package com.pshs.attendance_system.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pshs.attendance_system.dto.AttendanceResultDTO;
import com.pshs.attendance_system.entities.Attendance;
import com.pshs.attendance_system.entities.RFIDCredential;
import com.pshs.attendance_system.entities.range.DateRange;
import com.pshs.attendance_system.enums.AttendanceStatus;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AttendanceService {

	// Region: CRUD Methods

	/**
	 * Create a new attendance record with the student.
	 *
	 * @param attendance
	 * @return
	 */
	Attendance createAttendance(Attendance attendance);

	Attendance createAttendance(Long studentId);

	/**
	 * Update the attendance record with the student. Requires the attendance id, and a new attendance object with the updated values.
	 *
	 * @param id         Attendance ID
	 * @param attendance Updated Attendance Object
	 * @return ExecutionStatus
	 */
	ExecutionStatus updateAttendance(int id, Attendance attendance);

	/**
	 * Update the attendance record with the student. Requires the attendance id, and a new attendance object with the updated values.
	 *
	 * @param id      Attendance ID
	 * @param timeOut Updated Time Out
	 * @return ExecutionStatus {@link ExecutionStatus}
	 * @see ExecutionStatus
	 */
	ExecutionStatus updateAttendanceTimeOut(int id, LocalTime timeOut);

	/**
	 * Delete the attendance record of a student, requires the attendance id.
	 *
	 * @param id Attendance ID
	 * @return ExecutionStatus
	 */
	ExecutionStatus deleteAttendance(int id);

	/**
	 * Check IN the student with their RFID credential (MIFARE Card containing the hash of their lrn with salt).
	 *
	 * @param rfidCredential RFID Credential
	 * @see RFIDCredential
	 */
	AttendanceResultDTO attendanceIn(RFIDCredential rfidCredential);

	/**
	 * Check OUT the student with their RFID credential (MIFARE Card containing the hash of their lrn with salt).
	 *
	 * @param rfidCredential RFID Credential
	 * @see RFIDCredential
	 */
	AttendanceResultDTO attendanceOut(RFIDCredential rfidCredential) throws JsonProcessingException;
	// End Region: CRUD Methods

	// Region: Custom Queries

	/**
	 * Get the attendance record of a student by the attendance id.
	 *
	 * @param id Attendance ID
	 * @return Attendance of a student
	 */
	Attendance getAttendanceById(int id);

	Attendance getAttendanceByStudentDate(Long studentId, LocalDate date);

	/**
	 * Get the attendance record of a student by the student id.
	 *
	 * @param studentId Student ID
	 * @return The list of attendance of a student
	 */
	List<Attendance> getAttendancesByStudentId(Long studentId);

	/**
	 * Get all the attendance of every student in existence within the database.
	 *
	 * @param page Page
	 * @param size Shows how many student will it display.
	 * @return return the page object
	 */
	Page<Attendance> getAllAttendances(int page, int size);

	/**
	 * @param studentId Student ID
	 * @param page      Page
	 * @param size      How many student it will display.
	 * @return return the page object
	 */
	Page<Attendance> getAllAttendancesByStudentId(Long studentId, Pageable page);
	// End Region: Custom Queries

	// Region: Statistics / Numbers

	/**
	 * Count the total number of students with the attendance status between two date.
	 *
	 * @param attendanceStatus Attendance Status (LATE, ON_TIME, ...)
	 * @param dateRange        from 2024-10-1 to 2024-12-1
	 * @return the number of attendance of all student.
	 */
	int countStudentsByStatusAndDateRange(AttendanceStatus attendanceStatus, DateRange dateRange);

	/**
	 * Count the total number of students with the attendance status on a specific date.
	 *
	 * @param attendanceStatus Attendance Status (LATE, ON_TIME, ...)
	 * @param date             Specific Date
	 * @return the number of attendance of all the student with the specific status with the specific date
	 */
	int countStudentsByStatusAndDate(AttendanceStatus attendanceStatus, LocalDate date);

	/**
	 * Count the total number of students with the attendance status on a specific date.
	 *
	 * @param attendanceStatus Attendance Status (LATE, ON_TIME, ...)
	 * @param date             Specific Date
	 * @return the number of attendance of all the student with the specific status with the specific date
	 */
	int countStudentAttendancesByStatusBetweenDate(Long studentId, AttendanceStatus attendanceStatus, LocalDate date);

	/**
	 * Count the total number of students with the attendance status between two date.
	 *
	 * @param attendanceStatus Attendance Status (LATE, ON_TIME, ...)
	 * @param dateRange        from 2024-10-1 to 2024-12-1
	 * @return the number of attendance of all student with the specific status between the date range
	 */
	int countStudentAttendancesByStatusBetweenDate(Long studentId, AttendanceStatus attendanceStatus, DateRange dateRange);

	/**
	 * Count the total number of students who checked in at a specific time and have a specific attendance status on a specific date.
	 *
	 * @param timeIn           The specific time the students checked in
	 * @param date             The specific date
	 * @param attendanceStatus The attendance status (LATE, ON_TIME, ...)
	 * @return the number of attendances of all the students with time in, date, and status.
	 */
	int countStudentsAttendancesTimeInByDateAndStatus(LocalTime timeIn, LocalDate date, AttendanceStatus attendanceStatus);

	/**
	 * Count the total number of students who checked in at a specific time on a specific date.
	 *
	 * @param timeIn The specific time the students checked in
	 * @param date   The specific date
	 * @return the number of attendances of all the students with time in and date only.
	 */
	int countStudentsAttendancesTimeInByDate(LocalTime timeIn, LocalDate date);

	/**
	 * Count the total number of students who checked out at a specific time and have a specific attendance status on a specific date.
	 *
	 * @param timeOut          The specific time the students checked out
	 * @param date             The specific date
	 * @param attendanceStatus The attendance status (LATE, ON_TIME, ...)
	 * @return the number of students
	 */
	int countStudentsAttendancesTimeOutByDateAndStatus(LocalTime timeOut, LocalDate date, AttendanceStatus attendanceStatus);

	/**
	 * Count the total number of students who checked out at a specific time on a specific date.
	 *
	 * @param timeOut The specific time the students checked out
	 * @param date    The specific date
	 * @return the number of students attendances where when they went out of the school and with the date.
	 */
	int countStudentsAttendancesTimeOutByDate(LocalTime timeOut, LocalDate date);
	// End: Statistics / Numbers

	// Region: Custom Queries
	boolean isCheckedIn(Long lrn);

	boolean isScanned(Long lrn);

	boolean isAttendanceExist(Long studentId, LocalDate date);

	boolean isAttendanceExist(int attendanceId, LocalDate date);

	boolean isAttendanceExist(int attendanceId);
}