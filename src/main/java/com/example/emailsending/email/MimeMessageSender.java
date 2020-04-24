package com.example.emailsending.email;

import com.example.emailsending.dataReader.GenerateStockPriceInfo;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component("mimeMessageSender")
public class MimeMessageSender implements EmailService{

    private final static Logger logger = LoggerFactory.getLogger(MimeMessageSender.class);

    @Value("${toEmail}")
    private String toEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender emailSender;

    public MimeMessageSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmail(String text, String subjectLine) throws MessagingException, ParseException {
        MimeMessage message = emailSender.createMimeMessage();
        logger.info("mime message created");
        message.setFrom(fromEmail);
        message.setRecipients(Message.RecipientType.TO, toEmail);
        message.setSubject(subjectLine);
        message.setText(text);
        emailSender.send(message);
        logger.info("email sent successfully");
    }
}
