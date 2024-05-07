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

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQImplementation {
	static final String ATTENDANCE_EXCHANGE = "attendance-exchange";
	static final String ATTENDANCE_QUEUE = "attendance";

	// Region: Attendance Queue

	@Bean
	Queue attendanceQueue() {
		return new Queue(ATTENDANCE_QUEUE, true);
	}

	@Bean
	TopicExchange attendanceTopicExchange() {
		return new TopicExchange(ATTENDANCE_EXCHANGE);
	}

	@Bean
	public Binding attendanceBinding() {
		return new Binding(ATTENDANCE_QUEUE, Binding.DestinationType.QUEUE, "amq.topic", "attendance", null);
	}

	// End Region: Attendance Queue

	// Region: Attendance Notification Queue

	@Bean
	Queue attendanceNotificationQueue() {
		return new Queue("attendance-notifications", true);
	}

	@Bean
	TopicExchange attendanceNotificationTopicExchange() {
		return new TopicExchange("attendance-notifications-exchange");
	}

	@Bean
	public Binding attendanceNotificationsBinding() {
		return new Binding("attendance-notifications", Binding.DestinationType.QUEUE, "amq.topic", "attendance-notifications", null);
	}

	// End Region: Attendance Notification Queue

	// Region: Attendance Logs
	@Bean
	Queue attendanceLogsQueue() {
		return new Queue("attendance-logs", true);
	}

	@Bean
	TopicExchange attendanceLogsTopicExchange() {
		return new TopicExchange("attendance-logs-exchange");
	}

	@Bean
	public Binding attendanceLogsBinding() {
		return new Binding("attendance-logs", Binding.DestinationType.QUEUE, "amq.topic", "attendance-logs", null);
	}
}