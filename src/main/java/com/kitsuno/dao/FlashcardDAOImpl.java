package com.kitsuno.dao;

import com.kitsuno.entity.Flashcard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FlashcardDAOImpl implements FlashcardDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveFlashcard(Flashcard flashcard) {
        if (flashcard.getId() != 0) {
            entityManager.merge(flashcard);
        } else {
            entityManager.persist(flashcard);
        }
    }

    @Override
    public Flashcard getFlashcardById(int id) {
        return entityManager.find(Flashcard.class, id);
    }

    @Override
    public List<Flashcard> getAllFlashcardsByUserId(int userId) {
        TypedQuery<Flashcard> query = entityManager.createQuery(
                "FROM Flashcard f WHERE f.user.id = :userId", Flashcard.class);
        query.setParameter("userId", userId);

        List<Flashcard> flashcardList = query.getResultList();

        return flashcardList;
    }

    @Override
    public Flashcard getFlashcardByUserIdAndKanjiCharacter(int userId, String character) {
        TypedQuery<Flashcard> query = entityManager.createQuery(
                "FROM Flashcard f WHERE f.user.id = :userId AND f.kanji.character = :character", Flashcard.class);
        query.setParameter("userId", userId);
        query.setParameter("character", character);

        List<Flashcard> results = query.getResultList();

        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }


    @Override
    @Transactional
    public void deleteFlashcard(int id) {
        Flashcard flashcard = getFlashcardById(id);
        if (flashcard != null) {
            entityManager.remove(flashcard);
        }
    }
}
