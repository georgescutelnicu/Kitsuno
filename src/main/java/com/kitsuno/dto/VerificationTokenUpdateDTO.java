package com.kitsuno.dto;

import com.kitsuno.validation.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class VerificationTokenUpdateDTO {

    @ValidEmail
    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email is required")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
