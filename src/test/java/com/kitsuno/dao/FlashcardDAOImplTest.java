package com.kitsuno.dao;

import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = "com.kitsuno.dao")
@Sql(scripts = "/flashcard_data.sql")
class FlashcardDAOImplTest {

    @Autowired
    private FlashcardDAOImpl flashcardDAO;

    @Autowired
    private KanjiDAOImpl kanjiDAO;

    @Autowired
    private UserDAOImpl userDAO;

    @Test
    void testSaveFlashcard() {
        Kanji kanji = kanjiDAO.findKanjiById(1);
        User user = userDAO.findById(1).get();

        Flashcard flashcard = new Flashcard(
                new String[]{"ひあさ morning sun"},
                "Flashcard note for 日",
                kanji,
                user
        );

        flashcardDAO.saveFlashcard(flashcard);
        Flashcard savedFlashcard = flashcardDAO.getFlashcardById(flashcard.getId());

        assertThat(savedFlashcard).isNotNull();
        assertThat(savedFlashcard.getKanji()).isEqualTo(kanji);
        assertThat(savedFlashcard.getUser()).isEqualTo(user);
        assertThat(savedFlashcard.getVocabulary()).containsExactly("ひあさ morning sun");
        assertThat(savedFlashcard.getNotes()).isEqualTo("Flashcard note for 日");
    }

    @Test
    void testGetFlashcardByIdExisting() {
        Flashcard flashcard = flashcardDAO.getFlashcardById(1);

        assertThat(flashcard).isNotNull();
        assertThat(flashcard.getKanji()).isEqualTo(kanjiDAO.findKanjiById(1));
        assertThat(flashcard.getUser()).isEqualTo(userDAO.findById(1).get());
        assertThat(flashcard.getVocabulary()).containsExactly("ひあさ morning sun");
        assertThat(flashcard.getNotes()).isEqualTo("Flashcard note for 日");
    }

    @Test
    void testGetFlashcardByIdNonExisting() {
        Flashcard flashcard = flashcardDAO.getFlashcardById(999);

        assertThat(flashcard).isNull();
    }

    @Test
    void testGetAllFlashcardsByUserId() {
        List<Flashcard> flashcards = flashcardDAO.getAllFlashcardsByUserId(1);

        assertThat(flashcards).hasSize(2);
        assertThat(flashcards).extracting(Flashcard::getId)
                .containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void testCountFlashcardsByUserId() {
        int count = flashcardDAO.countFlashcardsByUserId(1);

        assertThat(count).isEqualTo(2);
    }

    @Test
    void testGetFlashcardByUserIdAndId() {
        Flashcard flashcard = flashcardDAO.getFlashcardByUserIdAndId(1, 1);

        assertThat(flashcard).isNotNull();
        assertThat(flashcard.getKanji()).isEqualTo(kanjiDAO.findKanjiById(1));
        assertThat(flashcard.getUser()).isEqualTo(userDAO.findById(1).get());
        assertThat(flashcard.getVocabulary()).containsExactly("ひあさ morning sun");
        assertThat(flashcard.getNotes()).isEqualTo("Flashcard note for 日");
    }

    @Test
    void testGetFlashcardByUserIdAndKanji() {
        Flashcard flashcard = flashcardDAO.getFlashcardByUserIdAndKanji(1, "日");

        assertThat(flashcard).isNotNull();
        assertThat(flashcard.getKanji()).isEqualTo(kanjiDAO.findKanjiById(1));
        assertThat(flashcard.getUser()).isEqualTo(userDAO.findById(1).get());
        assertThat(flashcard.getVocabulary()).containsExactly("ひあさ morning sun");
        assertThat(flashcard.getNotes()).isEqualTo("Flashcard note for 日");
    }

    @Test
    void testDeleteFlashcardExisting() {
        flashcardDAO.deleteFlashcard(1);

        Flashcard flashcard = flashcardDAO.getFlashcardById(1);

        assertThat(flashcard).isNull();
    }
}
