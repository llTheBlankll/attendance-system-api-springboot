

package com.pshs.attendance_system.app.attendances.models.entities;

import com.pshs.attendance_system.app.attendances.models.dto.AttendanceDTO;
import com.pshs.attendance_system.app.students.models.entities.Student;
import com.pshs.attendance_system.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendances")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

	@Column(name = "status", length = Integer.MAX_VALUE)
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
	public AttendanceDTO toDTO() {
		return new AttendanceDTO(id, student.toDTO(), attendanceStatus, date, time, timeOut);
	}
}