

package com.pshs.attendance_system.app.rfid_credentials.models.dto;

import com.pshs.attendance_system.enums.Mode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardRFIDCredentialDTO {

	private String hashedLrn;
	private Mode mode;
}