package com.kitsuno.dao;

import com.kitsuno.entity.Particle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ParticleDAOImpl implements ParticleDAO {

    private final EntityManager entityManager;

    public ParticleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Particle> findAll() {
        TypedQuery<Particle> query = entityManager.createQuery("SELECT p FROM Particle p", Particle.class);

        return query.getResultList();
    }
}
