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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pshs.attendance_system.dto.AttendanceResultDTO;
import com.pshs.attendance_system.entities.Attendance;
import com.pshs.attendance_system.entities.RFIDCredential;
import com.pshs.attendance_system.entities.Student;
import com.pshs.attendance_system.entities.range.DateRange;
import com.pshs.attendance_system.enums.AttendanceStatus;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.enums.Status;
import com.pshs.attendance_system.exceptions.AttendanceInvalidException;
import com.pshs.attendance_system.exceptions.AttendanceNotFoundException;
import com.pshs.attendance_system.exceptions.StudentAlreadySignedOutException;
import com.pshs.attendance_system.repositories.AttendanceRepository;
import com.pshs.attendance_system.services.AttendanceService;
import com.pshs.attendance_system.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	private static final Logger logger = LogManager.getLogger(AttendanceServiceImpl.class);
	private final StudentService studentService;
	private final AttendanceRepository attendanceRepository;
	private final ObjectMapper mapper = new ObjectMapper();
	private final RabbitTemplate rabbitTemplate;

	public AttendanceServiceImpl(StudentService studentService, AttendanceRepository attendanceRepository, RabbitTemplate rabbitTemplate) {
		this.studentService = studentService;
		this.attendanceRepository = attendanceRepository;
		this.rabbitTemplate = rabbitTemplate;
		mapper.registerModule(new JavaTimeModule());
	}

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
			// Get Student and Attendance Status
			Status status = this.getAttendanceStatus();

			// Set attendance info.
			attendance.setStudent(student);
			attendance.setStatus(status);
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

			// Get Student and Attendance Status
			Status status = this.getAttendanceStatus();

			// Set attendance info.
			Attendance attendance = new Attendance();
			attendance.setStudent(student);
			attendance.setStatus(status);
			attendance.setTime(LocalTime.now());
			attendance.setDate(LocalDate.now());

			// Save attendance and send a message to the topic exchange in topic attendance-notifications
			Attendance savedAttendance = this.attendanceRepository.save(attendance);
			logger.debug("Attendance of {}, {} has been created.", savedAttendance.getStudent().getFirstName(), savedAttendance.getStudent().getLastName());
			rabbitTemplate.convertAndSend("amq.topic", "attendance-notifications", mapper.writeValueAsString(savedAttendance));
			return savedAttendance;
		} catch (Exception e) {
			rabbitTemplate.convertAndSend("amq.topic", "attendance-logs", e.getMessage());
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
		return (rowAffected > 0) ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILURE;
	}

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
	 * @return {@link Status}
	 * @see Status
	 */
	public Status getAttendanceStatus() {
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
			return Status.ON_TIME;
		} else if (currentLocalTime.isAfter(lateArrivalTime)) {
			return Status.LATE;
		} else {
			return Status.ON_TIME;
		}
	}

	@Override
	public AttendanceResultDTO attendanceIn(RFIDCredential rfidCredential) {
		AttendanceResultDTO attendanceResultDTO = new AttendanceResultDTO();
		Attendance attendance = this.createAttendance(rfidCredential.getLrn().getId());
		if (rfidCredential.getLrn() != null) {
			if (attendance == null) { // attendance being null has many meaning, but usually it means that the student is already recorded
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
			if (attendance.getStatus() == Status.EXISTS) {
				attendanceResultDTO.setDate(attendance.getDate())
					.setTime(attendance.getTime())
					.setTimeOut(attendance.getTimeOut())
					.setStatus(attendance.getStatus())
					.setHashedLrn(rfidCredential.getHashedLrn())
					.setStudent(attendance.getStudent().toDTO())
					.setMessage("Student already recorded.");
				logger.debug("Student already recorded.");
				logger.debug("Status: {}", attendance.getStatus());
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
					this.updateAttendanceTimeOut(attendance.getId(), timeSignOut);

					// Successful attendance dto is used for sending a message to the topic exchange in topic attendance-notifications
					attendanceResultDTO.setDate(attendance.getDate())
						.setTime(attendance.getTime())
						.setTimeOut(timeSignOut)
						.setStatus(Status.SIGNED_OUT) // * Status.OUT is used for marking the student as signed out
						.setStudent(attendance.getStudent().toDTO())
						.setHashedLrn(rfidCredential.getHashedLrn())
						.setMessage("Student successfully signed out.");

					logger.debug("Student successfully signed out.");
					rabbitTemplate.convertAndSend("amq.topic", "attendance-notifications", mapper.writeValueAsString(attendanceResultDTO));
				} else {
					// ! If student has already signed out, send a message to the topic exchange in topic attendance-logs
					logger.debug("Student already signed out.");

					attendanceResultDTO.setDate(attendance.getDate())
						.setTime(attendance.getTime())
						.setTimeOut(attendance.getTimeOut())
						.setStatus(Status.SIGNED_OUT) // * Status.OUT is used for marking the student as signed out
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

	@Override
	public Attendance getAttendanceByStudentDate(Long studentId, LocalDate date) {
		return attendanceRepository.getAttendanceByStudentDate(studentId, date).orElse(null);
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
		return ExecutionStatus.FAILURE;
	}
}