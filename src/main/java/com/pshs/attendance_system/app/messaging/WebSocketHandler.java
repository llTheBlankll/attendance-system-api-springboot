

package com.pshs.attendance_system.app.messaging;

import com.pshs.attendance_system.app.attendances.services.AttendanceService;
import com.pshs.attendance_system.app.rfid_credentials.services.RFIDCredentialService;
import com.pshs.attendance_system.app.messaging.handlers.AttendanceWebSocketHandler;
import com.pshs.attendance_system.app.messaging.handlers.RealTimeNotificationsHandler;
import com.pshs.attendance_system.app.messaging.services.RealTimeNotificationSubscribers;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketHandler implements WebSocketConfigurer {

	private final AttendanceService attendanceService;
	private final RFIDCredentialService rfidCredentialService;
	private final RealTimeNotificationSubscribers realTimeNotificationSubscribers;

	public WebSocketHandler(AttendanceService attendanceService, RFIDCredentialService rfidCredentialService, RealTimeNotificationSubscribers realTimeNotificationSubscribers) {
		this.attendanceService = attendanceService;
		this.rfidCredentialService = rfidCredentialService;
		this.realTimeNotificationSubscribers = realTimeNotificationSubscribers;
	}


	/**
	 * Register the WebSocket handlers.
	 *
	 * @param registry The registry to use for registering the handlers.
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new AttendanceWebSocketHandler(attendanceService, rfidCredentialService, realTimeNotificationSubscribers), "/wsocket/attendance");
		registry.addHandler(new RealTimeNotificationsHandler(realTimeNotificationSubscribers), "/wsocket/realtime-attendance");
	}
}