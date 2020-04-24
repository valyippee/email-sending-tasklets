package com.example.emailsending.email;

import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;

public interface EmailService {

    void sendEmail(String text, String subjectLine) throws MessagingException, ParseException;
}
