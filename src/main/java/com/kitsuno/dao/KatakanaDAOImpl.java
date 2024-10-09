package com.kitsuno.dao;

import com.kitsuno.entity.Katakana;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KatakanaDAOImpl implements KatakanaDAO {

    private EntityManager entityManager;

    @Autowired
    public KatakanaDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Katakana> findAll() {
        TypedQuery<Katakana> query = entityManager.createQuery("FROM Hiragana", Katakana.class);

        List<Katakana> hiraganaList = query.getResultList();

        return hiraganaList;
    }
}
