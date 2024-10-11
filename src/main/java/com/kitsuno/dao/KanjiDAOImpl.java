package com.kitsuno.dao;

import com.kitsuno.entity.Kanji;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KanjiDAOImpl implements KanjiDAO {

    private EntityManager entityManager;

    @Autowired
    public KanjiDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Kanji> findAll() {
        TypedQuery<Kanji> query = entityManager.createQuery("Select k from Kanji k", Kanji.class);

        List<Kanji> kanjiList = query.getResultList();

        return kanjiList;
    }
}
