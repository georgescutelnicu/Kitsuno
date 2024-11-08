package com.kitsuno.controller;

import com.kitsuno.dto.PasswordUpdateDTO;
import com.kitsuno.dto.UsernameUpdateDTO;
import com.kitsuno.entity.User;
import com.kitsuno.service.FlashcardService;
import com.kitsuno.service.UserService;
import com.kitsuno.utils.ProfileUtils;
import com.kitsuno.utils.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ProfileController {

    private UserService userService;
    private FlashcardService flashcardService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ProfileController(UserService userService, FlashcardService flashcardService,
                             BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.flashcardService = flashcardService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Optional<User> authenticatedUser = SecurityUtils.getAuthenticatedUser(userService);

        ProfileUtils.populateProfileModel(model, authenticatedUser.get(), flashcardService,
                new UsernameUpdateDTO(), new PasswordUpdateDTO());

        return "profile";
    }

    @PostMapping("/updateUsername")
    public String updateUsername(@Valid @ModelAttribute("usernameUpdateDTO") UsernameUpdateDTO usernameUpdateDTO,
                                 BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                                 PasswordUpdateDTO passwordUpdateDTO) {
        Optional<User> authenticatedUser = SecurityUtils.getAuthenticatedUser(userService);

        if (userService.findByUsername(usernameUpdateDTO.getNewUsername()).isPresent()) {
            bindingResult.rejectValue("newUsername", "error.usernameUpdateDTO",
                    "Username already exists");
        }

        if (bindingResult.hasErrors()) {
            ProfileUtils.populateProfileModel(model, authenticatedUser.get(), flashcardService,
                    usernameUpdateDTO, passwordUpdateDTO);
            return "profile";
        }

        userService.updateUsernameAndRefreshAuthentication(authenticatedUser.get(),
                usernameUpdateDTO.getNewUsername());

        redirectAttributes.addFlashAttribute("usernameSuccess",
                "Username was successfully updated");

        return "redirect:/profile";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@Valid @ModelAttribute("passwordUpdateDTO") PasswordUpdateDTO passwordUpdateDTO,
                                 BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                                 UsernameUpdateDTO usernameUpdateDTO) {
        Optional<User> authenticatedUser = SecurityUtils.getAuthenticatedUser(userService);

        if (!passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), authenticatedUser.get().getPassword())) {
            bindingResult.rejectValue("oldPassword", "error.passwordUpdateDTO",
                    "Old password is incorrect");
        }

        if (!passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.passwordUpdateDTO",
                    "New Password and Confirm Password must match");
        }

        if (bindingResult.hasErrors()) {
            ProfileUtils.populateProfileModel(model, authenticatedUser.get(), flashcardService,
                    usernameUpdateDTO, passwordUpdateDTO);
            return "profile";
        }

        userService.updatePassword(authenticatedUser.get(), passwordUpdateDTO.getNewPassword());

        redirectAttributes.addFlashAttribute("passwordSuccess",
                "Password was successfully updated");

        return "redirect:/profile";
    }
}
