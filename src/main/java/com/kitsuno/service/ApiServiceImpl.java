package com.kitsuno.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class ApiServiceImpl implements ApiService {

    private final WebClient webClient;

    public ApiServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Map<String, Object> getSentenceExamples(String query) {
        Map<String, Object> requestBody = Map.of(
                "query", query,
                "language", "English",
                "no_english", false
        );

        return webClient.post()
                .uri("/search/sentences")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}
