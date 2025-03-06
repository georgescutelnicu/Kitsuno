package com.kitsuno.service;

import com.kitsuno.dao.ParticleDAO;
import com.kitsuno.entity.Particle;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class ParticleServiceImpl implements ParticleService {

    private final ParticleDAO particleDAO;

    @Autowired
    public ParticleServiceImpl(ParticleDAO particleDAO) {
        this.particleDAO = particleDAO;
    }

    @Override
    public List<Particle> findAll() {
        return particleDAO.findAll();
    }
}
