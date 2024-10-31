package com.kitsuno.dao;

import com.kitsuno.entity.Flashcard;

import java.util.List;

public interface FlashcardDAO {

    void saveFlashcard(Flashcard flashcard);

    Flashcard getFlashcardById(int id);

    List<Flashcard> getAllFlashcardsByUserId(int userId);

    Flashcard getFlashcardByUserIdAndKanjiCharacter(int userId, String character);

    void deleteFlashcard(int id);
}
