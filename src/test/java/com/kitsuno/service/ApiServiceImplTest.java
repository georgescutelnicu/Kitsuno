package com.kitsuno.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ApiServiceImplTest {

    @Autowired
    private ApiServiceImpl apiService;

    @Test
    void testApiCall_ReturnsExpectedResponse() {
        Map<String, Object> response = apiService.getSentenceExamples("tree");

        assertThat(response).isNotNull();
        assertThat(response).containsKey("sentences");


        List<Map<String, Object>> sentences = (List<Map<String, Object>>) response.get("sentences");

        assertThat(sentences).isNotEmpty();

        for (Map<String, Object> sentence : sentences) {
            assertThat(sentence.get("content")).asString().contains("木");
        }

        for (Map<String, Object> sentence : sentences) {
            assertThat(sentence.get("translation")).asString().containsIgnoringCase("tree");
        }
    }
}
