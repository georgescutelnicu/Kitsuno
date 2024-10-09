package com.kitsuno.controller;

import com.kitsuno.entity.Hiragana;
import com.kitsuno.service.HiraganaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {
    private HiraganaService hiraganaService;

    public WebController(HiraganaService hiraganaService) {
        this.hiraganaService = hiraganaService;
    }

    @GetMapping("/")
    public String showWelcome() {
        return "welcome";
    }

    @GetMapping("/home")
    public String showHome() {
        return "home";
    }

    @GetMapping("hiragana")
    public String showHiragana(Model model) {
        List<Hiragana> hiraganaList = this.hiraganaService.findAll();
        model.addAttribute("hiraganaList", hiraganaList);
        System.out.println(hiraganaList);
        return "hiragana";
    }
}
