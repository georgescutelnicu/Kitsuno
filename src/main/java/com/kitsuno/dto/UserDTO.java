package com.kitsuno.dto;

import com.kitsuno.validation.ValidEmail;
import com.kitsuno.validation.ValidPassword;
import jakarta.validation.constraints.*;

public class UserDTO {

    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username is required")
    @Size(min = 5, max = 14, message = "Username must be between 5 and 14 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain alphanumeric characters")
    private String username;

    @ValidEmail
    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email is required")
    private String email;

    @ValidPassword
    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password is required")
    @Size(min = 6, max = 18, message = "Password must be between 6 and 18 characters")
    private String password;

    @NotEmpty(message = "Please Confirm the Password")
    private String confirmPassword;

    @AssertTrue(message = "Passwords must match")
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(confirmPassword);
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
