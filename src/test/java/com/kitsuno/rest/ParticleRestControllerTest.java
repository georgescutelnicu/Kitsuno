package com.kitsuno.rest;

import com.kitsuno.entity.Particle;
import com.kitsuno.service.ParticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParticleRestController.class)
class ParticleRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticleService particleService;

    private Particle particle;

    @BeforeEach
    void setup() {
        particle = new Particle(
                "は (wa)",
                "topic marker",
                "は (wa) follows the topic the speaker wants to talk about. Therefore, " +
                        "wa（は）is often called topic marking particle. The “topic” is often the grammatical subject, " +
                        "but can be anything (including the grammatical object, and sometimes the verb), " +
                        "and it may also follow some other particles.",
                "[ A ] wa [ B ] desu. [ A ] is [ B ].",
                "Kinō wa ame datta 【昨日は雨だった】 It was rainy yesterday"
        );
    }

    @Test
    void testGetAllParticles() throws Exception {
        when(particleService.findAll()).thenReturn(Arrays.asList(particle));

        mockMvc.perform(get("/api/particles").header("API-KEY", "mock-api-key"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].particle").value("は (wa)"))
                .andExpect(jsonPath("$[0].meaning").value("topic marker"));
    }
}
