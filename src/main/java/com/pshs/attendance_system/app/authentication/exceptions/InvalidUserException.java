package com.pshs.attendance_system.app.authentication.exceptions;

public class InvalidUserException extends RuntimeException {
	public InvalidUserException(String message) {
		super(message);
	}
}
