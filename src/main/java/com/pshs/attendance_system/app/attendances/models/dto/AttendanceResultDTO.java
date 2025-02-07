

package com.pshs.attendance_system.app.attendances.models.dto;

import com.pshs.attendance_system.app.students.models.dto.StudentDTO;
import com.pshs.attendance_system.enums.AttendanceStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceResultDTO implements Serializable {

	private StudentDTO student;
	private LocalDate date;
	private LocalTime time;
	private LocalTime timeOut;
	private AttendanceStatus attendanceStatus;
	private String message;
	private String hashedLrn;
}