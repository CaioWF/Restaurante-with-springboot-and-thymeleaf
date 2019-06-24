package br.com.ufc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
	
	@Autowired
	public JavaMailSender javaMailSender;
	
	public void sendMail(Double total, String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Pedido");
		message.setText("Pedido finalizado! Total a pagar R$ "+total);
		try {
			javaMailSender.send(message);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
