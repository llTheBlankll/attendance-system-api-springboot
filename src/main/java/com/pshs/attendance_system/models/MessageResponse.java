package com.pshs.attendance_system.models;

import com.pshs.attendance_system.enums.ExecutionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

	private String message;
	private ExecutionStatus status;
}