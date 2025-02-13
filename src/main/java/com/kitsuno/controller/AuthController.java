package com.kitsuno.controller;

import com.kitsuno.dto.UserDTO;
import com.kitsuno.dto.VerificationTokenUpdateDTO;
import com.kitsuno.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.UUID;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new UserDTO());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (userService.findByUsername(userDTO.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.username",
                    "Username already exists");
        }

        if (userService.findByEmail(userDTO.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.email",
                    "Email already exists");
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.userDTO",
                    "Password and Confirm Password must match");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
            modelAndView.addObject("user", userDTO);
            return modelAndView;
        }
        userService.registerUser(userDTO, bindingResult);

        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @GetMapping("/verify")
    public ModelAndView verify(@RequestParam String token) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("verify");
        if (userService.verifyUser(token)) {
            modelAndView.addObject("message", "Congratulations, " +
                    "your account has been verified!");
        } else {
            modelAndView.addObject("message", "Sorry, we couldn't verify your account. " +
                    "It may already be verified or the token might be invalid.");
        }
        return modelAndView;
    }

    @GetMapping("/resend-verification")
    public ModelAndView showResendVerificationForm() {
        ModelAndView modelAndView = new ModelAndView("resend-verification");
        modelAndView.addObject("verificationTokenUpdateDTO", new VerificationTokenUpdateDTO());
        return modelAndView;
    }

    @PostMapping("/resend-verification")
    public ModelAndView resendVerificationEmail(@ModelAttribute("verificationTokenUpdateDTO")
                                                    @Valid VerificationTokenUpdateDTO dto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("resend-verification");

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        userService.updateVerificationToken(dto.getEmail());
        modelAndView.addObject("message", "If the email is linked to an unverified" +
                " account, a new verification email has been sent.");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return new ModelAndView("redirect:/logout");
        }
        return new ModelAndView("login");
    }

    @GetMapping("/logout")
    public ModelAndView showLogoutForm() {
        return new ModelAndView("logout");
    }
}
