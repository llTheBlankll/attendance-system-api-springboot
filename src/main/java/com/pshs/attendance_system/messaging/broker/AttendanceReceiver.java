

package com.pshs.attendance_system.messaging.broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pshs.attendance_system.models.dto.AttendanceResultDTO;
import com.pshs.attendance_system.models.dto.CardRFIDCredentialDTO;
import com.pshs.attendance_system.models.entities.RFIDCredential;
import com.pshs.attendance_system.enums.Mode;
import com.pshs.attendance_system.services.AttendanceService;
import com.pshs.attendance_system.services.RFIDCredentialService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AttendanceReceiver {

	private static final Logger logger = LogManager.getLogger(AttendanceReceiver.class);
	private final RabbitTemplate rabbitTemplate;
	private final AttendanceService attendanceService;
	private final RFIDCredentialService rfidCredentialService;
	private final ObjectMapper mapper = new ObjectMapper();

	public AttendanceReceiver(RabbitTemplate rabbitTemplate, AttendanceService attendanceService, RFIDCredentialService rfidCredentialService) {
		this.rabbitTemplate = rabbitTemplate;
		this.attendanceService = attendanceService;
		this.rfidCredentialService = rfidCredentialService;
		mapper.registerModule(new JavaTimeModule());
	}

	public void sendMessageToLogs(String message) {
		rabbitTemplate.convertAndSend("amq.topic", "attendance-logs", message);
	}

	public void sendMessageToNotifications(String message) {
		rabbitTemplate.convertAndSend("amq.topic", "attendance-notifications", message);
	}

	/**
	 * This method is a RabbitMQ listener that listens to the attendance queue.
	 * It receives a JSON string containing a student's RFID credential and mode of attendance.
	 * It then processes the received data and sends the appropriate response to the logs queue.
	 *
	 * @param in the JSON string containing the student's RFID credential and mode of attendance
	 */
	@RabbitListener(queues = "#{attendanceQueue.name}")
	public void receive(String in) {
		try {
			CardRFIDCredentialDTO studentRFID = mapper.readValue(in, CardRFIDCredentialDTO.class);

			if (studentRFID.getMode() == Mode.ATTENDANCE_IN) {
				RFIDCredential rfidCredential = rfidCredentialService.getRFIDCredentialByHash(studentRFID.getHashedLrn());
				AttendanceResultDTO successfulAttendance = attendanceService.attendanceIn(rfidCredential);

				if (successfulAttendance != null) {
					sendMessageToLogs(mapper.writeValueAsString(successfulAttendance));
				} else {
					sendMessageToLogs("Student might already signed in");
				}
			} else if (studentRFID.getMode() == Mode.ATTENDANCE_OUT) {
				RFIDCredential rfidCredential = rfidCredentialService.getRFIDCredentialByHash(studentRFID.getHashedLrn());
				AttendanceResultDTO successfulAttendance = attendanceService.attendanceOut(rfidCredential);

				if (successfulAttendance != null) {
					sendMessageToLogs(mapper.writeValueAsString(successfulAttendance));
				} else {
					sendMessageToLogs("Student might already signed out");
				}
			} else {
				logger.debug("Invalid mode.");
				sendMessageToLogs("ERROR: Invalid mode" + studentRFID.getMode());
			}
		} catch (Exception e) {
			logger.debug("Error receiving message: {}", e.getMessage());
			sendMessageToLogs("ERROR: " + e.getMessage());
		}
	}
}