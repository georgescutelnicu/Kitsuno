package com.kitsuno.service;

import com.kitsuno.dao.UserDAO;
import com.kitsuno.dto.UserDTO;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userDAO.save(user);
    }

    @Override
    public boolean registerUser(UserDTO userDTO, BindingResult bindingResult) {

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(true);
        save(user);

        System.out.println("User registered successfully.");

        return true;
    }

}
