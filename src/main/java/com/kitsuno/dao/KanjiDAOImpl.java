package com.kitsuno.dao;

import com.kitsuno.entity.Kanji;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KanjiDAOImpl implements KanjiDAO {

    private final EntityManager entityManager;

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
        TypedQuery<Kanji> query = entityManager.createQuery(
                "FROM Kanji k WHERE k.character = :character", Kanji.class);
        query.setParameter("character", character);

        List<Kanji> kanjiList = query.getResultList();
        
        return kanjiList.isEmpty() ? null : kanjiList.get(0);
    }

    @Override
    public List<Kanji> findAll() {
        TypedQuery<Kanji> query = entityManager.createQuery(
                "SELECT k FROM Kanji k ORDER BY k.id", Kanji.class);

        return query.getResultList();
    }

    @Override
    public List<Integer> findAllGrades() {
        TypedQuery<Integer> query = entityManager.createQuery(
                "SELECT DISTINCT k.grade FROM Kanji k ORDER BY k.grade", Integer.class);

        return query.getResultList();
    }

    @Override
    public List<Kanji> findAllByGrade(int grade) {
        TypedQuery<Kanji> query = entityManager.createQuery(
                "SELECT k FROM Kanji k WHERE k.grade = :grade ORDER BY k.id", Kanji.class);
        query.setParameter("grade", grade);

        return query.getResultList();
    }
}