package com.kitsuno.dao;

import com.kitsuno.entity.User;

import java.util.Optional;


public interface UserDAO {

    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByApiKey(String apiKey);
    Optional<User> findByVerificationToken(String token);
    User save(User user);
}
