package com.kitsuno.service;

import com.kitsuno.dto.FlashcardDTO;
import com.kitsuno.entity.Flashcard;
import java.util.List;

public interface FlashcardService {

    void saveOrUpdateFlashcard(FlashcardDTO flashcardDTO, int userId, String kanjiCharacter);

    Flashcard getFlashcardById(int id);

    List<Flashcard> getAllFlashcardsByUserId(int userId);

    int countFlashcardsByUserId(int userId);

    Flashcard getFlashcardByUserAndId(int userId, int flashcardId);

    Flashcard getFlashcardByUserAndKanji(int userId, String character);

    void deleteFlashcard(int id);
}
