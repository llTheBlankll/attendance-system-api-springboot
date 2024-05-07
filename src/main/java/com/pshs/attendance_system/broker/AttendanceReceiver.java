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
import com.pshs.attendance_system.dto.RFIDCredentialDTO;
import com.pshs.attendance_system.dto.StudentRFID;
import com.pshs.attendance_system.entities.RFIDCredential;
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

	public AttendanceReceiver(RabbitTemplate rabbitTemplate, AttendanceService attendanceService, RFIDCredentialService rfidCredentialService) {
		this.rabbitTemplate = rabbitTemplate;
		this.attendanceService = attendanceService;
		this.rfidCredentialService = rfidCredentialService;
	}



	@RabbitListener(queues = "#{attendanceQueue.name}")
	public void receive(String in) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			StudentRFID studentRFID = mapper.readValue(in, StudentRFID.class);
			RFIDCredential rfidCredential = rfidCredentialService.getRFIDCredentialByStudentId(studentRFID.getLrn());

			if (rfidCredential.getLrn() != null) {
				// TODO: Implement method to create attendance
				attendanceService.createAttendance(rfidCredential.getLrn().getId());
			}

			// ! If success, send a message to the topic exchange in topic attendance-notifications
			rabbitTemplate.convertAndSend("amq.topic", "attendance-notifications", "SUCCESS");
			logger.info("Received message: {}", studentRFID.getLrn());
		} catch (Exception e) {
			rabbitTemplate.convertAndSend("amq.topic", "attendance-logs", "ERROR: " + e.getMessage());
			logger.info("Error receiving message: {}", e.getMessage());
		}
	}
}