package com.kitsuno.controller;

import com.kitsuno.dto.FlashcardDTO;
import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.User;
import com.kitsuno.service.FlashcardService;
import com.kitsuno.service.KanjiService;
import com.kitsuno.service.UserService;
import com.kitsuno.utils.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class FlashcardController {

    private final FlashcardService flashcardService;
    private final KanjiService kanjiService;
    private final UserService userService;

    @Autowired
    public FlashcardController(FlashcardService flashcardService, KanjiService kanjiService, UserService userService) {
        this.flashcardService = flashcardService;
        this.kanjiService = kanjiService;
        this.userService = userService;
    }

    @PostMapping("/kanji/{character}")
    public String saveOrUpdateFlashcard(@Valid @ModelAttribute("flashcardDTO") FlashcardDTO flashcardDTO,
                                        BindingResult bindingResult,
                                        @RequestParam("kanjiCharacter") String kanjiCharacter,
                                        @RequestParam("userId") int userId,
                                        Model model) {

        if (!flashcardDTO.hasAtLeastOneVocabPair()) {
            bindingResult.reject("error.flashcardDTO", "At least one vocabulary pair is required");
        }

        if (bindingResult.hasErrors()) {
            Kanji kanji = kanjiService.findKanjiByCharacter(kanjiCharacter);
            model.addAttribute("hasError", true);
            model.addAttribute("userId", userId);
            model.addAttribute("kanji", kanji);
            model.addAttribute("flashcardDTO", flashcardDTO);
            model.addAttribute("hasFlashcard",
                    flashcardService.getFlashcardByUserAndKanji(userId, kanjiCharacter) != null);
            return "kanji-details";
        }

        flashcardService.saveOrUpdateFlashcard(flashcardDTO, userId, kanjiCharacter);

        return "redirect:/flashcards";
    }

    @GetMapping("/flashcards")
    public String showFlashcards(Model model) {

        Optional<User> authenticatedUser = SecurityUtils.getAuthenticatedUser(userService);

            if (authenticatedUser.isPresent()) {
                User user = authenticatedUser.get();
                List<Flashcard> flashcardsList = flashcardService.getAllFlashcardsByUserId(user.getId());
                model.addAttribute("flashcardsList", flashcardsList);
            }

        return "flashcard";
    }

    @PostMapping("/flashcards/delete/{id}")
    public String deleteFlashcard(@PathVariable("id") int id) {
        flashcardService.deleteFlashcard(id);
        return "redirect:/flashcards";
    }
}
