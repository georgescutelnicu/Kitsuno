package com.kitsuno.service;

import com.kitsuno.dao.ParticleDAO;
import com.kitsuno.entity.Particle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticleServiceImplTest {

    @Mock
    private ParticleDAO particleDAO;

    @InjectMocks
    private ParticleServiceImpl particleService;

    @Test
    void testFindAll() {
        Particle particle = new Particle(
                "は (wa)",
                "topic marker",
                "Follows the topic the speaker wants to talk about.",
                "[ A ] wa [ B ] desu. [ A ] is [ B ].",
                "Kinō wa ame datta 【昨日は雨だった】 It was rainy yesterday"
        );

        when(particleDAO.findAll()).thenReturn(List.of(particle));

        List<Particle> result = particleService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getParticle()).isEqualTo("は (wa)");
    }
}
