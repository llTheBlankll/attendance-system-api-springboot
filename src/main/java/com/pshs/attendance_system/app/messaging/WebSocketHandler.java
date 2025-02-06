

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

<<<<<<<<<<<<<<  ✨ Codeium Command ⭐ >>>>>>>>>>>>>>>>
	/**
	 * Register the WebSocket handlers.
	 *
	 * <p>This method registers the following WebSocket handlers:
	 * <ul>
	 *     <li>{@link AttendanceWebSocketHandler} on "/wsocket/attendance" path</li>
	 *     <li>{@link RealTimeNotificationsHandler} on "/wsocket/realtime-attendance" path</li>
	 * </ul>
	 *
	 * @param registry The {@link WebSocketHandlerRegistry} to register the handlers with.
	 */
<<<<<<<  9f80ec08-2e60-430e-a9d3-f7f6b04c3c28  >>>>>>>
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new AttendanceWebSocketHandler(attendanceService, rfidCredentialService, realTimeNotificationSubscribers), "/wsocket/attendance");
		registry.addHandler(new RealTimeNotificationsHandler(realTimeNotificationSubscribers), "/wsocket/realtime-attendance");
	}
}