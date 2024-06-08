

package com.pshs.attendance_system.dto;

import com.pshs.attendance_system.enums.Mode;

public class CardRFIDCredentialDTO {

	private String hashedLrn;
	private Mode mode;

	public CardRFIDCredentialDTO() {
	}

	public CardRFIDCredentialDTO(String hashedLrn) {
		this.hashedLrn = hashedLrn;
	}

	public Mode getMode() {
		return mode;
	}

	public CardRFIDCredentialDTO setMode(Mode mode) {
		this.mode = mode;
		return this;
	}

	public String getHashedLrn() {
		return hashedLrn;
	}

	public CardRFIDCredentialDTO setHashedLrn(String hashedLrn) {
		this.hashedLrn = hashedLrn;
		return this;
	}
}