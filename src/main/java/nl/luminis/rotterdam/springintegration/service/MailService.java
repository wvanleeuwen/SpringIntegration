package nl.luminis.rotterdam.springintegration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Autowired
	private SimpleMailMessage preConfiguredMessage;

	/**
	 * This method will compose and send the message
	 */
	public void sendMail(String to, String subject, String body) {
		System.out.println("Mail to be sent with <to>: "+to+" <subject>"+subject+" <body>"+body);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		System.out.println("Start sending mail");
		mailSender.send(message);
		System.out.println("Finished sending mail");	}

	/**
	 * This method will send a pre-configured message
	 */
	public void sendPreConfiguredMail(String message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
		mailMessage.setText(message);
		mailSender.send(mailMessage);
	}
}
