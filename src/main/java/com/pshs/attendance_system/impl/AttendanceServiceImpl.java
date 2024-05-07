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

package com.pshs.attendance_system.impl;

import com.pshs.attendance_system.entities.Attendance;
import com.pshs.attendance_system.entities.Student;
import com.pshs.attendance_system.entities.range.DateRange;
import com.pshs.attendance_system.enums.AttendanceStatus;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.repositories.AttendanceRepository;
import com.pshs.attendance_system.services.AttendanceService;
import com.pshs.attendance_system.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	private static final Logger logger = LogManager.getLogger(AttendanceServiceImpl.class);
	private final StudentService studentService;
	private final AttendanceRepository attendanceRepository;

	public AttendanceServiceImpl(StudentService studentService, AttendanceRepository attendanceRepository) {
		this.studentService = studentService;
		this.attendanceRepository = attendanceRepository;
	}

	/**
	 * Create a new attendance record with the student.
	 *
	 * @param attendance Attendance Object that will be created in the database.
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus createAttendance(Attendance attendance) {
		// Checks the DATE if there is already an attendance record, if there is then it will return an error.
		// Search attendance by student and check the attendance object 'date' if it already exists.
		// If it exists, return an error.
		// If it does not exist, create the attendance record.
		if (isAttendanceNotValid(attendance) && attendanceRepository.existsById(attendance.getId())) {
			logger.debug("Attendance is invalid and cannot be accepted.");
			return attendanceValidationFailedLog(attendance);
		}

		Student student = attendance.getStudent();
		if (student == null) {
			logger.debug("Student is null and cannot be accepted.");
			return ExecutionStatus.VALIDATION_ERROR;
		}

		// Check if the student already has an attendance record for today.
		if (isStudentRecordExist(student)) {
			logger.debug("Student {} already has an attendance record for today.", student.getFirstName());
			return ExecutionStatus.FAILURE;
		}

		attendanceRepository.save(attendance);
		logger.debug("Attendance of {}, {} has been created.", attendance.getStudent().getFirstName(), attendance.getStudent().getLastName());
		return ExecutionStatus.SUCCESS;
	}

	@Override
	public ExecutionStatus createAttendance(Long studentId) {
		// TODO: implement method
		return null;
	}

	/**
	 * Update the attendance record with the student. Requires the attendance id, and a new attendance object with the updated values.
	 *
	 * @param id         Attendance ID
	 * @param attendance Updated Attendance Object
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus updateAttendance(Long id, Attendance attendance) {
		if (id <= 0) {
			return attendanceInvalidIdLog(id);
		}

		if (isAttendanceNotExist(id)) {
			return attendanceNotFoundLog(id);
		}

		if (isAttendanceNotValid(attendance)) {
			return attendanceValidationFailedLog(attendance);
		}

		attendanceRepository.save(attendance);
		logger.debug("Attendance of {}, {} has been updated.", attendance.getStudent().getFirstName(), attendance.getStudent().getLastName());
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Delete the attendance record of a student, requires the attendance id.
	 *
	 * @param id Attendance ID
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus deleteAttendance(Long id) {
		if (id <= 0) {
			return attendanceInvalidIdLog(id);
		}

		if (isAttendanceNotExist(id)) {
			return attendanceNotFoundLog(id);
		}

		attendanceRepository.deleteById(id.intValue());
		logger.debug("Attendance ID: {} has been deleted.", id);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Get the attendance record of a student by the attendance id.
	 *
	 * @param id Attendance ID
	 * @return Attendance of a student
	 */
	@Override
	public Attendance getAttendanceById(Long id) {
		if (id <= 0) {
			return null;
		}

		if (isAttendanceNotExist(id)) {
			return null;
		}

		return attendanceRepository.findById(id.intValue()).orElse(null);
	}

	/**
	 * Get the attendance record of a student by the student id.
	 *
	 * @param studentId Student ID
	 * @return The list of attendance of a student
	 */
	@Override
	public List<Attendance> getAttendancesByStudentId(Long studentId) {
		// Alphabetical order
		Sort sortBy = Sort.by("lastName").ascending();

		return attendanceRepository.getAttendancesByStudentId(studentId, sortBy);
	}

	/**
	 * Get all the attendance of every student in existence within the database.
	 *
	 * @param page Page
	 * @param size Shows how many student will it display.
	 * @return return the page object
	 */
	@Override
	public Page<Attendance> getAllAttendances(int page, int size) {
		return attendanceRepository.findAll(PageRequest.of(page, size));
	}

	/**
	 * @param studentId Student ID
	 * @param page      Page
	 * @param size      How many student it will display.
	 * @return return the page object
	 */
	@Override
	public Page<Attendance> getAllAttendancesByStudentId(Long studentId, int page, int size) {
		// Alphabetical order of students
		Sort sort = Sort.by("lastName").ascending();

		return attendanceRepository.findAllAttendancesByStudentId(studentId, PageRequest.of(page, size).withSort(sort));
	}

	/**
	 * Count the total number of students with the attendance status between two date.
	 *
	 * @param attendanceStatus Attendance Status (LATE, ON_TIME, ...)
	 * @param dateRange        from 2024-10-1 to 2024-12-1
	 * @return the number of attendance of all student.
	 */
	@Override
	public int countStudentsByStatusAndDateRange(AttendanceStatus attendanceStatus, DateRange dateRange) {
		return (int) attendanceRepository.countStudentsByStatusAndDateRange(attendanceStatus.name(), dateRange.getStartDate(), dateRange.getEndDate());
	}

	/**
	 * Count the total number of students with the attendance status on a specific date.
	 *
	 * @param attendanceStatus Attendance Status (LATE, ON_TIME, ...)
	 * @param date             Specific Date
	 * @return the number of attendance of all the student with the specific status with the specific date
	 */
	@Override
	public int countStudentsByStatusAndDate(AttendanceStatus attendanceStatus, LocalDate date) {
		return (int) attendanceRepository.countStudentsByStatusAndDate(attendanceStatus.name(), date);
	}

	/**
	 * Count the total number of students with the attendance status on a specific date.
	 *
	 * @param studentId        Student ID
	 * @param attendanceStatus Attendance Status (LATE, ON_TIME, ...)
	 * @param date             Specific Date
	 * @return the number of attendance of all the student with the specific status with the specific date
	 */
	@Override
	public int countStudentAttendancesByStatusBetweenDate(Long studentId, AttendanceStatus attendanceStatus, LocalDate date) {
		return (int) attendanceRepository.countStudentAttendancesByStatusAndDate(studentId, attendanceStatus.name(), date);
	}

	/**
	 * Count the total number of students with the attendance status between two date.
	 *
	 * @param studentId        Student ID
	 * @param attendanceStatus Attendance Status (LATE, ON_TIME, ...)
	 * @param dateRange        from 2024-10-1 to 2024-12-1
	 * @return the number of attendance of all student with the specific status between the date range
	 */
	@Override
	public int countStudentAttendancesByStatusBetweenDate(Long studentId, AttendanceStatus attendanceStatus, DateRange dateRange) {
		return (int) attendanceRepository.countStudentAttendancesByStatusBetweenDate(studentId, attendanceStatus.name(), dateRange.getStartDate(), dateRange.getEndDate());
	}

	/**
	 * Count the total number of students who checked in at a specific time and have a specific attendance status on a specific date.
	 *
	 * @param timeIn           The specific time the students checked in
	 * @param date             The specific date
	 * @param attendanceStatus The attendance status (LATE, ON_TIME, ...)
	 * @return the number of attendances of all the students with time in, date, and status.
	 */
	@Override
	public int countStudentsAttendancesTimeInByDateAndStatus(LocalTime timeIn, LocalDate date, AttendanceStatus attendanceStatus) {
		return (int) attendanceRepository.countStudentsAttendancesTimeInByDateAndStatus(timeIn, date, attendanceStatus.name());
	}

	/**
	 * Count the total number of students who checked in at a specific time on a specific date.
	 *
	 * @param timeIn The specific time the students checked in
	 * @param date   The specific date
	 * @return the number of attendances of all the students with time in and date only.
	 */
	@Override
	public int countStudentsAttendancesTimeInByDate(LocalTime timeIn, LocalDate date) {
		return (int) attendanceRepository.countByTimeAndDate(timeIn, date);
	}

	/**
	 * Count the total number of students who checked out at a specific time and have a specific attendance status on a specific date.
	 *
	 * @param timeOut          The specific time the students checked out
	 * @param date             The specific date
	 * @param attendanceStatus The attendance status (LATE, ON_TIME, ...)
	 * @return the number of students
	 */
	@Override
	public int countStudentsAttendancesTimeOutByDateAndStatus(LocalTime timeOut, LocalDate date, AttendanceStatus attendanceStatus) {
		return (int) attendanceRepository.countStudentsAttendancesTimeOutBydateAndStatus(timeOut, date, attendanceStatus.name());
	}

	/**
	 * Count the total number of students who checked out at a specific time on a specific date.
	 *
	 * @param timeOut The specific time the students checked out
	 * @param date    The specific date
	 * @return the number of students attendances where when they went out of the school and with the date.
	 */
	@Override
	public int countStudentsAttendancesTimeOutByDate(LocalTime timeOut, LocalDate date) {
		return (int) attendanceRepository.count();
	}

	@Override
	public boolean isCheckedIn(Long lrn) {
		return false;
	}

	@Override
	public boolean isOut(Long lrn) {
		// Get the date now.
		LocalDate today = LocalDate.now();
		Student student = studentService.
	}

	@Override
	public boolean isAttendanceExist(int attendanceId) {
		return false;
	}

	private boolean isAttendanceNotExist(Long id) {
		return !attendanceRepository.existsById(id.intValue());
	}

	/**
	 * So this function will check if the student has already an attendance record for today.
	 * If the student has already an attendance record for today, then it will return true.
	 * If the student does not have an attendance record for today, then it will return false.
	 * This function will be used in the createAttendance function.
	 * First we will take the student object from the attendance object.
	 * Then we will check if the student object is null, if it is null then it will return false.
	 * Let's get the latest attendance of the student.
	 * Take the latest attendance and check the date of that attendance and compare it to the date today.
	 * If it is the same, then we will return true because it means that the student has already an attendance record for today.
	 *
	 * @param student Student Object that will be checked if it has an attendance record for today.
	 * @return true if the student has an attendance record for today, false otherwise.
	 */
	private boolean isStudentRecordExist(Student student) {
		final int LIMIT = 1;

		if (student == null) {
			return false;
		}

		// Get the latest attendance record of the student.
		Attendance attendance = attendanceRepository.findAttendancesByStudentOrderByDateDesc(student, LIMIT, Sort.by("date").descending()).getFirst();
		if (attendance != null) {
			// The date today
			LocalDate today = LocalDate.now();
			// The date of the latest attendance record
			LocalDate latestAttendanceDate = attendance.getDate();
			return today.isEqual(latestAttendanceDate);
		}

		return false;
	}

	private boolean isAttendanceNotValid(Attendance attendance) {
		return attendance.getStudent() == null && attendance.getStatus() == null && attendance.getTime() == null && attendance.getTimeOut() == null;
	}

	private ExecutionStatus attendanceValidationFailedLog(Attendance attendance) {
		logger.debug("Attendance of {}, {} validation failed.", attendance.getStudent().getFirstName(), attendance.getStudent().getLastName());
		return ExecutionStatus.VALIDATION_ERROR;
	}

	private ExecutionStatus attendanceNotFoundLog(Long id) {
		logger.debug("Attendance ID: {} not found.", id);
		return ExecutionStatus.NOT_FOUND;
	}

	private ExecutionStatus attendanceInvalidIdLog(Long id) {
		logger.debug("Attendance ID: {} is invalid.", id);
		return ExecutionStatus.FAILURE;
	}
}