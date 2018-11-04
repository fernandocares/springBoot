package com.fernandocares.springBoot.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService{
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger logger = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		logger.info("Enviando email: ");
		mailSender.send(msg);
		logger.info("Email enviado");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		logger.info("Enviando email: ");
		javaMailSender.send(msg);
		logger.info("Email enviado");	
	}
	
}
