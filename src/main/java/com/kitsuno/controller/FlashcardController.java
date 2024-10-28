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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                                @RequestParam("kanjiCharacter") String kanjiCharacter,
                                BindingResult bindingResult,
                                Model model) {

        if (!flashcardDTO.hasAtLeastOneVocabPair()) {
            bindingResult.reject("error.flashcardDTO", "At least one vocabulary pair is required!");
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
}
