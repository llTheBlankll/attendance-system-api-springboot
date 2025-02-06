package com.pshs.attendance_system.app.messaging.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RealTimeNotificationSubscribers {

	private static final Logger logger = LogManager.getLogger(RealTimeNotificationSubscribers.class);
	private final List<WebSocketSession> subscribers = new ArrayList<>();

	public void addSubscriber(WebSocketSession session) {
		subscribers.add(session);
	}

	@Async
	public void notifySubscribers(String message) {
		subscribers.forEach(subscriber -> {
			try {
				subscriber.sendMessage(new TextMessage(message));
			} catch (IOException ex) {
				logger.error("Error sending message to subscriber: {}", ex.getMessage());
			}
		});
	}

public void removeSubscriber(WebSocketSession session) {
		subscribers.remove(session);
	}
}