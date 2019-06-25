package br.com.ufc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.ufc.model.Item;
import br.com.ufc.model.Pedido;
import br.com.ufc.model.ShoppingCart;

@Service
public class MailSenderService {
	
	@Autowired
	public JavaMailSender javaMailSender;
	
	public void sendMail(Pedido pedido, String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Pedido Finalizado!");
		String text = "~Delivery Yukihira~\n\n";
		text += "Pedido Finalizado!\n\n";
		for(Item item: pedido.getItems()) {
			text += "- " + item.getQuantity() + "x | " + item.getDish().getName() + " | R$ " + item.getTotalPrice() + "\n";
		}
		text += "\nEndereço de entrega: " + pedido.getDeliveryAddress() + "\n";
		text += "TOTAL a pagar R$ " + pedido.getTotalPrice();
		message.setText(text);
		try {
			javaMailSender.send(message);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
