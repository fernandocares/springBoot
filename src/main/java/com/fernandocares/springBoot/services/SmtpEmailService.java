package com.fernandocares.springBoot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService{
	
	@Autowired
	private MailSender mailSender;
	
	private static final Logger logger = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		
		logger.info("Enviando email: ");
		mailSender.send(msg);
		logger.info("Email enviado");
	}
	
}
