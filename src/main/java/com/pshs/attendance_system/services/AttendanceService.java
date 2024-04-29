package com.pshs.attendance_system.services;

import com.pshs.attendance_system.entities.Attendance;
import com.pshs.attendance_system.entities.range.DateRange;
import com.pshs.attendance_system.enums.AttendanceStatus;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;

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
	ExecutionStatus createAttendance(Attendance attendance);

	/**
	 * Update the attendance record with the student. Requires the attendance id, and a new attendance object with the updated values.
	 *
	 * @param id - Attendance ID
	 * @param attendance - Updated Attendance Object
	 * @return ExecutionStatus
	 */
	ExecutionStatus updateAttendance(Long id, Attendance attendance);

	/**
	 * Delete the attendance record of a student, requires the attendance id.
	 *
	 * @param id - Attendance ID
	 * @return ExecutionStatus
	 */
	ExecutionStatus deleteAttendance(Long id);
	// End Region: CRUD Methods

	// Region: Custom Queries

	/**
	 * Get the attendance record of a student by the attendance id.
	 *
	 * @param id - Attendance ID
	 * @return Attendance of a student
	 */
	Attendance getAttendanceById(Long id);

	/**
	 * Get the attendance record of a student by the student id.
	 *
	 * @param studentId - Student ID
	 * @return The list of attendance of a student
	 */
	List<Attendance> getAttendancesByStudentId(Long studentId);

	/**
	 * Get all the attendance of every student in existence within the database.
	 *
	 * @param page - Page
	 * @param size - Shows how many student will it display.
	 * @return return the page object
	 */
	Page<Attendance> getAllAttendances(int page, int size);

	/**
	 *
	 *
	 * @param studentId - Student ID
	 * @param page - Page
	 * @param size - How many student it will display.
	 * @return return the page object
	 */
	Page<Attendance> getAllAttendancesByStudentId(Long studentId, int page, int size);
	// End Region: Custom Queries

	// Region: Statistics / Numbers

	/**
	 * Count the total number of students with the attendance status between two date.
	 *
	 * @param attendanceStatus - Attendance Status (LATE, ON_TIME, ...)
	 * @param dateRange - from 2024-10-1 to 2024-12-1
	 * @return the number of attendance of all student.
	 */
	int countStudentsByStatusAndDateRange(AttendanceStatus attendanceStatus, DateRange dateRange);

	/**
	 * Count the total number of students with the attendance status on a specific date.
	 *
	 * @param attendanceStatus Attendance Status (LATE, ON_TIME, ...)
	 * @param date Specific Date
	 * @return the number of attendance of all the student with the specific status with the specific date
	 */
	int countStudentsByStatusAndDate(AttendanceStatus attendanceStatus, LocalDate date);

	/**
	 * Count the total number of students with the attendance status on a specific date.
	 *
	 * @param attendanceStatus Attendance Status (LATE, ON_TIME, ...)
	 * @param date Specific Date
	 * @return the number of attendance of all the student with the specific status with the specific date
	 */
	int countStudentAttendancesByStatus(int studentId, AttendanceStatus attendanceStatus, LocalDate date);

	/**
	 * Count the total number of students with the attendance status between two date.
	 *
	 * @param attendanceStatus - Attendance Status (LATE, ON_TIME, ...)
	 * @param dateRange - from 2024-10-1 to 2024-12-1
	 * @return the number of attendance of all student with the specific status between the date range
	 */
	int countStudentAttendancesByStatus(int studentId, AttendanceStatus attendanceStatus, DateRange dateRange);

	/**
	 * Count the total number of students who checked in at a specific time and have a specific attendance status on a specific date.
	 *
	 * @param timeIn - The specific time the students checked in
	 * @param date - The specific date
	 * @param attendanceStatus - The attendance status (LATE, ON_TIME, ...)
	 * @return the number of students
	 */
	int countStudentsAttendancesTimeInByDateAndStatus(LocalTime timeIn, LocalDate date, AttendanceStatus attendanceStatus);

	/**
	 * Count the total number of students who checked in at a specific time on a specific date.
	 *
	 * @param timeIn - The specific time the students checked in
	 * @param date - The specific date
	 * @return the number of students
	 */
	int countStudentsAttendancesTimeInByDate(LocalTime timeIn, LocalDate date);

	/**
	 * Count the total number of students who checked out at a specific time and have a specific attendance status on a specific date.
	 *
	 * @param timeOut - The specific time the students checked out
	 * @param date - The specific date
	 * @param attendanceStatus - The attendance status (LATE, ON_TIME, ...)
	 * @return the number of students
	 */
	int countStudentsAttendancesTimeOutByDateAndStatus(LocalTime timeOut, LocalDate date, AttendanceStatus attendanceStatus);

	/**
	 * Count the total number of students who checked out at a specific time on a specific date.
	 *
	 * @param timeOut - The specific time the students checked out
	 * @param date - The specific date
	 * @return the number of students
	 */
	int countStudentsAttendancesTimeOutByDate(LocalTime timeOut, LocalDate date);
	// End: Statistics / Numbers
}