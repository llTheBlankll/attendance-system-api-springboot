

package com.pshs.attendance_system.dto;

import java.io.Serializable;

public class StudentRFID implements Serializable {

	private Long lrn;
	private String rfid;

	public StudentRFID() {

	}

	public StudentRFID(Long lrn, String rfid) {
		this.lrn = lrn;
		this.rfid = rfid;
	}

	public Long getLrn() {
		return lrn;
	}

	public StudentRFID setLrn(Long lrn) {
		this.lrn = lrn;
		return this;
	}

	public String getRfid() {
		return rfid;
	}

	public StudentRFID setRfid(String rfid) {
		this.rfid = rfid;
		return this;
	}
}