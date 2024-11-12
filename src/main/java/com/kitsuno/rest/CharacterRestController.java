package com.kitsuno.rest;

import com.kitsuno.entity.Hiragana;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.Katakana;
import com.kitsuno.service.HiraganaService;
import com.kitsuno.service.KanjiService;
import com.kitsuno.service.KatakanaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CharacterRestController {

    private final HiraganaService hiraganaService;
    private final KatakanaService katakanaService;
    private final KanjiService kanjiService;

    @Autowired
    public CharacterRestController(HiraganaService hiraganaService, KatakanaService katakanaService,
                                   KanjiService kanjiService) {
        this.hiraganaService = hiraganaService;
        this.katakanaService = katakanaService;
        this.kanjiService = kanjiService;
    }

    @GetMapping("/hiragana")
    public List<Hiragana> getAllHiragana() {
        return hiraganaService.findAll();
    }

    @GetMapping("/hiragana/{character}")
    public Hiragana getHiraganaByCharacter(@PathVariable String character) {
        return hiraganaService.findByCharacter(character);
    }

    @GetMapping("/katakana")
    public List<Katakana> getAllKatakana() {
        return katakanaService.findAll();
    }

    @GetMapping("/katakana/{character}")
    public Katakana getKatakanaByCharacter(@PathVariable String character) {
        return katakanaService.findByCharacter(character);
    }

    @GetMapping("/kanji")
    public List<Kanji> getAllKanji() {
        return kanjiService.findAll();
    }

    @GetMapping("/kanji/{character}")
    public Kanji getKanjiByCharacter(@PathVariable String character) {
        return kanjiService.findKanjiByCharacter(character);
    }
}
