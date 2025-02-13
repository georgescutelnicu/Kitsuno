package com.kitsuno.service;

import com.kitsuno.dto.UserDTO;
import com.kitsuno.entity.User;
import org.springframework.validation.BindingResult;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByApiKey(String apiKey);
    Optional<User> findByVerificationToken(String token);
    User save(User user);
    boolean registerUser(UserDTO userDTO, BindingResult bindingResult);
    boolean verifyUser(String token);
    void updateVerificationToken(String email);
    void updateUsernameAndRefreshAuthentication(User user, String newUsername);
    void updatePassword(User user, String newPassword);
    String generateApiKey();
    void updateApiKey(User user);
}
