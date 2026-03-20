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
        String link = "https://kitsuno-app.onrender.com/verify?token=" + token;

        SendEmailRequest request = SendEmailRequest.builder()
                .from("no-reply@kitsuno.help")
                .to(new String[]{to})
                .subject("Kitsuno Email Verification")
                .html(buildEmailHtml(link))
                .build();

        resend.emails().send(request);
    }

    private String buildEmailHtml(String link) {
        return """
                <div style="font-family: Arial, sans-serif; line-height: 1.6;">
                    <h2>Kitsuno Email Verification</h2>
                    <p>Hello,</p>
                    <p>Thanks for registering! Click the button below to verify your email:</p>
                    
                    <a href="%s" style="
                        display:inline-block;
                        padding:10px 20px;
                        color:#ffffff;
                        background-color:#007bff;
                        text-decoration:none;
                        border-radius:5px;
                        margin:15px 0;
                    ">
                        Verify Email
                    </a>

                    <p>If the button doesn’t work, use this link:</p>
                    <p><a href="%s">%s</a></p>

                    <p>If you didn’t sign up, ignore this email.</p>

                    <p>— Kitsuno Team</p>
                </div>
                """.formatted(link, link, link);
    }
}