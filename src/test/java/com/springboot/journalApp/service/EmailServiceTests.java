package com.springboot.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        emailService.sendEmail("pj201999@gmail.com", "Test Java Mail Sender", "This is a test email sent from JavaMailSender.");
    }
}
