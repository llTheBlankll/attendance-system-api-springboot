package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.entities.Attendance;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Attendance}
 */
public class AttendanceDTO implements Serializable {
	private final Integer id;
	private final StudentDTO student;
	private final String status;
	private final LocalDate date;
	private final LocalTime time;
	private final LocalTime timeOut;

	public AttendanceDTO(Integer id, StudentDTO student, String status, LocalDate date, LocalTime time, LocalTime timeOut) {
		this.id = id;
		this.student = student;
		this.status = status;
		this.date = date;
		this.time = time;
		this.timeOut = timeOut;
	}

	public Attendance toEntity() {
		return new Attendance(id, student.toEntity(), status, date, time, timeOut);

	}

	public Integer getId() {
		return id;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public String getStatus() {
		return status;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getTime() {
		return time;
	}

	public LocalTime getTimeOut() {
		return timeOut;
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