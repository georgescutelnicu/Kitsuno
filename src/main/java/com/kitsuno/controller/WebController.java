package com.kitsuno.controller;

import com.kitsuno.entity.Hiragana;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.Katakana;
import com.kitsuno.service.HiraganaService;
import com.kitsuno.service.KanjiService;
import com.kitsuno.service.KatakanaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class WebController {
    private HiraganaService hiraganaService;
    private KatakanaService katakanaService;
    private KanjiService kanjiService;

    public WebController(HiraganaService hiraganaService, KatakanaService katakanaService, KanjiService kanjiService) {
        this.hiraganaService = hiraganaService;
        this.katakanaService = katakanaService;
        this.kanjiService = kanjiService;
    }

    @GetMapping("/")
    public String showWelcome() {
        return "welcome";
    }

    @GetMapping("/home")
    public String showHome() {
        return "home";
    }

    @GetMapping("/hiragana")
    public String showHiragana(Model model) {
        List<Hiragana> hiraganaList = this.hiraganaService.findAll();
        model.addAttribute("hiraganaList", hiraganaList);
        return "hiragana";
    }

    @GetMapping("/katakana")
    public String showKatakana(Model model) {
        List<Katakana> katakanaList = this.katakanaService.findAll();
        model.addAttribute("katakanaList", katakanaList);
        return "katakana";
    }

    @GetMapping("/kanji")
    public String showKanji(Model model) {
        Map<String, List<Kanji>> kanjiMap = kanjiService.findAllGroupedByCategory();
        model.addAttribute("kanjiMap", kanjiMap);
        return "kanji";
    }

    @GetMapping("/kanji/{character}")
    public String showKanjiDetails(@PathVariable("character") String character, Model model) {
        Kanji kanji = kanjiService.findKanjiByCharacter(character);
        model.addAttribute("kanji", kanji);
        return "kanji-details";
    }
}
