package com.kitsuno.dao;

import com.kitsuno.entity.Kanji;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class KanjiDAOImpl implements KanjiDAO {

    private EntityManager entityManager;

    @Autowired
    public KanjiDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Kanji findKanjiById(int id) {
        return entityManager.find(Kanji.class, id);
    }

    @Override
    public Kanji findKanjiByCharacter(String character) {
        TypedQuery<Kanji> query = entityManager.createQuery("FROM Kanji k WHERE k.character =: character",
                Kanji.class);
        query.setParameter("character", character);

        List<Kanji> kanjiList = query.getResultList();

        return kanjiList.isEmpty() ? null : kanjiList.get(0);
    }

    @Override
    public List<Kanji> findAll() {
        TypedQuery<Kanji> query = entityManager.createQuery("Select k from Kanji k ORDER BY k.id", Kanji.class);

        List<Kanji> kanjiList = query.getResultList();

        return kanjiList;
    }

    @Override
    public List<String> findAllCategories() {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT DISTINCT k.category FROM Kanji k WHERE k.category IS NOT NULL ORDER BY k.category",
                String.class
        );

        List<String> categories = query.getResultList();
        categories = categories.stream()
                .map(category -> category.replace(" ", "-"))
                .collect(Collectors.toList());

        return categories;
    }

    @Override
    public List<Kanji> findAllByCategory(String category) {
        TypedQuery<Kanji> query = entityManager.createQuery(
                "SELECT k FROM Kanji k WHERE k.category = :category ORDER BY k.id",
                Kanji.class
        );
        query.setParameter("category", category);

        List<Kanji> kanjiList = query.getResultList();

        return kanjiList;
    }
}
