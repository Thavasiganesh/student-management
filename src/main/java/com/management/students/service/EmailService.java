package com.management.students.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.*;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String toEmail,String subjet,String body) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("studentmanagementsystem2025@gmail.com");
		message.setTo(toEmail);
		message.setSubject(subjet);
		message.setText(body);
		mailSender.send(message);
	}
	
}
