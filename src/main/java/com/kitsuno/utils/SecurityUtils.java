package com.kitsuno.utils;

import com.kitsuno.entity.User;
import com.kitsuno.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtils {

    public static Optional<User> getAuthenticatedUser(UserService userService) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            return userService.findByUsername(username);
        }
        return Optional.empty();
    }
}
