

package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.enums.AttendanceStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceResultDTO implements Serializable {

	private StudentDTO student;
	private LocalDate date;
	private LocalTime time;
	private LocalTime timeOut;
	private AttendanceStatus attendanceStatus;
	private String message;
	private String hashedLrn;

	public AttendanceResultDTO(StudentDTO student, LocalDate date, LocalTime time, LocalTime timeOut, AttendanceStatus attendanceStatus, String message, String hashedLrn) {
		this.student = student;
		this.date = date;
		this.time = time;
		this.timeOut = timeOut;
		this.attendanceStatus = attendanceStatus;
		this.message = message;
		this.hashedLrn = hashedLrn;
	}

	public AttendanceResultDTO() {
	}

	public StudentDTO getStudent() {
		return student;
	}

	public AttendanceResultDTO setStudent(StudentDTO student) {
		this.student = student;
		return this;
	}

	public LocalDate getDate() {
		return date;
	}

	public AttendanceResultDTO setDate(LocalDate date) {
		this.date = date;
		return this;
	}

	public LocalTime getTime() {
		return time;
	}

	public AttendanceResultDTO setTime(LocalTime time) {
		this.time = time;
		return this;
	}

	public LocalTime getTimeOut() {
		return timeOut;
	}

	public AttendanceResultDTO setTimeOut(LocalTime timeOut) {
		this.timeOut = timeOut;
		return this;
	}

	public AttendanceStatus getStatus() {
		return attendanceStatus;
	}

	public AttendanceResultDTO setStatus(AttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public AttendanceResultDTO setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getHashedLrn() {
		return hashedLrn;
	}

	public AttendanceResultDTO setHashedLrn(String hashedLrn) {
		this.hashedLrn = hashedLrn;
		return this;
	}
}