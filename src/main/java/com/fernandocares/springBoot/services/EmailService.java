package com.fernandocares.springBoot.services;

import org.springframework.mail.SimpleMailMessage;

import com.fernandocares.springBoot.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
	
}
