package com.example.emailsending;

import com.example.emailsending.configuration.JobConfiguration;
import com.example.emailsending.dataReader.StockInfoGenerator;
import com.example.emailsending.email.EmailSender;
import com.example.emailsending.email.MessageGenerator;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;

@EnableEncryptableProperties
@SpringBootApplication
@EnableScheduling
public class EmailSendingApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(EmailSendingApplication.class);

//	@Autowired
//	private EmailSender emailSender;

//	@Autowired
//	private StockInfoGenerator infoGenerator;
//
//	@Autowired
//	private MessageGenerator messageGenerator;

//	@Autowired
//	JobLauncher jobLauncher;
//
//	@Autowired
//	Job job;

	public static void main(String[] args) {
		SpringApplication.run(EmailSendingApplication.class, args).close();
	}

	@Override
	public void run(String... args)  {

		logger.info("running the application");

		System.out.println("hello");




//		try {
//			infoGenerator.settingInfo();
//		} catch (ParseException e) {
//			logger.error("Error: " + e);
//		}
//
//
//		String message = messageGenerator.generateMessage();
//
//		try {
//			emailSender.sendEmail(message);
//		} catch (MessagingException e) {
//			logger.error("Error: " + e);
//		}

	}
}
