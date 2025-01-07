package com.kitsuno.controller;

import com.kitsuno.dto.FlashcardDTO;
import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.User;
import com.kitsuno.service.FlashcardService;
import com.kitsuno.service.KanjiService;
import com.kitsuno.service.UserService;
import com.kitsuno.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(FlashcardController.class)
public class FlashcardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlashcardService flashcardService;

    @MockBean
    private KanjiService kanjiService;

    @MockBean
    private UserService userService;

    private Kanji kanji1;
    private User user1;
    private Flashcard flashcard1;

    @BeforeEach
    public void setup() {
        kanji1 = new Kanji("日", "N5", "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                new String[]{"毎日 【まいにち】every day", "日光 【にっこう】sunlight", "翌日 【よくじつ】next day"},
                new String[]{"日 【ひ】day, days", "記念日 【きねんび】anniversary, memorial day"},
                "Time", 4);

        user1 = new User("user1", "user1@example.com", "hashedpassword1", true);

        flashcard1 = new Flashcard(new String[]{"ひあさ morning sun"}, "Flashcard note for 日", kanji1, user1);
        flashcard1.setId(1);
    }

    @Test
    public void testSaveOrUpdateFlashcardSuccess() throws Exception {
        FlashcardDTO flashcardDTO = new FlashcardDTO();
        flashcardDTO.setVocabWord1("word1");
        flashcardDTO.setVocabMeaning1("meaning1");
        flashcardDTO.setVocabWord2("word2");
        flashcardDTO.setVocabMeaning2("meaning2");
        flashcardDTO.setKanjiId(0);

        String kanjiCharacter = "日";
        int userId = 1;

        when(kanjiService.findKanjiByCharacter(kanjiCharacter)).thenReturn(kanji1);

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user1));

        mockMvc.perform(post("/kanji/{character}", kanjiCharacter)
                        .with(csrf())
                        .flashAttr("flashcardDTO", flashcardDTO)
                        .param("kanjiCharacter", kanjiCharacter)
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/flashcards"));

        verify(flashcardService, times(1)).saveOrUpdateFlashcard(flashcardDTO, userId,
                kanjiCharacter);

        mockedStatic.close();
    }

    @Test
    public void testSaveOrUpdateFlashcardValidationFailure() throws Exception {
        FlashcardDTO flashcardDTO = new FlashcardDTO();

        String kanjiCharacter = "日";
        int userId = 1;

        when(kanjiService.findKanjiByCharacter(kanjiCharacter)).thenReturn(kanji1);

        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user1));

        mockMvc.perform(post("/kanji/{character}", kanjiCharacter)
                        .with(csrf())
                        .flashAttr("flashcardDTO", flashcardDTO)
                        .param("kanjiCharacter", kanjiCharacter)
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().isOk())
                .andExpect(view().name("kanji-details"))
                .andExpect(model().attributeExists("hasError"))
                .andExpect(model().attribute("hasFlashcard", false));

        verify(flashcardService, times(0)).saveOrUpdateFlashcard(flashcardDTO, userId,
                kanjiCharacter);

        mockedStatic.close();
    }

    @Test
    public void testShowFlashcardsAuthenticated() throws Exception {
        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.of(user1));

        Flashcard flashcard = new Flashcard();
        flashcard.setId(1);
        flashcard.setKanji(kanji1);
        List<Flashcard> flashcardsList = List.of(flashcard);
        when(flashcardService.getAllFlashcardsByUserId(user1.getId())).thenReturn(flashcardsList);

        mockMvc.perform(get("/flashcards")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("flashcard"))
                .andExpect(model().attribute("flashcardsList", flashcardsList));

        mockedStatic.close();
    }

    @Test
    public void testShowFlashcardsUnauthenticated() throws Exception {
        MockedStatic<SecurityUtils> mockedStatic = Mockito.mockStatic(SecurityUtils.class);
        mockedStatic.when(() -> SecurityUtils.getAuthenticatedUser(userService)).thenReturn(Optional.empty());

        mockMvc.perform(get("/flashcards")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("flashcard"))
                .andExpect(model().attributeDoesNotExist("flashcardsList"));

        mockedStatic.close();
    }

    @Test
    public void testDeleteFlashcard() throws Exception {
       when(flashcardService.getFlashcardById(flashcard1.getId())).thenReturn(flashcard1);

        mockMvc.perform(post("/flashcards/delete/{id}", flashcard1.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/flashcards"));

        verify(flashcardService, times(1)).deleteFlashcard(flashcard1.getId());
    }
}
