

package com.pshs.attendance_system.app.attendances.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pshs.attendance_system.enums.AttendanceStatus;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.attendances.models.dto.AttendanceResultDTO;
import com.pshs.attendance_system.app.attendances.models.entities.Attendance;
import com.pshs.attendance_system.app.rfid_credentials.models.entities.RFIDCredential;
import com.pshs.attendance_system.app.students.models.entities.Student;
import com.pshs.attendance_system.models.DateRange;
import com.pshs.attendance_system.app.attendances.repositories.AttendanceRepository;
import com.pshs.attendance_system.app.attendances.services.AttendanceService;
import com.pshs.attendance_system.app.students.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	private static final Logger logger = LogManager.getLogger(AttendanceServiceImpl.class);
	private final StudentService studentService;
	private final AttendanceRepository attendanceRepository;

	public AttendanceServiceImpl(StudentService studentService, AttendanceRepository attendanceRepository) {
		this.studentService = studentService;
		this.attendanceRepository = attendanceRepository;
	}

	// Region: AttendanceService Implementation

	/**
	 * Create a new attendance record with the student.
	 *
	 * @param attendance Attendance Object that will be created in the database.
	 * @return ExecutionStatus
	 */
	@Override
	public Attendance createAttendance(Attendance attendance) {
		// Checks the DATE if there is already an attendance record, if there is then it will return an error.
		// Search attendance by student and check the attendance object 'date' if it already exists.
		// If it exists, return an error.
		// If it does not exist, create the attendance record.
		if (isAttendanceNotValid(attendance) && attendanceRepository.existsById(attendance.getId())) {
			logger.debug("Attendance is invalid and cannot be accepted.");
			return null;
		}

		Student student = attendance.getStudent();
		if (student == null) {
			logger.debug("Student is null and cannot be accepted.");
			return null;
		}

		// Check if the student already has an attendance record for today.
		if (isStudentRecordTodayExist(student)) {
			logger.debug("Student {} already has an attendance record for today.", student.getFirstName());
			return null;
		}

		try {
			// Get Student and Attendance AttendanceStatus
			AttendanceStatus attendanceStatus = this.getAttendanceStatus();

			// Set attendance info.
			attendance.setStudent(student);
			attendance.setStatus(attendanceStatus);
			attendance.setTime(LocalTime.now());
			attendance.setDate(LocalDate.now());

			// Save attendance.
			this.attendanceRepository.save(attendance);
			logger.debug("Attendance of {}, {} has been created.", attendance.getStudent().getFirstName(), attendance.getStudent().getLastName());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	@Override
	public Attendance createAttendance(Long studentId) {
		if (studentId <= 0 || !studentService.isStudentExist(studentId)) {
			logger.debug("Invalid student id or student does not exist.");
			return null;
		}

		try {
			// Get Student
			Student student = studentService.getStudentById(studentId);
			// Check for existing attendance record for today
			if (isStudentRecordTodayExist(student)) {
				logger.debug("Student {} already has an attendance record for today.", student.getFirstName());
				return null;
			}

			if (student != null) {
				logger.debug("Student: {} {} has been found.", student.getFirstName(), student.getLastName());
			} else {
				return null;
			}

			// Get Student and Attendance AttendanceStatus
			AttendanceStatus attendanceStatus = this.getAttendanceStatus();

			// Set attendance info.
			Attendance attendance = new Attendance();
			attendance.setStudent(student);
			attendance.setStatus(attendanceStatus);
			attendance.setTime(LocalTime.now());
			attendance.setDate(LocalDate.now());

			// Save attendance and send a message to the topic exchange in topic attendance-notifications
			return this.attendanceRepository.save(attendance);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	/**
	 * Update the attendance record with the student. Requires the attendance id, and a new attendance object with the updated values.
	 *
	 * @param id      Attendance ID
	 * @param timeOut Updated Time Out
	 * @return ExecutionStatus {@link ExecutionStatus}
	 * @see ExecutionStatus
	 */
	@Override
	public ExecutionStatus updateAttendanceTimeOut(int id, LocalTime timeOut) {
		if (id <= 0) {
			return attendanceInvalidIdLog(id);
		}

		if (isAttendanceNotExist(id)) {
			return attendanceNotFoundLog(id);
		}

		int rowAffected = attendanceRepository.updateAttendanceTimeOut(timeOut, id);
		return (rowAffected > 0) ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILED;
	}

	@Override
	public AttendanceResultDTO attendanceIn(RFIDCredential rfidCredential) {
		AttendanceResultDTO attendanceResultDTO = new AttendanceResultDTO();
		Attendance attendance = this.createAttendance(rfidCredential.getLrn().getId());
		if (rfidCredential.getLrn() != null) {
			if (attendance == null) { // attendance being null has many meanings,
				// but usually it means that the student is already recorded
				attendance = getAttendanceByStudentDate(rfidCredential.getLrn().getId(), LocalDate.now());
				if (attendance != null) {
					// If the existing attendance is not null, it means that the student is already recorded.
					attendanceResultDTO.setDate(attendance.getDate())
						.setTime(attendance.getTime())
						.setTimeOut(attendance.getTimeOut())
						.setStatus(attendance.getStatus())
						.setStudent(attendance.getStudent().toDTO())
						.setHashedLrn(rfidCredential.getHashedLrn())
						.setMessage("Student already recorded.");

				} else {
					attendanceResultDTO.setMessage("Student not found.");
				}
				return attendanceResultDTO;
			}

			// Successful attendance dto is used for sending a message to the topic exchange in topic attendance-notifications
			if (attendance.getStatus() == AttendanceStatus.EXISTS) {
				attendanceResultDTO.setDate(attendance.getDate())
					.setTime(attendance.getTime())
					.setTimeOut(attendance.getTimeOut())
					.setStatus(attendance.getStatus())
					.setHashedLrn(rfidCredential.getHashedLrn())
					.setStudent(attendance.getStudent().toDTO())
					.setMessage("Student already recorded.");
				logger.debug("Student already recorded.");
				logger.debug("AttendanceStatus: {}", attendance.getStatus());
				logger.debug("Time Out: {}", attendance.getTimeOut());
			} else {
				attendanceResultDTO.setDate(attendance.getDate())
					.setTime(attendance.getTime())
					.setTimeOut(attendance.getTimeOut())
					.setStatus(attendance.getStatus())
					.setStudent(attendance.getStudent().toDTO())
					.setHashedLrn(rfidCredential.getHashedLrn())
					.setMessage("Student successfully recorded.");
			}
		} else {
			logger.debug("Student not found.");
			attendanceResultDTO.setMessage("Student not found.");
		}

		return attendanceResultDTO;
	}

	@Override
	public AttendanceResultDTO attendanceOut(RFIDCredential rfidCredential) throws JsonProcessingException {
		AttendanceResultDTO attendanceResultDTO = new AttendanceResultDTO();
		if (rfidCredential != null && rfidCredential.getLrn() != null) {
			// Get the attendance record of the student by the student id and the current date
			Attendance attendance = this.getAttendanceByStudentDate(rfidCredential.getLrn().getId(), LocalDate.now());
			if (attendance != null) {
				// Check if the student has already signed out
				if (attendance.getTimeOut() == null) {
					LocalTime timeSignOut = LocalTime.now();

					// Update the attendance record with the student. Requires the attendance id.
					ExecutionStatus status = this.updateAttendanceTimeOut(attendance.getId(), timeSignOut);
					if (status == ExecutionStatus.FAILED) {
						logger.debug("Failed to update the attendance record.");
						attendanceResultDTO.setMessage("Failed to update the attendance record.");
						return attendanceResultDTO;
					}

					// Successful attendance dto is used for sending a message to the topic exchange in topic attendance-notifications
					attendanceResultDTO.setDate(attendance.getDate())
						.setTime(attendance.getTime())
						.setTimeOut(timeSignOut)
						.setStatus(AttendanceStatus.SIGNED_OUT) // * AttendanceStatus.OUT is used for marking the student as signed out
						.setStudent(attendance.getStudent().toDTO())
						.setHashedLrn(rfidCredential.getHashedLrn())
						.setMessage("Student successfully signed out.");

					logger.debug("Student successfully signed out.");
				} else {
					// ! If a student has already signed out, send a message to the topic exchange in topic attendance-logs
					logger.debug("Student already signed out.");

					attendanceResultDTO.setDate(attendance.getDate())
						.setTime(attendance.getTime())
						.setTimeOut(attendance.getTimeOut())
						.setStatus(AttendanceStatus.SIGNED_OUT) // * AttendanceStatus.OUT is used for marking the student as signed out
						.setStudent(attendance.getStudent().toDTO())
						.setHashedLrn(rfidCredential.getHashedLrn())
						.setMessage("Student already signed out.");
				}
			} else {
				logger.debug("Attendance not found.");
				attendanceResultDTO.setMessage("Attendance not found.");
			}
		} else {
			logger.debug("Student not found.");
			attendanceResultDTO.setMessage("Student not found.");
		}

		return attendanceResultDTO;
	}

	private boolean isTodayMonday() {
		return LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY);
	}

	/**
	 * Update the attendance record with the student. Requires the attendance id, and a new attendance object with the updated values.
	 *
	 * @param id         Attendance ID
	 * @param attendance Updated Attendance Object
	 * @return ExecutionStatus
	 */
	@Override
	public ExecutionStatus updateAttendance(int id, Attendance attendance) {
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
	public ExecutionStatus deleteAttendance(int id) {
		if (id <= 0) {
			return attendanceInvalidIdLog(id);
		}

		if (isAttendanceNotExist(id)) {
			return attendanceNotFoundLog(id);
		}

		attendanceRepository.deleteById(id);
		logger.debug("Attendance ID: {} has been deleted.", id);
		return ExecutionStatus.SUCCESS;
	}

	// End: AttendanceService Implementation

	// Region: Retrieval Region

	/**
	 * Get the attendance status of the student. The status can be one of the following:
	 * <p>
	 * * On Time
	 * <p>
	 * * Late
	 * <p>
	 * * On Time
	 * <p>
	 * The current time is after onTimeArrival and before lateArrivalTime.
	 * The onTimeArrival is set to 5:30. The lateArrivalTime is set to 6:30 if it's Monday, otherwise it's set to 7:00.
	 * So, the range for being on time is from 5:30 to 6:30 on Mondays and from 5:30 to 7:00 on other days.
	 * <p>
	 * *Late
	 * The current time is after lateArrivalTime.
	 * As mentioned above, lateArrivalTime is set to 6:30 if it's Monday, otherwise it's set to 7:00.
	 * So, if the current time is after these times, the status is set to
	 * late.
	 * <p>
	 * * On Time (default):
	 * If the current time doesn't fall into the above two conditions,
	 * the status is set to on time by default.
	 * This would be the case if the current time is before onTimeArrival (5:30).
	 *
	 * @return {@link AttendanceStatus}
	 * @see AttendanceStatus
	 */
	public AttendanceStatus getAttendanceStatus() {
		LocalTime lateArrivalTime;
		LocalTime onTimeArrival = LocalTime.of(5, 30);

		LocalTime currentLocalTime = new Time(System.currentTimeMillis()).toLocalTime();

		// Flag Ceremony Time
		if (isTodayMonday()) { // * Which also mean flag ceremony day
			lateArrivalTime = LocalTime.of(6, 30);
		} else {
			lateArrivalTime = LocalTime.of(7, 0); // * Regular day
		}

		if (currentLocalTime.isBefore(lateArrivalTime) && currentLocalTime.isAfter(onTimeArrival)) {
			return AttendanceStatus.ON_TIME;
		} else if (currentLocalTime.isAfter(lateArrivalTime)) {
			return AttendanceStatus.LATE;
		} else {
			return AttendanceStatus.ON_TIME;
		}
	}

	/**
	 * Get the attendance record of a student by the attendance id.
	 *
	 * @param id Attendance ID
	 * @return Attendance of a student
	 */
	@Override
	public Attendance getAttendanceById(int id) {
		if (id <= 0) {
			return null;
		}

		if (isAttendanceNotExist(id)) {
			return null;
		}

		return attendanceRepository.findById(id).orElse(null);
	}

	/**
	 * Get the attendance record of a student by the student id and the date.
	 *
	 * @param studentId Student ID
	 * @param date      Date of the attendance
	 * @return Attendance of a student
	 */
	@Override
	public Attendance getAttendanceByStudentDate(Long studentId, LocalDate date) {
		return attendanceRepository.getAttendanceByStudentDate(studentId, date).orElse(null);
	}

	/**
	 * Get all the attendance of every student in existence within the database.
	 *
	 * @param date      Date of the attendance
	 * @param sectionId Section ID
	 * @return return the list of attendance
	 */
	@Override
	public List<Attendance> getAllAttendancesByDateAndSection(LocalDate date, Integer sectionId) {
		return attendanceRepository.getAttendanceByDateAndSection(date, sectionId);
	}

	/**
	 * Get all the attendance of every student in existence within the database.
	 *
	 * @param page Page
	 * @return return the page object
	 */
	@Override
	public Page<Attendance> getAllAttendances(Pageable page) {
		return attendanceRepository.findAll(page);
	}

	/**
	 * Get all the attendance of every student in existence within the database.
	 *
	 * @param date Date of the attendance
	 * @return return the list of attendance
	 */
	@Override
	public List<Attendance> getAllAttendancesByDate(LocalDate date) {
		return attendanceRepository.listAllAttendancesByDate(date, Sort.by(Sort.Direction.ASC, "time", "timeOut"));
	}

	/**
	 * @param studentId Student ID
	 * @param page      Page
	 * @return return the page object
	 */
	@Override
	public Page<Attendance> getAllAttendancesByStudentId(Long studentId, Pageable page) {
		return attendanceRepository.findAllAttendancesByStudentId(studentId, page);
	}

	@Override
	public Page<Attendance> getAllAttendancesByDate(LocalDate date, Pageable page) {
		return attendanceRepository.getAllAttendancesByDate(date, page);
	}

	/**
	 * Get all the attendance of every student in existence within the database.
	 *
	 * @param date         Date of the attendance
	 * @param sectionId    Section ID
	 * @param gradeLevelId Grade Level ID
	 * @param page         Page
	 * @return return the page object
	 */
	@Override
	public Page<Attendance> getAllSectionAndGradeLevelAttendanceByDate(LocalDate date, Integer sectionId, Integer gradeLevelId, Pageable page) {
		return attendanceRepository.getAllSectionAndGradeLevelAttendanceByDate(sectionId, gradeLevelId, date, page);
	}

	// End: Retrieval Region

	// Region: Statistics Region

	/**
	 * Count the total number of students with the attendance attendanceStatus between two dates.
	 *
	 * @param attendanceStatus Attendance AttendanceStatus (LATE, ON_TIME, ...)
	 * @param dateRange        from 2024-10-1 to 2024-12-1
	 * @return the number of attendances from all students.
	 */
	@Override
	public int countStudentsByStatusAndDateRange(AttendanceStatus attendanceStatus, DateRange dateRange) {
		return (int) attendanceRepository.countStudentsByStatusAndDateRange(attendanceStatus, dateRange.getStartDate(), dateRange.getEndDate());
	}

	/**
	 * Count the total number of students with the attendance attendanceStatus on a specific date.
	 *
	 * @param attendanceStatus Attendance AttendanceStatus (LATE, ON_TIME, ...)
	 * @param date             Specific Date
	 * @return the number of attendance of all the student with the specific attendanceStatus with the specific date
	 */
	@Override
	public int countStudentsByStatusAndDate(AttendanceStatus attendanceStatus, LocalDate date) {
		return (int) attendanceRepository.countStudentsByStatusAndDate(attendanceStatus, date);
	}

	/**
	 * Count the total number of students with the attendance attendanceStatus on a specific date.
	 *
	 * @param studentId        Student ID
	 * @param attendanceStatus Attendance AttendanceStatus (LATE, ON_TIME, ...)
	 * @param date             Specific Date
	 * @return the number of attendance of all the student with the specific attendanceStatus with the specific date
	 */
	@Override
	public int countStudentAttendanceByStatusAndDate(Long studentId, AttendanceStatus attendanceStatus, LocalDate date) {
		return (int) attendanceRepository.countStudentAttendancesByStatusAndDate(studentId, attendanceStatus, date);
	}

	/**
	 * Count the total number of students with the attendance attendanceStatus between two date.
	 *
	 * @param studentId        Student ID
	 * @param attendanceStatus Attendance AttendanceStatus (LATE, ON_TIME, ...)
	 * @param dateRange        from 2024-10-1 to 2024-12-1
	 * @return the number of attendance of all student with the specific attendanceStatus between the date range
	 */
	@Override
	public int countStudentAttendanceByStatusAndDate(Long studentId, AttendanceStatus attendanceStatus, DateRange dateRange) {
		return (int) attendanceRepository.countStudentAttendancesByStatusBetweenDate(studentId, attendanceStatus, dateRange.getStartDate(), dateRange.getEndDate());
	}

	/**
	 * Returns the total number of attendances of a student with the specific attendanceStatus.
	 *
	 * @param date             Date
	 * @param attendanceStatus AttendanceStatus
	 * @return the number of attendances of a student with the specific attendanceStatus
	 */
	@Override
	public int getAttendanceCountByDateAndStatus(LocalDate date, AttendanceStatus attendanceStatus) {
		return (int) attendanceRepository.getAttendanceCountByDateAndStatus(date, attendanceStatus);
	}

	@Override
	public int countAttendanceInSection(Integer sectionId, LocalDate date, AttendanceStatus attendanceStatus) {
		return (int) attendanceRepository.countAttendanceInSectionByDate(sectionId, date, attendanceStatus);
	}

	@Override
	public int countAttendanceInSection(Integer sectionId, DateRange dateRange, AttendanceStatus attendanceStatus) {
		return (int) attendanceRepository.countAttendanceInSection(sectionId, dateRange.getStartDate(), dateRange.getEndDate(), attendanceStatus);
	}

	@Override
	public int averageAttendanceInSection(Integer sectionId, LocalDate date, AttendanceStatus attendanceStatus) {
		return 0;
	}

	@Override
	public int averageAttendanceInSection(Integer sectionId, DateRange dateRange, AttendanceStatus attendanceStatus) {
		return 0;
	}

	public long totalAttendanceByDate(LocalDate date) {
		return attendanceRepository.countStudentsByStatusAndDate(AttendanceStatus.ON_TIME, date) + attendanceRepository.countStudentsByStatusAndDate(AttendanceStatus.LATE, date);
	}

	// End: Statistics Region

	@Override
	public boolean isCheckedIn(Long lrn) {
		return false;
	}

	@Override
	public boolean isScanned(Long lrn) {
		// Get the date now.
		LocalDate today = LocalDate.now();
		Student student = studentService.getStudentById(lrn);

		// Get the latest attendance record of the student.
		Attendance attendance = attendanceRepository.getAttendanceByStudentDate(student.getId(), today).orElse(null);
		assert attendance != null;
		if (attendance.getDate().isEqual(LocalDate.now())) {
			return attendance.getTimeOut() != null;
		} else {
			return false;
		}
	}

	@Override
	public boolean isAttendanceExist(Long studentId, LocalDate date) {
		if (studentId <= 0) {
			return false;
		}

		return attendanceRepository.isAttendanceExistByStudentAndDate(studentId, date);
	}

	@Override
	public boolean isAttendanceExist(int attendanceId, LocalDate date) {
		return false;
	}

	@Override
	public boolean isAttendanceExist(int attendanceId) {
		return attendanceRepository.existsById(attendanceId);
	}

	private boolean isAttendanceNotExist(int id) {
		return !attendanceRepository.existsById(id);
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
	private boolean isStudentRecordTodayExist(Student student) {
		if (student == null) {
			return false;
		}

		// Get the latest attendance record of the student.
		Optional<Attendance> attendance = attendanceRepository.isStudentAttendanceExist(student.getId(), LocalDate.now());
		return attendance.isPresent();
	}

	private boolean isAttendanceNotValid(Attendance attendance) {
		return attendance.getStudent() == null && attendance.getStatus() == null && attendance.getTime() == null && attendance.getTimeOut() == null;
	}

	private ExecutionStatus attendanceValidationFailedLog(Attendance attendance) {
		logger.debug("Attendance of {}, {} validation failed.", attendance.getStudent().getFirstName(), attendance.getStudent().getLastName());
		return ExecutionStatus.VALIDATION_ERROR;
	}

	private ExecutionStatus attendanceNotFoundLog(int id) {
		logger.debug("Attendance ID: {} not found.", id);
		return ExecutionStatus.NOT_FOUND;
	}

	private ExecutionStatus attendanceInvalidIdLog(int id) {
		logger.debug("Attendance ID: {} is invalid.", id);
		return ExecutionStatus.FAILED;
	}
}