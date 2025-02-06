package com.pshs.attendance_system.app.messaging.handlers;

import com.pshs.attendance_system.app.messaging.services.RealTimeNotificationSubscribers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class RealTimeNotificationsHandler extends TextWebSocketHandler {

	private static final Logger logger = LogManager.getLogger(RealTimeNotificationsHandler.class);
	private final RealTimeNotificationSubscribers subscribers;

	public RealTimeNotificationsHandler(RealTimeNotificationSubscribers subscribers) {
		super();
		this.subscribers = subscribers;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		subscribers.addSubscriber(session);
		session.sendMessage(new TextMessage("Successfully connected: " + session.getId()));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);

		// Remove the session from the subscriber list.
		if (status == CloseStatus.NORMAL) {
			subscribers.removeSubscriber(session);
		} else {
			subscribers.removeSubscriber(session);
			logger.debug("Connection closed with status: {}", status.getReason());
		}
	}
}