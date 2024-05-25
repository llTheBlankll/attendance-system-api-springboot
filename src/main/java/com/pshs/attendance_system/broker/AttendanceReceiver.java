/*
 * COPYRIGHT (C) 2024 VINCE ANGELO BATECAN
 *
 * PERMISSION IS HEREBY GRANTED, FREE OF CHARGE, TO STUDENTS, FACULTY, AND STAFF OF PUNTURIN SENIOR HIGH SCHOOL TO USE THIS SOFTWARE FOR EDUCATIONAL PURPOSES ONLY.
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL COPIES OR SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 *
 * MODIFICATIONS:
 *
 * ANY MODIFICATIONS OR DERIVATIVE WORKS OF THE SOFTWARE SHALL BE CONSIDERED PART OF THE SOFTWARE AND SHALL BE SUBJECT TO THE TERMS AND CONDITIONS OF THIS LICENSE.
 * ANY PERSON OR ENTITY MAKING MODIFICATIONS TO THE SOFTWARE SHALL ASSIGN AND TRANSFER ALL RIGHT, TITLE, AND INTEREST IN AND TO SUCH MODIFICATIONS TO VINCE ANGELO BATECAN.
 * VINCE ANGELO BATECAN SHALL OWN ALL INTELLECTUAL PROPERTY RIGHTS IN AND TO SUCH MODIFICATIONS.
 *
 * NO COMMERCIAL USE:
 *
 * THE SOFTWARE SHALL NOT BE SOLD, RENTED, LEASED, OR OTHERWISE COMMERCIALLY EXPLOITED. THE SOFTWARE IS INTENDED FOR PERSONAL, NON-COMMERCIAL USE ONLY WITHIN PUNTURIN SENIOR HIGH SCHOOL.
 *
 * NO WARRANTIES:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.pshs.attendance_system.broker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pshs.attendance_system.dto.CardRFIDCredentialDTO;
import com.pshs.attendance_system.dto.AttendanceResultDTO;
import com.pshs.attendance_system.entities.RFIDCredential;
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

	private final RabbitTemplate rabbitTemplate;
	private final AttendanceService attendanceService;
	private final RFIDCredentialService rfidCredentialService;
	private static final Logger logger = LogManager.getLogger(AttendanceReceiver.class);
	private final ObjectMapper mapper = new ObjectMapper();

	public AttendanceReceiver(RabbitTemplate rabbitTemplate, AttendanceService attendanceService, RFIDCredentialService rfidCredentialService) {
		this.rabbitTemplate = rabbitTemplate;
		this.attendanceService = attendanceService;
		this.rfidCredentialService = rfidCredentialService;
		mapper.registerModule(new JavaTimeModule());
	}


	@RabbitListener(queues = "#{attendanceQueue.name}")
	public void receive(String in) {
		try {
			CardRFIDCredentialDTO studentRFID = mapper.readValue(in, CardRFIDCredentialDTO.class);

			// Check if the mode is for attendance
			if (studentRFID.getMode() == Mode.ATTENDANCE) { // ! If the mode is for attendance
				RFIDCredential rfidCredential = rfidCredentialService.getRFIDCredentialByHash(studentRFID.getHashedLrn());
				AttendanceResultDTO successfulAttendance = attendanceService.attendanceIn(rfidCredential);

				if (successfulAttendance != null) {
					rabbitTemplate.convertAndSend("amq.topic", "attendance-logs", mapper.writeValueAsString(successfulAttendance));
				} else {
					rabbitTemplate.convertAndSend("amq.topic", "attendance-logs", "Student might already signed in.");
				}
			} else if (studentRFID.getMode() == Mode.ATTENDANCE_OUT) { // ! If the mode is for attendance out
				RFIDCredential rfidCredential = rfidCredentialService.getRFIDCredentialByHash(studentRFID.getHashedLrn());
				AttendanceResultDTO successfulAttendance = attendanceService.attendanceOut(rfidCredential);

				if (successfulAttendance != null) {
					rabbitTemplate.convertAndSend("amq.topic", "attendance-logs", mapper.writeValueAsString(successfulAttendance));
				} else {
					rabbitTemplate.convertAndSend("amq.topic", "attendance-logs", "Student might already signed out.");
				}
			} else { // ! If the mode is invalid
				logger.debug("Invalid mode.");
				rabbitTemplate.convertAndSend("amq.topic", "attendance-logs", "ERROR: Invalid mode received " + studentRFID.getMode());
			}
		} catch (Exception e) {
			logger.debug("Error receiving message: {}", e.getMessage());
			rabbitTemplate.convertAndSend("amq.topic", "attendance-logs", "ERROR: " + e.getMessage());
		}
	}
}