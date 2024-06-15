

package com.pshs.attendance_system.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pshs.attendance_system.dto.AttendanceResultDTO;
import com.pshs.attendance_system.entities.Attendance;
import com.pshs.attendance_system.entities.RFIDCredential;
import com.pshs.attendance_system.entities.range.DateRange;
import com.pshs.attendance_system.enums.Status;
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
	 * @param attendance Attendance object that will be created.
	 * @return Returns the attendance object created that symbolize that the operation is successful.
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

	/**
	 * get the attendance record of a student by the student id and the date.
	 *
	 * @param studentId Student ID
	 * @param date      Date of the attendance
	 * @return Attendance of a student {@link Attendance}
	 */
	Attendance getAttendanceByStudentDate(Long studentId, LocalDate date);

	/**
	 * Get all the attendance of every student in existence within the database.
	 *
	 * @param page Page
	 * @return return the page object
	 */
	Page<Attendance> getAllAttendances(Pageable page);

	/**
	 * Get all the attendance of every student in existence within the database.
	 *
	 * @return return the list of attendance
	 */
	List<Attendance> getAllAttendancesByDate(LocalDate date);

	/**
	 * Get all the attendance of every student in existence within the database.
	 *
	 * @param date Date of the attendance
	 * @param sectionId Section ID
	 * @return return the list of attendance
	 */
	List<Attendance> getAllAttendancesByDateAndSection(LocalDate date, Integer sectionId);

	/**
	 * @param studentId Student ID
	 * @param page      Page
	 * @return return the page object
	 */
	Page<Attendance> getAllAttendancesByStudentId(Long studentId, Pageable page);

	/**
	 * Get all attendances by date
	 *
	 * @param date Date of the attendance
	 * @param page paging object
	 * @return return the page object
	 */
	Page<Attendance> getAllAttendancesByDate(LocalDate date, Pageable page);

	// End Region: Custom Queries

	// Region: Statistics / Numbers

	/**
	 * Count the total number of students with the attendance status between two date.
	 *
	 * @param status    Attendance Status (LATE, ON_TIME, ...)
	 * @param dateRange from 2024-10-1 to 2024-12-1
	 * @return the number of attendance of all student.
	 */
	int countStudentsByStatusAndDateRange(Status status, DateRange dateRange);

	/**
	 * Count the total number of students with the attendance status on a specific date.
	 *
	 * @param status Attendance Status (LATE, ON_TIME, ...)
	 * @param date   Specific Date
	 * @return the number of students who have attendance with the specific status with the specific date
	 */
	int countStudentsByStatusAndDate(Status status, LocalDate date);

	/**
	 * Count the total number of students with the attendance status on a specific date.
	 *
	 * @param status Attendance Status (LATE, ON_TIME, ...)
	 * @param date   Specific Date
	 * @return the number of attendance of all the student with the specific status with the specific date
	 */
	int countStudentAttendanceByStatusAndDate(Long studentId, Status status, LocalDate date);

	/**
	 * Count the total number of students with the attendance status between two date.
	 *
	 * @param status    Attendance Status (LATE, ON_TIME, ...)
	 * @param dateRange from 2024-10-1 to 2024-12-1
	 * @return the number of attendance of all student with the specific status between the date range
	 */
	int countStudentAttendanceByStatusAndDate(Long studentId, Status status, DateRange dateRange);

	/**
	 * Returns the total number of attendances of a student with the specific status.
	 *
	 * @param date   Date
	 * @param status Status
	 * @return the number of attendances of a student with the specific status
	 */
	int getAttendanceCountByDateAndStatus(LocalDate date, Status status);

	/**
	 * Count attendance in section by status and date
	 *
	 * @param sectionId The section id
	 * @param date The date
	 * @param status The status {@link Status}
	 * @return The number of attendances in the section with the specific status and date
	 */
	int countAttendanceInSection(Integer sectionId, LocalDate date, Status status);

	/**
	 * Count attendance in section by status and date range
	 *
	 * @param sectionId The section id
	 * @param dateRange The date range {@link DateRange}
	 * @param status The status {@link Status}
	 * @return The number of attendances in the section with the specific status and date range
	 */
	int countAttendanceInSection(Integer sectionId, DateRange dateRange, Status status);

	int averageAttendanceInSection(Integer sectionId, LocalDate date, Status status);

	int averageAttendanceInSection(Integer sectionId, DateRange dateRange, Status status);


	// End: Statistics / Numbers

	// Region: Custom Queries
	boolean isCheckedIn(Long lrn);

	boolean isScanned(Long lrn);

	boolean isAttendanceExist(Long studentId, LocalDate date);

	boolean isAttendanceExist(int attendanceId, LocalDate date);

	boolean isAttendanceExist(int attendanceId);
}