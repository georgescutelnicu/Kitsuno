package com.kitsuno.service;

import com.kitsuno.dao.FlashcardDAO;
import com.kitsuno.dto.FlashcardDTO;
import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.User;
import com.kitsuno.exception.rest.FlashcardNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlashcardServiceImplTest {

    @Mock
    private FlashcardDAO flashcardDAO;

    @Mock
    private KanjiService kanjiService;

    @Mock
    private UserService userService;

    @InjectMocks
    private FlashcardServiceImpl flashcardService;

    private Kanji kanji1;
    private Kanji kanji2;
    private User user1;
    private User user2;
    private Flashcard flashcard1;
    private Flashcard flashcard2;
    private Flashcard flashcard3;

    @BeforeEach
    void setUp() {
        kanji1 = new Kanji("日", "N5", "day, sun, Japan, counter for days",
                new String[]{"ニチ", "ジツ"},
                new String[]{"ひ", "-び", "-か"},
                new String[]{"毎日 【まいにち】every day", "日光 【にっこう】sunlight", "翌日 【よくじつ】next day"},
                new String[]{"日 【ひ】day, days", "記念日 【きねんび】anniversary, memorial day"},
                "Time", 4);

        kanji2 = new Kanji("人", "N5", "person", new String[]{"ジン", "ニン"},
                new String[]{"ひと"},
                new String[]{"~人 【じん】 often used for citizenship after a country (American, Chinese, etc..)",
                        "友人 【ゆうじん】friend"},
                new String[]{"人 【ひと】 man, person, people", "いい人 【いいひと】 good-natured person, " +
                        "good person, lover"},
                "People", 2);

        user1 = new User("user1", "user1@example.com", "hashedpassword1", true);
        user2 = new User("user2", "user2@example.com", "hashedpassword2", true);

        flashcard1 = new Flashcard(new String[]{"ひあさ morning sun"}, "Flashcard note for 日", kanji1, user1);
        flashcard1.setId(1);
        flashcard2 = new Flashcard(new String[]{"ひとびと people"}, "Flashcard note for 人", kanji2, user1);
        flashcard2.setId(2);
        flashcard3 = new Flashcard(new String[]{"ひさし long time"}, "Flashcard note for 日", kanji1, user2);
        flashcard3.setId(3);
    }

    @Test
    void testSaveOrUpdateFlashcard() {
        FlashcardDTO flashcardDTO = new FlashcardDTO();
        flashcardDTO.setVocabWord1("ひあさ");
        flashcardDTO.setVocabMeaning1("morning sun");
        flashcardDTO.setVocabWord2("あさ");
        flashcardDTO.setVocabMeaning2("morning");
        flashcardDTO.setNotes("Flashcard note for 日");
        flashcardDTO.setKanjiId(1);
        flashcardDTO.setUserId(1);

        when(userService.findById(1)).thenReturn(Optional.of(user1));
        when(kanjiService.findKanjiById(1)).thenReturn(kanji1);
        doNothing().when(flashcardDAO).saveFlashcard(any(Flashcard.class));

        flashcardService.saveOrUpdateFlashcard(flashcardDTO, 1, "日");

        assertThat(flashcardDTO.getVocabularyAsArray()).containsExactly("ひあさ morning sun", "あさ morning");
        verify(flashcardDAO).saveFlashcard(any(Flashcard.class));
    }

    @Test
    void testGetFlashcardByIdExisting() {
        when(flashcardDAO.getFlashcardById(1)).thenReturn(flashcard1);

        Flashcard flashcard = flashcardService.getFlashcardById(1);

        assertThat(flashcard).isNotNull();
        assertThat(flashcard.getId()).isEqualTo(1);
        assertThat(flashcard.getKanji()).isEqualTo(kanji1);
        assertThat(flashcard.getUser()).isEqualTo(user1);
        assertThat(flashcard.getVocabulary()).containsExactly("ひあさ morning sun");
        assertThat(flashcard.getNotes()).isEqualTo("Flashcard note for 日");
    }

    @Test
    void testGetFlashcardByIdNonExisting() {
        when(flashcardDAO.getFlashcardById(999)).thenReturn(null);

        Flashcard flashcard = flashcardService.getFlashcardById(999);

        assertThat(flashcard).isNull();
    }

    @Test
    void testGetAllFlashcardsByUserId() {
        when(flashcardDAO.getAllFlashcardsByUserId(1)).thenReturn(Arrays.asList(flashcard1, flashcard2));

        List<Flashcard> returnedFlashcards = flashcardDAO.getAllFlashcardsByUserId(1);

        assertThat(returnedFlashcards).hasSize(2);
        assertThat(returnedFlashcards).extracting(Flashcard::getId)
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void testCountFlashcardsByUserId() {
        when(flashcardDAO.countFlashcardsByUserId(1)).thenReturn(2);

        int count = flashcardService.countFlashcardsByUserId(1);

        assertThat(count).isEqualTo(2);
    }

    @Test
    void testGetFlashcardByUserAndIdExisting() {
        when(flashcardDAO.getFlashcardByUserIdAndId(1, 1)).thenReturn(flashcard1);

        Flashcard result = flashcardService.getFlashcardByUserAndId(1, 1);

        assertThat(result).isNotNull();
        assertThat(result.getVocabulary()).containsExactly("ひあさ morning sun");
        assertThat(result.getNotes()).isEqualTo("Flashcard note for 日");
    }

    @Test
    void testGetFlashcardByUserAndIdNonExisting() {
        when(flashcardDAO.getFlashcardByUserIdAndId(1, 999)).thenReturn(null);

        assertThatThrownBy(() -> flashcardService.getFlashcardByUserAndId(1, 999))
                .isInstanceOf(FlashcardNotFoundException.class)
                .hasMessage("Flashcard with id 999 was not found for the current user");
    }

    @Test
    void testGetFlashcardByUserAndKanji() {
        when(flashcardDAO.getFlashcardByUserIdAndKanji(1, "日")).thenReturn(flashcard1);

        Flashcard result = flashcardService.getFlashcardByUserAndKanji(1, "日");

        assertThat(result).isNotNull();
        assertThat(result.getVocabulary()).containsExactly("ひあさ morning sun");
        assertThat(result.getNotes()).isEqualTo("Flashcard note for 日");
    }

    @Test
    void testDeleteFlashcardExisting() {
        when(flashcardDAO.getFlashcardById(1)).thenReturn(flashcard1);
        doNothing().when(flashcardDAO).deleteFlashcard(1);

        flashcardService.deleteFlashcard(1);

        verify(flashcardDAO).deleteFlashcard(1);
        verify(flashcardDAO).getFlashcardById(1);
    }

    @Test
    void testDeleteFlashcardNonExisting() {
        when(flashcardDAO.getFlashcardById(999)).thenReturn(null);

        assertThatThrownBy(() -> flashcardService.deleteFlashcard(999))
                .isInstanceOf(FlashcardNotFoundException.class)
                .hasMessage("Flashcard with id 999 was not found for the current user");
    }
}
