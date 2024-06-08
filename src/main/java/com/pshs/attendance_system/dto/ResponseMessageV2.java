package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.enums.ResponseStatus;

public class ResponseMessageV2 {

	private String message;
	private ResponseStatus status;

	public ResponseMessageV2(String message, ResponseStatus status) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public ResponseMessageV2 setMessage(String message) {
		this.message = message;
		return this;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public ResponseMessageV2 setStatus(ResponseStatus status) {
		this.status = status;
		return this;
	}
}