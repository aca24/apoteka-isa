package isa.apoteka.async.service;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import isa.apoteka.domain.Promotion;
import isa.apoteka.domain.User;


@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Async
	public void sendPromotionNotificaitionAsync(User user, Promotion promotion) throws MailException{
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		if(env.getProperty("spring.mail.username") == null) {
			return;
		}
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject(promotion.getTitle());
		mail.setText("Pozdrav " + user.getFirstName() + ",\n" + "\nPočetak promocije: " + new SimpleDateFormat("dd. MMM yyyy.").format(promotion.getStartOfPromotion())
				+ "\nKraj promocije: " + new SimpleDateFormat("dd. MMM yyyy.").format(promotion.getEndOfPromotion()) + "\n\n" + promotion.getContent());
		javaMailSender.send(mail);

	}

}
