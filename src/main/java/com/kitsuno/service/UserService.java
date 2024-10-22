package com.kitsuno.service;

import com.kitsuno.dto.UserDTO;
import com.kitsuno.entity.User;
import org.springframework.validation.BindingResult;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(User user);
    boolean registerUser(UserDTO userDTO, BindingResult bindingResult);
}
