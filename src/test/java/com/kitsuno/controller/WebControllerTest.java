package com.kitsuno.controller;

import com.kitsuno.entity.*;
import com.kitsuno.service.*;
import com.kitsuno.utils.KanaUtils;
import com.kitsuno.utils.SecurityUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private ParticleService particleService;

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
        Map<Integer, List<Kanji>> kanjiMap = Map.of(1, List.of(new Kanji()));

        when(kanjiService.findAllGroupedByGrade()).thenReturn(kanjiMap);

        mockMvc.perform(get("/kanji"))
                .andExpect(status().isOk())
                .andExpect(view().name("kanji"))
                .andExpect(model().attribute("kanjiMap", kanjiMap));
    }

    @Test
    public void testShowKanjiDetailsAuthenticatedUserWithFlashcard() throws Exception {
        Kanji kanji = new Kanji("日", 4, 1, "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                "<svg>...</svg>",
                new String[]{"毎日", "日光"});
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
        Kanji kanji = new Kanji("日", 4, 1, "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                "<svg>...</svg>",
                new String[]{"毎日", "日光"});
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
        Kanji kanji = new Kanji("日", 4, 1, "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                "<svg>...</svg>",
                new String[]{"毎日", "日光"});
        String character = "日";

        when(kanjiService.findKanjiByCharacter(character)).thenReturn(kanji);

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.empty());

        mockMvc.perform(get("/kanji/{character}", character))
                .andExpect(status().isOk())
                .andExpect(view().name("kanji-details"))
                .andExpect(model().attributeExists("kanji"))
                .andExpect(model().attributeExists("flashcardDTO"))
                .andExpect(model().attribute("userId", Matchers.nullValue()))
                .andExpect(model().attribute("hasFlashcard", false));

        mockedStatic.close();
    }

    @Test
    public void testShowParticles() throws Exception {
        Particle mockParticle = new Particle(
                "は (wa)",
                "topic marker",
                "は (wa) follows the topic the speaker wants to talk about.",
                "[ A ] wa [ B ] desu. [ A ] is [ B ].",
                "Kinō wa ame datta 【昨日は雨だった】 It was rainy yesterday"
        );

        List<Particle> particlesList = List.of(mockParticle);
        when(particleService.findAll()).thenReturn(particlesList);

        mockMvc.perform(get("/particles"))
                .andExpect(status().isOk())
                .andExpect(view().name("particles"))
                .andExpect(model().attribute("particlesList", particlesList));
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