package com.kitsuno.rest;

import com.kitsuno.entity.Hiragana;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.Katakana;
import com.kitsuno.service.HiraganaService;
import com.kitsuno.service.KanjiService;
import com.kitsuno.service.KatakanaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CharacterRestController.class)
class CharacterRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HiraganaService hiraganaService;

    @MockBean
    private KatakanaService katakanaService;

    @MockBean
    private KanjiService kanjiService;

    private Hiragana hiragana1;
    private Hiragana hiragana2;
    private Katakana katakana1;
    private Katakana katakana2;
    private Kanji kanji1;
    private Kanji kanji2;

    @BeforeEach
    void setup() {
        hiragana1 = new Hiragana("あ", "a", "audio_a.mp3", "mnemonic_a", "story_a",
                "stroke_order_a.png");
        hiragana2 = new Hiragana("い", "i", "audio_i.mp3", "mnemonic_i", "story_i",
                "stroke_order_i.png");

        katakana1 = new Katakana("ア", "a", "audio_a.mp3", "mnemonic_a", "story_a",
                "stroke_order_a.png");
        katakana2 = new Katakana("イ", "i", "audio_i.mp3", "mnemonic_i", "story_i",
                "stroke_order_i.png");

        kanji1 = new Kanji("日", "N5", "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                new String[]{"毎日 【まいにち】every day", "日光 【にっこう】sunlight", "翌日 【よくじつ】next day"},
                new String[]{"日 【ひ】day, days", "記念日 【きねんび】anniversary, memorial day"},
                "Time", 4);

        kanji2 = new Kanji("人", "N5", "person",
                new String[]{"ジン", "ニン"},
                new String[]{"ひと"},
                new String[]{"~人 【じん】 often used for citizenship after a country (American, Chinese, etc..)",
                        "友人 【ゆうじん】friend"},
                new String[]{"人 【ひと】 man, person, people", "いい人 【いいひと】 good-natured person, good person"},
                "People", 2);
    }

    @Test
    void testGetAllHiragana() throws Exception {
        when(hiraganaService.findAll()).thenReturn(Arrays.asList(hiragana1, hiragana2));

        mockMvc.perform(get("/api/hiragana").header("API-KEY", "mock-api-key"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].character").value("あ"))
                .andExpect(jsonPath("$[1].character").value("い"));
    }

    @Test
    void testGetHiraganaByCharacter() throws Exception {
        when(hiraganaService.findByCharacter("a")).thenReturn(hiragana1);

        mockMvc.perform(get("/api/hiragana/a").header("API-KEY", "mock-api-key"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.character").value("あ"));
    }

    @Test
    void testGetAllKatakana() throws Exception {
        when(katakanaService.findAll()).thenReturn(Arrays.asList(katakana1, katakana2));

        mockMvc.perform(get("/api/katakana").header("API-KEY", "mock-api-key"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].character").value("ア"))
                .andExpect(jsonPath("$[1].character").value("イ"));
    }

    @Test
    void testGetKatakanaByCharacter() throws Exception {
        when(katakanaService.findByCharacter("a")).thenReturn(katakana1);

        mockMvc.perform(get("/api/katakana/a").header("API-KEY", "mock-api-key"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.character").value("ア"));
    }

    @Test
    void testGetAllKanji() throws Exception {
        when(kanjiService.findAll()).thenReturn(Arrays.asList(kanji1, kanji2));

        mockMvc.perform(get("/api/kanji").header("API-KEY", "mock-api-key"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].character").value("日"))
                .andExpect(jsonPath("$[1].character").value("人"));
    }

    @Test
    void testGetKanjiById() throws Exception {
        when(kanjiService.findKanjiById(1)).thenReturn(kanji1);

        mockMvc.perform(get("/api/kanji/1").header("API-KEY", "mock-api-key"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.character").value("日"))
                .andExpect(jsonPath("$.onyomiVocab").isArray())
                .andExpect(jsonPath("$.onyomiVocab[0]").value("毎日 【まいにち】every day"));
    }
}
