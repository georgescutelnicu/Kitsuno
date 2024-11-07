package com.kitsuno.dto;

import jakarta.validation.constraints.*;

public class UsernameUpdateDTO {

    @NotNull(message = "New Username cannot be null")
    @NotEmpty(message = "New Username is required")
    @Size(min = 6, max = 18, message = "New Username must be between 6 and 18 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "New Username can only contain alphanumeric characters")
    private String newUsername;

    public UsernameUpdateDTO() {
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }
}
