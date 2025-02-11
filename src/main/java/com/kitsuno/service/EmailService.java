package com.kitsuno.service;

public interface EmailService {

    void sendVerificationEmail(String to, String token);
}
