package com.kitsuno.dao;

import com.kitsuno.entity.Flashcard;

import java.util.List;

public interface FlashcardDAO {

    void saveFlashcard(Flashcard flashcard);

    Flashcard getFlashcardById(int id);

    List<Flashcard> getAllFlashcardsByUserId(int userId);

    int countFlashcardsByUserId(int userId);

    Flashcard getFlashcardByUserIdAndId(int userId, int flashcardId);

    Flashcard getFlashcardByUserIdAndKanji(int userId, String character);

    void deleteFlashcard(int id);
}
