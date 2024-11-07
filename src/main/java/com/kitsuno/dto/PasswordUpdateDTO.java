package com.kitsuno.dto;

import com.kitsuno.validation.ValidPassword;
import jakarta.validation.constraints.*;

public class PasswordUpdateDTO {

    @NotNull(message = "Old Password cannot be null")
    private String oldPassword;

    @ValidPassword
    @NotNull(message = "New Password cannot be null")
    @NotEmpty(message = "New Password is required")
    @Size(min = 6, max = 18, message = "New Password must be between 6 and 18 characters")
    private String newPassword;

    @NotEmpty(message = "Please Confirm the Password")
    private String confirmPassword;

    @AssertTrue(message = "New Password and Confirm Password must match")
    public boolean isPasswordConfirmed() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }

    public PasswordUpdateDTO() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
