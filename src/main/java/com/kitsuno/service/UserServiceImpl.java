package com.kitsuno.service;

import com.kitsuno.dao.UserDAO;
import com.kitsuno.dto.UserDTO;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
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

        return true;
    }

    @Override
    public void updateUsernameAndRefreshAuthentication(User user, String newUsername) {

        user.setUsername(newUsername);
        userDAO.save(user);

        UserDetails updatedUserDetails = userDetailsService.loadUserByUsername(newUsername);
        Authentication authentication = new UsernamePasswordAuthenticationToken(updatedUserDetails, updatedUserDetails.getPassword(), updatedUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void updatePassword(User user, String newPassword) {

        user.setPassword(passwordEncoder.encode(newPassword));
        userDAO.save(user);
    }
}
