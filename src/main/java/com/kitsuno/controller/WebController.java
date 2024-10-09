package com.kitsuno.controller;

import com.kitsuno.entity.Hiragana;
import com.kitsuno.entity.Katakana;
import com.kitsuno.service.HiraganaService;
import com.kitsuno.service.KatakanaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {
    private HiraganaService hiraganaService;
    private KatakanaService katakanaService;

    public WebController(HiraganaService hiraganaService, KatakanaService katakanaService) {
        this.hiraganaService = hiraganaService;
        this.katakanaService = katakanaService;
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
}
