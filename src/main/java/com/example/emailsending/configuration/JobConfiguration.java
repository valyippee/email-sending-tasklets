package com.example.emailsending.configuration;

import com.example.emailsending.email.EmailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class JobConfiguration {

    @Bean
    public EmailSender emailSender() {
        return new EmailSender(new JavaMailSenderImpl());
    }
}
