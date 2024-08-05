

package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Attendance;
import com.pshs.attendance_system.enums.AttendanceStatus;

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
	private AttendanceStatus attendanceStatus;
	private LocalDate date;
	private LocalTime time;
	private LocalTime timeOut;

	public AttendanceDTO() {
	}

	public AttendanceDTO(Integer id, StudentDTO student, AttendanceStatus attendanceStatus, LocalDate date, LocalTime time, LocalTime timeOut) {
		this.id = id;
		this.student = student;
		this.attendanceStatus = attendanceStatus;
		this.date = date;
		this.time = time;
		this.timeOut = timeOut;
	}

	public Attendance toEntity() {
		return new Attendance()
			.setId(id)
			.setStudent(student.toEntity())
			.setStatus(attendanceStatus)
			.setDate(date)
			.setTime(time)
			.setTimeOut(timeOut);
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

	public AttendanceStatus getStatus() {
		return attendanceStatus;
	}

	public AttendanceDTO setStatus(AttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
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
			Objects.equals(this.attendanceStatus, entity.attendanceStatus) &&
			Objects.equals(this.date, entity.date) &&
			Objects.equals(this.time, entity.time) &&
			Objects.equals(this.timeOut, entity.timeOut);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, student, attendanceStatus, date, time, timeOut);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"student = " + student + ", " +
			"attendanceStatus = " + attendanceStatus + ", " +
			"date = " + date + ", " +
			"time = " + time + ", " +
			"timeOut = " + timeOut + ")";
	}
}