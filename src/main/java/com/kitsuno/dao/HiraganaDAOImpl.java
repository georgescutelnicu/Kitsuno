package com.kitsuno.dao;

import com.kitsuno.entity.Hiragana;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HiraganaDAOImpl implements HiraganaDAO {

    private final EntityManager entityManager;

    @Autowired
    public HiraganaDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Hiragana> findAll() {
        TypedQuery<Hiragana> query = entityManager.createQuery("SELECT h FROM Hiragana h", Hiragana.class);

        return query.getResultList();
    }

    @Override
    public Hiragana findByCharacter(String romaji) {
        TypedQuery<Hiragana> query = entityManager.createQuery(
                "SELECT h FROM Hiragana h WHERE h.romaji = :romaji", Hiragana.class);
        query.setParameter("romaji", romaji);

        List<Hiragana> hiraganaList = query.getResultList();

        return hiraganaList.isEmpty() ? null : hiraganaList.get(0);
    }
}
