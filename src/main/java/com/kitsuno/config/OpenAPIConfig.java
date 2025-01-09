package com.kitsuno.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        License mitLicense = new License()
                .name("MIT License")
                .url("https://opensource.org/license/mit");

        Info info = new Info()
                .title("Kitsuno API")
                .version("1.1.2")
                .description("Kitsuno API allows you to efficiently manage flashcards and Japanese character data, " +
                        "including Hiragana, Katakana, and Kanji. Designed for learning purposes, " +
                        "this API provides endpoints to create, retrieve, update, and delete flashcards," +
                        " as well as access detailed information about essential Japanese characters. " +
                        "Whether you're studying or developing educational tools, " +
                        "Kitsuno API supports and enhances your language learning journey.")
                .license(mitLicense);

        ExternalDocumentation externalDocs = new ExternalDocumentation()
                .description("Go Back")
                .url("/vocabulary");

        return new OpenAPI()
                .info(info)
                .externalDocs(externalDocs);
    }
}

