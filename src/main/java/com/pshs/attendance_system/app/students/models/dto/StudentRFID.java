

package com.pshs.attendance_system.app.students.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRFID implements Serializable {

	private Long lrn;
	private String rfid;
}