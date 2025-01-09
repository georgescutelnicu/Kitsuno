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
    User save(User user);
    boolean registerUser(UserDTO userDTO, BindingResult bindingResult);
    void updateUsernameAndRefreshAuthentication(User user, String newUsername);
    void updatePassword(User user, String newPassword);
    String generateApiKey();
    void updateApiKey(User user);
}
