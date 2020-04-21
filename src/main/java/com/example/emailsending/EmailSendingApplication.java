package com.example.emailsending;

import com.example.emailsending.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.MailException;

@SpringBootApplication
public class EmailSendingApplication implements CommandLineRunner {

	@Autowired
	private EmailService emailService;


	public static void main(String[] args) {
		SpringApplication.run(EmailSendingApplication.class, args).close();
	}

	@Override
	public void run(String... args) throws MailException {

		emailService.sendEmail("testing");


	}
}
