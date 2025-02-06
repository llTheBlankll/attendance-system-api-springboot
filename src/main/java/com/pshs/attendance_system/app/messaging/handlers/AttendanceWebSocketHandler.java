

package com.pshs.attendance_system.app.messaging.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pshs.attendance_system.app.attendances.models.dto.AttendanceResultDTO;
import com.pshs.attendance_system.app.attendances.services.AttendanceService;
import com.pshs.attendance_system.models.MessageResponse;
import com.pshs.attendance_system.app.rfid_credentials.models.dto.CardRFIDCredentialDTO;
import com.pshs.attendance_system.app.rfid_credentials.models.entities.RFIDCredential;
import com.pshs.attendance_system.app.rfid_credentials.services.RFIDCredentialService;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.enums.Mode;
import com.pshs.attendance_system.app.messaging.services.RealTimeNotificationSubscribers;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.util.annotation.NonNull;

import java.io.IOException;
import java.util.Objects;

@Component
@Log4j2
public class AttendanceWebSocketHandler extends TextWebSocketHandler {

	private final AttendanceService attendanceService;
	private final RFIDCredentialService rfidCredentialService;
	private final RealTimeNotificationSubscribers realTimeNotificationSubscribers;
	private final ObjectMapper mapper = new ObjectMapper();

	private final String STUDENT_ALREADY_SIGNED_OUT = "Student already signed out.";

	public AttendanceWebSocketHandler(AttendanceService attendanceService, RFIDCredentialService rfidCredentialService, RealTimeNotificationSubscribers realTimeNotificationSubscribers) {
		this.attendanceService = attendanceService;
		this.rfidCredentialService = rfidCredentialService;
		this.realTimeNotificationSubscribers = realTimeNotificationSubscribers;
		mapper.registerModule(new JavaTimeModule());
	}

	private void notifySubscribers(AttendanceResultDTO attendanceResultDTO, String toCompare) throws JsonProcessingException {
		// The message will not send if those messages are something like they already has been recorded.
		if (!Objects.equals(attendanceResultDTO.getMessage(), toCompare)) {
			realTimeNotificationSubscribers.notifySubscribers(
				mapper.writeValueAsString(attendanceResultDTO)
			);
		}
	}

	/**
	 * @param session WebSocket Connection
	 * @param message The message received from websocket
	 * @throws JsonProcessingException The conversion between TextMessage to CardRFIDCredentialDTO
	 */
	@Override
	public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) throws IOException {
		// Extract the message
		TextMessage textMessage = (TextMessage) message;

		// Convert the message to studentRFID and retrieve the data from the database.
		CardRFIDCredentialDTO studentRFID = mapper.readValue(textMessage.getPayload(), CardRFIDCredentialDTO.class);
		RFIDCredential rfidCredential = rfidCredentialService.getRFIDCredentialByHash(studentRFID.getHashedLrn());

		// Checks if rfid credential doesn't exist and don't continue execution.
		if (rfidCredential == null) {
			session.sendMessage(
				new TextMessage(mapper.writeValueAsString(new MessageResponse("FAILURE", ExecutionStatus.FAILED)))
			);
			return;
		}

		if (Mode.ATTENDANCE_IN == studentRFID.getMode()) {
			log.debug("Attendance In Mode");
			AttendanceResultDTO attendanceResultDTO = attendanceService.attendanceIn(rfidCredential);

			// Send the result of the attendance.
			session.sendMessage(new TextMessage(mapper.writeValueAsString(attendanceResultDTO)));

			// ! Notify the subscribers in real time notification
			String STUDENT_ALREADY_RECORDED = "Student already recorded.";
//			notifySubscribers(attendanceResultDTO, STUDENT_ALREADY_RECORDED);
			notifySubscribers(attendanceResultDTO, ""); // TODO: CHANGE THIS IF IN PRODUCTION
			return;
		}

		if (Mode.ATTENDANCE_OUT == studentRFID.getMode()) {
			log.debug("Attendance Out Mode");
			AttendanceResultDTO attendanceResultDTO = attendanceService.attendanceOut(rfidCredential);

			// Send the result of the attendance.
			session.sendMessage(new TextMessage(mapper.writeValueAsString(attendanceResultDTO)));

			// ! Notify the subscribers in real time notification
//			notifySubscribers(attendanceResultDTO, STUDENT_ALREADY_SIGNED_OUT); // TODO: CHANGE THIS IF IN PRODUCTION
			notifySubscribers(attendanceResultDTO, "");
		}
	}

	@Override
	public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		log.info("WebSocket connection established for session: {}", session.getId());
	}
}