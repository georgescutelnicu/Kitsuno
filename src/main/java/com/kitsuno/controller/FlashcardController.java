package com.kitsuno.controller;

import com.kitsuno.dto.FlashcardDTO;
import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.User;
import com.kitsuno.service.FlashcardService;
import com.kitsuno.service.KanjiService;
import com.kitsuno.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class FlashcardController {

    private final FlashcardService flashcardService;
    private KanjiService kanjiService;
    private UserService userService;

    @Autowired
    public FlashcardController(FlashcardService flashcardService, KanjiService kanjiService, UserService userService) {
        this.flashcardService = flashcardService;
        this.kanjiService = kanjiService;
        this.userService = userService;
    }

    @PostMapping("/kanji/{character}")
    public String saveFlashcard(@Valid @ModelAttribute("flashcardDTO") FlashcardDTO flashcardDTO,
                                BindingResult bindingResult,
                                @RequestParam("kanjiCharacter") String kanjiCharacter,
                                Model model) {

        if (!flashcardDTO.hasAtLeastOneVocabPair()) {
            bindingResult.reject("error.flashcardDTO", "At least one vocabulary pair is required");
        }

        if (bindingResult.hasErrors()) {
            Kanji kanji = kanjiService.findKanjiByCharacter(kanjiCharacter);

            model.addAttribute("hasError", true);
            model.addAttribute("kanji", kanji);
            model.addAttribute("flashcardDTO", flashcardDTO);

            return "kanji-details";
        }

        Flashcard flashcard = new Flashcard();

        Kanji kanji = kanjiService.findKanjiById(flashcardDTO.getKanjiId());
        Optional<User> user = userService.findById(flashcardDTO.getUserId());
        flashcard.setKanji(kanji);
        flashcard.setUser(user.get());

        String[] vocabArray = flashcardDTO.getVocabularyAsArray();
        flashcard.setVocabulary(vocabArray);

        flashcard.setNotes(flashcardDTO.getNotes());

        flashcardService.saveFlashcard(flashcard);

        return "redirect:/flashcards";
    }

    @GetMapping("/flashcards")
    public String showFlashcards(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            Optional<User> userOptional = userService.findByUsername(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                List<Flashcard> flashcardsList = flashcardService.getAllFlashcardsByUserId(user.getId());
                model.addAttribute("flashcardsList", flashcardsList);
            }
        }

        return "flashcard";
    }

    @PostMapping("/flashcards/delete/{id}")
    public String deleteFlashcard(@PathVariable("id") int id) {
        flashcardService.deleteFlashcard(id);
        return "redirect:/flashcards";
    }
}
