package com.example.emailsending;

import com.example.emailsending.email.EmailSender;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@EnableScheduling
public class EmailSendingApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(EmailSendingApplication.class);

	@Autowired
	private EmailSender emailSender;

//	@Autowired
//	private StockInfoGenerator infoGenerator;
//
//	@Autowired
//	private MessageGenerator messageGenerator;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;


	public static void main(String[] args) {
		SpringApplication.run(EmailSendingApplication.class, args);

	}

//	@Scheduled(fixedRate = 1000)
//	public void launchJob() throws Exception {
//		JobParameters params = new JobParametersBuilder()
//								.addString("JobID", String.valueOf(System.currentTimeMillis()))
//								.toJobParameters();
//		jobLauncher.run(job, params);
//		System.out.println("in launch job method");
//	}

	@Override
	public void run(String... args) throws Exception {

		logger.info("running the application");

		JobParameters params = new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(job, params);


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
//			emailSender.generateEmail();
//		} catch (MessagingException e) {
//			logger.error("Error: " + e);
//		}

	}
}
