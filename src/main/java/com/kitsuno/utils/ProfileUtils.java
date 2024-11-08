package com.kitsuno.utils;

import com.kitsuno.dto.PasswordUpdateDTO;
import com.kitsuno.dto.UsernameUpdateDTO;
import com.kitsuno.entity.User;
import com.kitsuno.service.FlashcardService;
import org.springframework.ui.Model;

public class ProfileUtils {

    public static void populateProfileModel(Model model, User authenticatedUser, FlashcardService flashcardService,
                                            UsernameUpdateDTO usernameUpdateDTO, PasswordUpdateDTO passwordUpdateDTO) {
        model.addAttribute("user", authenticatedUser);
        model.addAttribute("countFlashcards", flashcardService.countFlashcardsByUserId(authenticatedUser.getId()));
        model.addAttribute("usernameUpdateDTO", usernameUpdateDTO);
        model.addAttribute("passwordUpdateDTO", passwordUpdateDTO);
    }
}
