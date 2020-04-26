package com.example.emailsending.email;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component("emailSender")
public class EmailSender {

    private final static Logger logger = LoggerFactory.getLogger(EmailSender.class);

    @Value("${toEmail}")
    private String toEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender emailSender;

    public EmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(String text, String subjectLine) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        logger.info("mime message created");
        message.setFrom(fromEmail);
        message.setRecipients(Message.RecipientType.TO, toEmail);
        helper.setSubject(subjectLine);
        helper.setText(text, true);
        emailSender.send(message);
        logger.info("email sent successfully");
    }
}
