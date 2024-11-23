package com.kitsuno.service;

import com.kitsuno.dao.FlashcardDAO;
import com.kitsuno.dto.FlashcardDTO;
import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.User;
import com.kitsuno.exception.rest.CharacterNotFoundException;
import com.kitsuno.exception.rest.FlashcardNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FlashcardServiceImpl implements FlashcardService {

    private final FlashcardDAO flashcardDAO;
    private final KanjiService kanjiService;
    private final UserService userService;

    @Autowired
    public FlashcardServiceImpl(FlashcardDAO flashcardDAO, KanjiService kanjiService, UserService userService) {
        this.flashcardDAO = flashcardDAO;
        this.kanjiService = kanjiService;
        this.userService = userService;
    }

    @Override
    public void saveOrUpdateFlashcard(FlashcardDTO flashcardDTO, int userId, String kanjiCharacter) {
        Flashcard flashcard = getFlashcardByUserAndKanji(userId, kanjiCharacter);

        if (flashcard == null) {
            flashcard = new Flashcard();
        }

        Kanji kanji = kanjiService.findKanjiById(flashcardDTO.getKanjiId());
        Optional<User> user = userService.findById(userId);

        flashcard.setKanji(kanji);
        flashcard.setUser(user.get());
        flashcard.setVocabulary(flashcardDTO.getVocabularyAsArray());
        flashcard.setNotes(flashcardDTO.getNotes());

        flashcardDAO.saveFlashcard(flashcard);
    }

    @Override
    public Flashcard getFlashcardById(int id) {
        return flashcardDAO.getFlashcardById(id);
    }

    @Override
    public List<Flashcard> getAllFlashcardsByUserId(int userId) {
        return flashcardDAO.getAllFlashcardsByUserId(userId);
    }

    @Override
    public int countFlashcardsByUserId(int userId) {
        return flashcardDAO.countFlashcardsByUserId(userId);
    }

    @Override
    public Flashcard getFlashcardByUserAndId(int userId, int flashcardId) {
        Flashcard flashcard = flashcardDAO.getFlashcardByUserIdAndId(userId, flashcardId);
        if (flashcard == null) {
            throw new FlashcardNotFoundException("Flashcard not found for id: " + flashcardId);
        }
        return flashcard;
    }

    @Override
    public Flashcard getFlashcardByUserAndKanji(int userId, String character) {
        return flashcardDAO.getFlashcardByUserIdAndKanji(userId, character);
    }

    @Override
    public void deleteFlashcard(int id) {
        flashcardDAO.deleteFlashcard(id);
    }
}
