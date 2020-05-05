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

@SpringBootApplication
@EnableScheduling
public class EmailSendingApplication {

	private static final Logger logger = LoggerFactory.getLogger(EmailSendingApplication.class);

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	public static void main(String[] args) {
		SpringApplication.run(EmailSendingApplication.class, args);

	}

	/**
	 * job is launched every day from tuesday to saturday
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0 ? * TUE,WED,THU,FRI,SAT *")
	public void launchJob() {
		try {
			JobParameters params = new JobParametersBuilder()
					.addString("JobID", String.valueOf(System.currentTimeMillis()))
					.toJobParameters();
			logger.info("launching job");
			jobLauncher.run(job, params);
		} catch (Exception e) {
			logger.info("job failed to launch");
		}

	}

}
