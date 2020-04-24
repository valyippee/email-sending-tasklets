package com.example.emailsending;

import com.example.emailsending.dataReader.GenerateStockPriceInfo;
import com.example.emailsending.dataReader.URLParser;
import com.example.emailsending.email.EmailService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;

@EnableEncryptableProperties
@SpringBootApplication
public class EmailSendingApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(EmailSendingApplication.class);

	@Autowired
	@Qualifier("mimeMessageSender")
	private EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(EmailSendingApplication.class, args).close();
	}

	@Override
	public void run(String... args) throws JSONException, ParseException, MessagingException {

		logger.info("inside run method");
		// obtaining stock price
		GenerateStockPriceInfo infoGenerator = new GenerateStockPriceInfo();
		String info = infoGenerator.generateInfo("VOO");
		String subjectLine = "VOO stock price info";

		emailService.sendEmail(info, subjectLine);

	}
}
