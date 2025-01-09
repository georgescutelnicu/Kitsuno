package com.kitsuno.controller;

import com.kitsuno.entity.*;
import com.kitsuno.service.*;
import com.kitsuno.utils.KanaUtils;
import com.kitsuno.utils.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.kitsuno.dto.FlashcardDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class WebController {

    private final ApiService apiService;
    private final HiraganaService hiraganaService;
    private final KatakanaService katakanaService;
    private final KanjiService kanjiService;
    private final UserService userService;
    private final FlashcardService flashcardService;

    public WebController(HiraganaService hiraganaService, KatakanaService katakanaService, KanjiService kanjiService,
                         UserService userService, FlashcardService flashcardService, ApiService apiService) {
        this.hiraganaService = hiraganaService;
        this.katakanaService = katakanaService;
        this.kanjiService = kanjiService;
        this.userService = userService;
        this.flashcardService = flashcardService;
        this.apiService = apiService;
    }

    @GetMapping("/")
    public String showWelcome() {
        return "welcome";
    }

    @GetMapping("/vocabulary")
    public String showHome() {
        return "vocabulary";
    }

    @PostMapping("/vocabulary")
    public String getSentenceExamples(@RequestParam("query") String query, Model model) {
        Map<String, Object> response = apiService.getSentenceExamples(query);

        model.addAttribute("query", query);
        model.addAttribute("apiResponse", response);

        return "vocabulary";
    }

    @GetMapping("/hiragana")
    public String showHiragana(Model model) {
        List<Hiragana> hiraganaList = this.hiraganaService.findAll();
        List<Hiragana> formattedHiraganaList = KanaUtils.formatKana(hiraganaList);
        Map<String, List<Map<String, String>>> hiraganaVariants = KanaUtils.getHiraganaVariants();
        model.addAttribute("hiraganaList", formattedHiraganaList);
        model.addAttribute("hiraganaVariants", hiraganaVariants);
        return "hiragana";
    }

    @GetMapping("/practice-hiragana")
    public String showPracticeHiragana() {
        return "hiragana-practice";
    }

    @GetMapping("/katakana")
    public String showKatakana(Model model) {
        List<Katakana> katakanaList = this.katakanaService.findAll();
        List<Katakana> formattedKatakanaList = KanaUtils.formatKana(katakanaList);
        Map<String, List<Map<String, String>>> katakanaVariants = KanaUtils.getKatakanaVariants();
        model.addAttribute("katakanaList", formattedKatakanaList);
        model.addAttribute("katakanaVariants", katakanaVariants);
        return "katakana";
    }

    @GetMapping("/practice-katakana")
    public String showPracticeKatakana() {
        return "katakana-practice";
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

        Optional<User> authenticatedUser = SecurityUtils.getAuthenticatedUser(userService);

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

    @GetMapping("/resources")
    public String showResources() {
        return "resources";
    }

    @GetMapping("/about")
    public String showAbout() {
        return "about";
    }

    @GetMapping("/privacypolicy")
    public String showPrivacyPolicy() {
        return "privacypolicy";
    }

    @GetMapping("/tos")
    public String showToS() {
        return "tos";
    }
}
