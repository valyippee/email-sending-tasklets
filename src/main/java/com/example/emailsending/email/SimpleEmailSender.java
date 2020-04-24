package com.example.emailsending.email;

import com.example.emailsending.dataReader.GenerateStockPriceInfo;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("simpleEmailSender")
public class SimpleEmailSender implements EmailService {

    @Value("${toEmail}")
    private String toEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender emailSender;

    public SimpleEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmail(String text, String subjectLine) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom(fromEmail);
        message.setText(text);
        emailSender.send(message);
    }
}
