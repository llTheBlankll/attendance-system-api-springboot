package com.pshs.attendance_system.controllers.v1.exposed;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	private final JavaMailSender javaMailSender;

	public TestController(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@GetMapping("/test-email")
	public String testEmail() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("llTheBlankll@gmail.com");
		msg.setFrom("llTheBlankll@gmail.com");
		msg.setSubject("Test email");
		msg.setText("This is a test email");
		javaMailSender.send(msg);
		return "Email sent successfully!";
	}
}