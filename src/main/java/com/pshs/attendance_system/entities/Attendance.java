

package com.pshs.attendance_system.entities;

import com.pshs.attendance_system.dto.AttendanceDTO;
import com.pshs.attendance_system.enums.AttendanceStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendances")
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendances_id_gen")
	@SequenceGenerator(name = "attendances_id_gen", sequenceName = "attendances_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@Column(name = "attendanceStatus", length = Integer.MAX_VALUE)
	@Enumerated(EnumType.STRING)
	private AttendanceStatus attendanceStatus;

	@ColumnDefault("CURRENT_DATE")
	@Column(name = "date")
	private LocalDate date;

	@ColumnDefault("LOCALTIME")
	@Column(name = "time")
	private LocalTime time;

	@ColumnDefault("LOCALTIME")
	@Column(name = "time_out")
	private LocalTime timeOut;

	public Attendance() {
	}

	public Attendance(Integer id, Student student, AttendanceStatus attendanceStatus, LocalDate date, LocalTime time, LocalTime timeOut) {
		this.id = id;
		this.student = student;
		this.attendanceStatus = attendanceStatus;
		this.date = date;
		this.time = time;
		this.timeOut = timeOut;
	}

	public AttendanceDTO toDTO() {
		return new AttendanceDTO(id, student.toDTO(), attendanceStatus, date, time, timeOut);
	}

	public Integer getId() {
		return id;
	}

	public Attendance setId(Integer id) {
		this.id = id;
		return this;
	}

	public Student getStudent() {
		return student;
	}

	public Attendance setStudent(Student student) {
		this.student = student;
		return this;
	}

	public AttendanceStatus getStatus() {
		return attendanceStatus;
	}

	public Attendance setStatus(AttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
		return this;
	}

	public LocalDate getDate() {
		return date;
	}

	public Attendance setDate(LocalDate date) {
		this.date = date;
		return this;
	}

	public LocalTime getTime() {
		return time;
	}

	public Attendance setTime(LocalTime time) {
		this.time = time;
		return this;
	}

	public LocalTime getTimeOut() {
		return timeOut;
	}

	public Attendance setTimeOut(LocalTime timeOut) {
		this.timeOut = timeOut;
		return this;
	}
}