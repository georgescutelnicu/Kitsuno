package com.kitsuno.service;

import com.kitsuno.dao.FlashcardDAO;
import com.kitsuno.entity.Flashcard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FlashcardServiceImpl implements FlashcardService {

    private final FlashcardDAO flashcardDAO;

    @Autowired
    public FlashcardServiceImpl(FlashcardDAO flashcardDAO) {
        this.flashcardDAO = flashcardDAO;
    }

    @Override
    public void saveFlashcard(Flashcard flashcard) {
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
    public void deleteFlashcard(int id) {
        flashcardDAO.deleteFlashcard(id);
    }
}
