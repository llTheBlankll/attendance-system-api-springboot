package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.enums.ExecutionStatus;

public class MessageResponse {

	private String message;
	private ExecutionStatus status;

	public MessageResponse() {

	}

	public MessageResponse(String message, ExecutionStatus status) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public MessageResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public ExecutionStatus getStatus() {
		return status;
	}

	public MessageResponse setStatus(ExecutionStatus status) {
		this.status = status;
		return this;
	}
}