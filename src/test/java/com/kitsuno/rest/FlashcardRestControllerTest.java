package com.kitsuno.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitsuno.dto.FlashcardDTO;
import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.User;
import com.kitsuno.exception.rest.FlashcardNotFoundException;
import com.kitsuno.service.FlashcardService;
import com.kitsuno.service.KanjiService;
import com.kitsuno.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlashcardRestController.class)
class FlashcardRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlashcardService flashcardService;

    @MockBean
    private UserService userService;

    @MockBean
    private KanjiService kanjiService;

    private User user1;
    private Kanji kanji1;
    private Kanji kanji2;
    private Flashcard flashcard1;
    private Flashcard flashcard2;
    private FlashcardDTO flashcardDTO;

    @BeforeEach
    void setup() {
        user1 = new User("user1", "user1@example.com", "hashedpassword1", true);
        user1.setId(1);
        user1.setApiKey("mock-api-key");

        kanji1 = new Kanji("日", "N5", "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                new String[]{"毎日 【まいにち】every day", "日光 【にっこう】sunlight", "翌日 【よくじつ】next day"},
                new String[]{"日 【ひ】day, days", "記念日 【きねんび】anniversary, memorial day"},
                "Time", 4);
        kanji1.setId(1);
        kanji2 = new Kanji("人", "N5", "person",
                new String[]{"ジン", "ニン"},
                new String[]{"ひと"},
                new String[]{"~人 【じん】 often used for citizenship after a country (American, Chinese, etc..)",
                        "友人 【ゆうじん】friend"},
                new String[]{"人 【ひと】 man, person, people", "いい人 【いいひと】 good-natured person, good person"},
                "People", 2);
        kanji2.setId(2);

        flashcard1 = new Flashcard(new String[]{"ひあさ morning sun"}, "Flashcard note for 日", kanji1, user1);
        flashcard1.setId(1);
        flashcard2 = new Flashcard(new String[]{"ひとびと people"}, "Flashcard note for 人", kanji2, user1);
        flashcard2.setId(2);

        flashcardDTO = new FlashcardDTO();
        flashcardDTO.setVocabWord1("ひあさ");
        flashcardDTO.setVocabMeaning1("morning sun");
        flashcardDTO.setVocabWord2("あさ");
        flashcardDTO.setVocabMeaning2("morning");
        flashcardDTO.setNotes("Flashcard note for 日");
        flashcardDTO.setKanjiId(1);
        flashcardDTO.setUserId(1);
    }

    @Test
    void testGetAllFlashcardsForUser() throws Exception {
        when(userService.findByApiKey("mock-api-key")).thenReturn(Optional.of(user1));
        when(flashcardService.getAllFlashcardsByUserId(1)).thenReturn(Arrays.asList(flashcard1, flashcard2));

        mockMvc.perform(get("/api/flashcards")
                        .header("API-KEY", "mock-api-key"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].kanjiCharacter").value("日"))
                .andExpect(jsonPath("$[1].kanjiCharacter").value("人"));
    }

    @Test
    void testGetFlashcardByUserAndId() throws Exception {
        when(userService.findByApiKey("mock-api-key")).thenReturn(Optional.of(user1));
        when(flashcardService.getFlashcardByUserAndId(1, 2)).thenReturn(flashcard2);

        mockMvc.perform(get("/api/flashcards/2").header("API-KEY", "mock-api-key"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.kanjiCharacter").value("人"))
                .andExpect(jsonPath("$.notes").value("Flashcard note for 人"));
    }

    @Test
    void testGetFlashcardByUserAndIdNotFound() {
        when(userService.findByApiKey("mock-api-key")).thenReturn(Optional.of(user1));
        when(flashcardService.getFlashcardByUserAndId(1, 999)).thenThrow(
                new FlashcardNotFoundException("Flashcard with id 999 was not found for the current user"));

        assertThatThrownBy(() -> flashcardService.getFlashcardByUserAndId(1, 999))
                .isInstanceOf(FlashcardNotFoundException.class)
                .hasMessage("Flashcard with id 999 was not found for the current user");
    }

    @Test
    void testCreateFlashcard() throws Exception {
        when(userService.findByApiKey("mock-api-key")).thenReturn(Optional.of(user1));
        when(kanjiService.findKanjiById(1)).thenReturn(kanji1);
        doNothing().when(flashcardService).saveOrUpdateFlashcard(flashcardDTO, user1.getId(), kanji1.getCharacter());

        mockMvc.perform(post("/api/flashcards")
                        .header("API-KEY", "mock-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flashcardDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateFlashcardAlreadyExists() throws Exception {
        when(userService.findByApiKey("mock-api-key")).thenReturn(Optional.of(user1));
        when(kanjiService.findKanjiById(1)).thenReturn(kanji1);
        when(flashcardService.getFlashcardByUserAndKanji(1, "日")).thenReturn(new Flashcard());

        mockMvc.perform(post("/api/flashcards")
                        .header("API-KEY", "mock-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flashcardDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.details").value("The flashcard already exists"));
    }

    @Test
    void testUpdateFlashcard() throws Exception {
        flashcardDTO.setNotes("Updated Flashcard note for 日");

        when(userService.findByApiKey("mock-api-key")).thenReturn(Optional.of(user1));
        when(kanjiService.findKanjiById(1)).thenReturn(kanji1);
        when(flashcardService.getFlashcardByUserAndId(user1.getId(), 1)).thenReturn(flashcard1);
        doNothing().when(flashcardService).saveOrUpdateFlashcard(flashcardDTO, user1.getId(), kanji1.getCharacter());

        mockMvc.perform(put("/api/flashcards/{flashcardId}", 1)
                        .header("API-KEY", "mock-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(flashcardDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteFlashcard() throws Exception {
        when(userService.findByApiKey("mock-api-key")).thenReturn(Optional.of(user1));
        when(flashcardService.getFlashcardByUserAndId(1, 1)).thenReturn(flashcard1);

        mockMvc.perform(delete("/api/flashcards/1").header("API-KEY", "mock-api-key"))
                .andExpect(status().isOk());

        verify(flashcardService, times(1)).deleteFlashcard(1);
    }
}
