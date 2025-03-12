package com.kitsuno.dao;

import com.kitsuno.entity.Particle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = "com.kitsuno.dao")
@Sql(scripts = "/particle_data.sql")
class ParticleDAOImplTest {

    @Autowired
    private ParticleDAO particleDAO;

    @Test
    void testFindAll() {
        List<Particle> result = particleDAO.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Particle::getParticle).contains("は (wa)");
    }
}
