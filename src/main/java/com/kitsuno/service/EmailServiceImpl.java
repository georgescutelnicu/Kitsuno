package com.kitsuno.service;

import com.resend.Resend;
import com.resend.services.emails.model.SendEmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final Resend resend;

    public EmailServiceImpl(@Value("${resend.api.key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    @Override
    public void sendVerificationEmail(String to, String token) {
        String link = "https://kitsuno.help/verify?token=" + token;

        SendEmailRequest request = SendEmailRequest.builder()
                .from("no-reply@kitsuno.help")
                .to(new String[]{to})
                .subject("Kitsuno Email Verification")
                .html(
                        "<p>Hello,</p>" +
                                "<p>Thank you for registering at Kitsuno! Please verify your email address " +
                                "by clicking the link below:</p>" +
                                "<p><a href='" + link + "'>" + link + "</a></p>" +
                                "<p>If you did not sign up, you can safely ignore this email.</p>" +
                                "<p>Kitsuno Team!</p>"
                )
                .build();

        resend.emails().send(request);
    }
}