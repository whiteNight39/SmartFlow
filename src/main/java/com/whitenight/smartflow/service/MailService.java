package com.whitenight.smartflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationEmail(String toEmail, String token) {
        String subject = "Activate your SmartFlow account";
        String activationLink = "https://your-frontend.com/activate?token=" + token;

        String text = String.format(
                "Hi there,\n\nPlease use the token below to activate your account:\n\nToken: %s\n\n" +
                        "Or click the link below:\n%s\n\n" +
                        "This token will expire in 2 days.\n\nBest regards,\nSmartFlow Team",
                token, activationLink
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("smartflow.whitenight@gmail.com"); // must match sender in Brevo
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}