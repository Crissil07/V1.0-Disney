package com.disney.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class notificationServ {
	
	@Autowired	
	private JavaMailSenderImpl mailSender;
	
	@Async //el hilo de ejecucion no espera a que se termine de mandar el mail, lo ejecuta en un hilo paralelo. El usuario tiene respuesta inmediata.
	public void sendMail (String bodyM, String titleM, String mail) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(mail);
		message.setFrom("notreply@disney.com");
		message.setSubject(titleM);
		message.setText(bodyM);
		
		mailSender.send(message);
		
	}

}

/*  TIRA ERROR - VER

APPLICATION FAILED TO START

Description:

Field mailSender in com.disney.services.notificationServ required a bean of type 'org.springframework.mail.javamail.JavaMailSenderImpl' that could not be found.

The injection point has the following annotations:
	- @org.springframework.beans.factory.annotation.Autowired(required=true)


Action:

Consider defining a bean of type 'org.springframework.mail.javamail.JavaMailSenderImpl' in your configuration. */
