package com.kitsuno.service;

import com.kitsuno.entity.Flashcard;
import java.util.List;

public interface FlashcardService {

    void saveFlashcard(Flashcard flashcard);

    Flashcard getFlashcardById(int id);

    List<Flashcard> getAllFlashcardsByUserId(int userId);

    Flashcard getFlashcardByUserAndKanji(int userId, String character);

    void deleteFlashcard(int id);
}
