package com.pshs.attendance_system.models;

import com.pshs.attendance_system.enums.ExecutionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {

	private String message;
	private ExecutionStatus status;

	public MessageResponse() {

	}

	public MessageResponse(String message, ExecutionStatus status) {
		this.message = message;
		this.status = status;
	}

	public MessageResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public MessageResponse setStatus(ExecutionStatus status) {
		this.status = status;
		return this;
	}
}