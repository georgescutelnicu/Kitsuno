package com.kitsuno.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${smtp_email}")
    private String fromEmail;

    public void sendVerificationEmail(String to, String token) {
        String link = "https://kitsuno-app.onrender.com/verify?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Kitsuno Email Verification");
        message.setText("Hello,\n\n"
                + "Thank you for registering at Kitsuno! Please verify your " +
                "email address by clicking the link below:\n\n"
                + link + "\n\n"
                + "If you did not sign up, you can safely ignore this email.\n\n"
                + "Best regards,\n"
                + "Kitsuno Team");

        mailSender.send(message);
    }
}
