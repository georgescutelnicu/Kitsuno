package com.kitsuno.dao;

import com.kitsuno.entity.Particle;
import java.util.List;

public interface ParticleDAO {
    List<Particle> findAll();
}
