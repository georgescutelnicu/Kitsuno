package com.kitsuno.dao;

import com.kitsuno.entity.Hiragana;
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
        TypedQuery<Katakana> query = entityManager.createQuery("SELECT k FROM Katakana k", Katakana.class);

        List<Katakana> katakanaList = query.getResultList();

        return katakanaList;
    }

    @Override
    public Katakana findByCharacter(String character) {
        TypedQuery<Katakana> query = entityManager.createQuery(
                "SELECT k FROM Katakana k WHERE k.character = :character", Katakana.class);
        query.setParameter("character", character);

        List<Katakana> katakanaList = query.getResultList();

        return katakanaList.isEmpty() ? null : katakanaList.get(0);
    }
}
