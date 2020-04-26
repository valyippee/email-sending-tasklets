package com.example.emailsending;

import com.example.emailsending.dataReader.StockInfoGenerator;
import com.example.emailsending.email.EmailSender;
import com.example.emailsending.email.MessageGenerator;
import com.example.emailsending.model.Data;
import com.example.emailsending.model.StockData;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;

@EnableEncryptableProperties
@SpringBootApplication
public class EmailSendingApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(EmailSendingApplication.class);

	@Autowired
	private EmailSender emailSender;

	@Value("${stocks}")
	private String[] stocks;

	@Autowired
	private StockInfoGenerator infoGenerator;

	@Autowired
	private MessageGenerator messageGenerator;

	@Autowired
	private Data data;

	public static void main(String[] args) {
		SpringApplication.run(EmailSendingApplication.class, args).close();
	}

	@Override
	public void run(String... args) throws JSONException, ParseException, MessagingException {

		logger.info("running the application");

		infoGenerator.generateInfo();

		String message = messageGenerator.generateMessage(data);

		String subjectLine = "Stock price info: " + infoGenerator.getDate();

		emailSender.sendEmail(message, subjectLine);

	}
}
