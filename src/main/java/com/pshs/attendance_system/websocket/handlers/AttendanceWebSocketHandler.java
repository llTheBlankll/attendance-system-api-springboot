

package com.pshs.attendance_system.websocket.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pshs.attendance_system.dto.AttendanceResultDTO;
import com.pshs.attendance_system.dto.CardRFIDCredentialDTO;
import com.pshs.attendance_system.entities.RFIDCredential;
import com.pshs.attendance_system.enums.Mode;
import com.pshs.attendance_system.services.AttendanceService;
import com.pshs.attendance_system.services.RFIDCredentialService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.util.annotation.NonNull;

@Component
public class AttendanceWebSocketHandler extends TextWebSocketHandler {

	private static final Logger logger = LogManager.getLogger(AttendanceWebSocketHandler.class);
	private final AttendanceService attendanceService;
	private final RFIDCredentialService rfidCredentialService;
	private final ObjectMapper mapper = new ObjectMapper();

	public AttendanceWebSocketHandler(AttendanceService attendanceService, RFIDCredentialService rfidCredentialService) {
		this.attendanceService = attendanceService;
		this.rfidCredentialService = rfidCredentialService;
		mapper.registerModule(new JavaTimeModule());
	}

	@Override
	public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) throws Exception {
		super.handleMessage(session, message);
		try {
			TextMessage textMessage = (TextMessage) message;
			CardRFIDCredentialDTO studentRFID = mapper.readValue(textMessage.getPayload(), CardRFIDCredentialDTO.class);
			if (Mode.ATTENDANCE == studentRFID.getMode()) {
				RFIDCredential rfidCredential = rfidCredentialService.getRFIDCredentialByHash(studentRFID.getHashedLrn());
				if (rfidCredential != null) {
					AttendanceResultDTO attendanceResultDTO = attendanceService.attendanceIn(rfidCredential);
					// Send the result of the attendance.
					session.sendMessage(new TextMessage(mapper.writeValueAsString(attendanceResultDTO)));
				} else {
					session.sendMessage(new TextMessage("RFID Credential not found."));
				}
			} else if (Mode.ATTENDANCE_OUT == studentRFID.getMode()) {
				RFIDCredential rfidCredential = rfidCredentialService.getRFIDCredentialByHash(studentRFID.getHashedLrn());
				if (rfidCredential != null) {
					AttendanceResultDTO attendanceResultDTO = attendanceService.attendanceOut(rfidCredential);
					// Send the result of the attendance.
					session.sendMessage(new TextMessage(mapper.writeValueAsString(attendanceResultDTO)));
				} else {
					session.sendMessage(new TextMessage("RFID Credential not found."));
				}
			} else {
				session.sendMessage(new TextMessage("Invalid mode."));
			}
		} catch (JsonProcessingException e) {
			logger.error("Error processing JSON message: {}", e.getMessage());
		}
	}

	@Override
	public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		logger.info("WebSocket connection established for session: {}", session.getId());
	}
}