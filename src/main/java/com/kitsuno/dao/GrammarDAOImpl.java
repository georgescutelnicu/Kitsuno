package com.kitsuno.dao;

import com.kitsuno.entity.Grammar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class GrammarDAOImpl implements GrammarDAO {

    private final EntityManager entityManager;

    public GrammarDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Grammar> findAll() {
        TypedQuery<Grammar> query = entityManager.createQuery(
                "SELECT g FROM Grammar g ORDER BY g.id", Grammar.class);

        return query.getResultList();
    }

    @Override
    public Grammar findById(int id) {
        return entityManager.find(Grammar.class, id);
    }

    @Override
    public Grammar findPrevious(int id) {
        TypedQuery<Grammar> query = entityManager.createQuery(
                "SELECT g FROM Grammar g WHERE g.id < :id ORDER BY g.id DESC", Grammar.class);
        query.setParameter("id", id);
        query.setMaxResults(1);

        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public Grammar findNext(int id) {
        TypedQuery<Grammar> query = entityManager.createQuery(
                "SELECT g FROM Grammar g WHERE g.id > :id ORDER BY g.id ASC", Grammar.class);
        query.setParameter("id", id);
        query.setMaxResults(1);

        return query.getResultStream().findFirst().orElse(null);
    }
}
