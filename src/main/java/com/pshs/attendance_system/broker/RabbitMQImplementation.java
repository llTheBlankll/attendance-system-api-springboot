

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