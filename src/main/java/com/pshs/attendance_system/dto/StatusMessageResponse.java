package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.enums.ExecutionStatus;

public class StatusMessageResponse {

	private String message;
	private ExecutionStatus status;

	public StatusMessageResponse() {

	}

	public StatusMessageResponse(String message, ExecutionStatus status) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public StatusMessageResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public ExecutionStatus getStatus() {
		return status;
	}

	public StatusMessageResponse setStatus(ExecutionStatus status) {
		this.status = status;
		return this;
	}
}