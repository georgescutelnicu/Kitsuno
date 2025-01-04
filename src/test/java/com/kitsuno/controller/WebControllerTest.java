package com.kitsuno.controller;

import com.kitsuno.entity.*;
import com.kitsuno.service.*;
import com.kitsuno.utils.KanaUtils;
import com.kitsuno.utils.SecurityUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WebController.class)
public class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HiraganaService hiraganaService;

    @MockBean
    private KatakanaService katakanaService;

    @MockBean
    private KanjiService kanjiService;

    @MockBean
    private UserService userService;

    @MockBean
    private FlashcardService flashcardService;

    @MockBean
    private ApiService apiService;

    @Test
    public void testShowWelcome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
    }

    @Test
    public void testShowVocabulary() throws Exception {
        mockMvc.perform(get("/vocabulary")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("vocabulary"));
    }

    @Test
    public void testGetSentenceExamples() throws Exception {
        String query = "tree";

        Map<String, Object> apiResponse = Map.of(
                "sentences", List.of(
                        Map.of(
                                "content", "あの木、大きいね。",
                                "furigana", "あの[木|き]、[大|おお]きいね。",
                                "translation", "That tree is big.",
                                "language", "English",
                                "eng", "That tree is big."
                        )
                )
        );

        when(apiService.getSentenceExamples(query)).thenReturn(apiResponse);

        mockMvc.perform(post("/vocabulary")
                        .param("query", query)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("vocabulary"))
                .andExpect(model().attribute("query", query))
                .andExpect(model().attribute("apiResponse", apiResponse));
    }

    @Test
    public void testShowHiragana() throws Exception {
        List<Hiragana> hiraganaList = List.of(new Hiragana(), new Hiragana());
        Map<String, List<Map<String, String>>> hiraganaVariants = KanaUtils.getHiraganaVariants();

        when(hiraganaService.findAll()).thenReturn(hiraganaList);

        mockMvc.perform(get("/hiragana"))
                .andExpect(status().isOk())
                .andExpect(view().name("hiragana"))
                .andExpect(model().attribute("hiraganaList", hiraganaList))
                .andExpect(model().attribute("hiraganaVariants", hiraganaVariants));
    }

    @Test
    public void testShowPracticeHiragana() throws Exception {
        mockMvc.perform(get("/practice-hiragana"))
                .andExpect(status().isOk())
                .andExpect(view().name("hiragana-practice"));
    }

    @Test
    public void testShowKatakana() throws Exception {
        List<Katakana> katakanaList = List.of(new Katakana(), new Katakana());
        Map<String, List<Map<String, String>>> katakanaVariants = KanaUtils.getKatakanaVariants();

        when(katakanaService.findAll()).thenReturn(katakanaList);

        mockMvc.perform(get("/katakana"))
                .andExpect(status().isOk())
                .andExpect(view().name("katakana"))
                .andExpect(model().attribute("katakanaList", katakanaList))
                .andExpect(model().attribute("katakanaVariants", katakanaVariants));
    }

    @Test
    public void testShowPracticeKatakana() throws Exception {
        mockMvc.perform(get("/practice-katakana"))
                .andExpect(status().isOk())
                .andExpect(view().name("katakana-practice"));
    }

    @Test
    public void testShowKanji() throws Exception {
        Map<String, List<Kanji>> kanjiMap = Map.of("category", List.of(new Kanji()));

        when(kanjiService.findAllGroupedByCategory()).thenReturn(kanjiMap);

        mockMvc.perform(get("/kanji"))
                .andExpect(status().isOk())
                .andExpect(view().name("kanji"))
                .andExpect(model().attribute("kanjiMap", kanjiMap));
    }

    @Test
    public void testShowKanjiDetailsAuthenticatedUserWithFlashcard() throws Exception {
        Kanji kanji = new Kanji("日", "N5", "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                new String[]{"毎日 【まいにち】every day", "日光 【にっこう】sunlight", "翌日 【よくじつ】next day"},
                new String[]{"日 【ひ】day, days", "記念日 【きねんび】anniversary, memorial day"},
                "Time", 4);
        User user = new User();
        String character = "日";

        when(kanjiService.findKanjiByCharacter(character)).thenReturn(kanji);

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user));

        when(flashcardService.getFlashcardByUserAndKanji(user.getId(), character)).thenReturn(new Flashcard());

        mockMvc.perform(get("/kanji/{character}", character))
                .andExpect(status().isOk())
                .andExpect(view().name("kanji-details"))
                .andExpect(model().attributeExists("kanji"))
                .andExpect(model().attributeExists("flashcardDTO"))
                .andExpect(model().attribute("userId", user.getId()))
                .andExpect(model().attribute("hasFlashcard", true));

        mockedStatic.close();
    }

    @Test
    public void testShowKanjiDetailsAuthenticatedUserWithoutFlashcard() throws Exception {
        Kanji kanji = new Kanji("日", "N5", "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                new String[]{"毎日 【まいにち】every day", "日光 【にっこう】sunlight", "翌日 【よくじつ】next day"},
                new String[]{"日 【ひ】day, days", "記念日 【きねんび】anniversary, memorial day"},
                "Time", 4);
        User user = new User();
        String character = "日";

        when(kanjiService.findKanjiByCharacter(character)).thenReturn(kanji);

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user));

        when(flashcardService.getFlashcardByUserAndKanji(user.getId(), character)).thenReturn(null);

        mockMvc.perform(get("/kanji/{character}", character))
                .andExpect(status().isOk())
                .andExpect(view().name("kanji-details"))
                .andExpect(model().attributeExists("kanji"))
                .andExpect(model().attributeExists("flashcardDTO"))
                .andExpect(model().attribute("userId", user.getId()))
                .andExpect(model().attribute("hasFlashcard", false));

        mockedStatic.close();
    }

    @Test
    public void testShowKanjiDetailsUnauthenticatedUser() throws Exception {
        Kanji kanji = new Kanji("日", "N5", "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                new String[]{"毎日 【まいにち】every day", "日光 【にっこう】sunlight", "翌日 【よくじつ】next day"},
                new String[]{"日 【ひ】day, days", "記念日 【きねんび】anniversary, memorial day"},
                "Time", 4);
        String character = "日";

        when(kanjiService.findKanjiByCharacter(character)).thenReturn(kanji);

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.empty());

        mockMvc.perform(get("/kanji/{character}", character))
                .andExpect(status().isOk())
                .andExpect(view().name("kanji-details"))
                .andExpect(model().attributeExists("kanji"))
                .andExpect(model().attributeExists("flashcardDTO"))
                .andExpect(model().attribute("userId",  Matchers.nullValue()))
                .andExpect(model().attribute("hasFlashcard", false));

        mockedStatic.close();
    }

    @Test
    public void testShowResources() throws Exception {
        mockMvc.perform(get("/resources"))
                .andExpect(status().isOk())
                .andExpect(view().name("resources"));
    }

    @Test
    public void testShowAbout() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    @Test
    public void testShowPrivacyPolicy() throws Exception {
        mockMvc.perform(get("/privacypolicy"))
                .andExpect(status().isOk())
                .andExpect(view().name("privacypolicy"));
    }

    @Test
    public void testShowToS() throws Exception {
        mockMvc.perform(get("/tos"))
                .andExpect(status().isOk())
                .andExpect(view().name("tos"));
    }
}
