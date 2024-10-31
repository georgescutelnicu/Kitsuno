package com.kitsuno.controller;

import com.kitsuno.entity.*;
import com.kitsuno.service.*;
import com.kitsuno.utils.Utils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.kitsuno.dto.FlashcardDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class WebController {
    private HiraganaService hiraganaService;
    private KatakanaService katakanaService;
    private KanjiService kanjiService;
    private UserService userService;
    private FlashcardService flashcardService;

    public WebController(HiraganaService hiraganaService, KatakanaService katakanaService, KanjiService kanjiService,
                         UserService userService, FlashcardService flashcardService) {
        this.hiraganaService = hiraganaService;
        this.katakanaService = katakanaService;
        this.kanjiService = kanjiService;
        this.userService = userService;
        this.flashcardService = flashcardService;
    }

    @GetMapping("/")
    public String showWelcome() {
        return "welcome";
    }

    @GetMapping("/home")
    public String showHome(Model model) {
        return "home";
    }

    @GetMapping("/hiragana")
    public String showHiragana(Model model) {
        List<Hiragana> hiraganaList = this.hiraganaService.findAll();
        List<Hiragana> formattedHiraganaList = Utils.formatKana(hiraganaList);
        model.addAttribute("hiraganaList", formattedHiraganaList);
        return "hiragana";
    }

    @GetMapping("/katakana")
    public String showKatakana(Model model) {
        List<Katakana> katakanaList = this.katakanaService.findAll();
        List<Katakana> formattedKatakanaList = Utils.formatKana(katakanaList);
        model.addAttribute("katakanaList", formattedKatakanaList);
        return "katakana";
    }

    @GetMapping("/kanji")
    public String showKanji(Model model) {
        Map<String, List<Kanji>> kanjiMap = this.kanjiService.findAllGroupedByCategory();
        model.addAttribute("kanjiMap", kanjiMap);
        return "kanji";
    }

    @GetMapping("/kanji/{character}")
    public String showKanjiDetails(@PathVariable("character") String character, Model model) {
        Kanji kanji = this.kanjiService.findKanjiByCharacter(character);
        FlashcardDTO flashcardDTO = new FlashcardDTO();
        model.addAttribute("kanji", kanji);
        model.addAttribute("flashcardDTO", flashcardDTO);

        Optional<User> authenticatedUser = Utils.getAuthenticatedUser(userService);

        if (authenticatedUser.isPresent()) {
            User user = authenticatedUser.get();
            model.addAttribute("userId", user.getId());

            Flashcard flashcardByUserAndKanji = flashcardService.getFlashcardByUserAndKanji(user.getId(), character);
            model.addAttribute("hasFlashcard", flashcardByUserAndKanji != null);

        } else {
            model.addAttribute("userId", null);
            model.addAttribute("hasFlashcard", false);
        }

        return "kanji-details";
    }
}
