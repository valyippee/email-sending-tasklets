package com.example.emailsending.email;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSender {

    private final static Logger logger = LoggerFactory.getLogger(EmailSender.class);

    @Value("${to.email}")
    private String toEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private MessageGenerator messageGenerator;

    private final JavaMailSender javaMailSender;

    public EmailSender(JavaMailSender emailSender) {
        this.javaMailSender = emailSender;
    }

    /**
     * @return today's date as a string in yyyy/MM/dd + dayOfWeek format
     */
    public static String getTodayDate() {
        DateTime dateTime = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("EEEE");
        return dateTime.toString() + " " + formatter.print(dateTime);
    }

    /**
     * @return mime message
     * @throws MessagingException
     */
    public void sendEmail() throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        message.setFrom(fromEmail);
        message.setRecipients(Message.RecipientType.TO, toEmail);
        helper.setSubject("Stock price info: " + getTodayDate());
        helper.setText(messageGenerator.generateMessage(), true);
        javaMailSender.send(message);
        logger.info("email sent successfully");
    }
}
