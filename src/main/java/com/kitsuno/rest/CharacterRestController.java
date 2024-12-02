package com.kitsuno.rest;

import com.kitsuno.entity.Hiragana;
import com.kitsuno.entity.Kanji;
import com.kitsuno.entity.Katakana;
import com.kitsuno.service.HiraganaService;
import com.kitsuno.service.KanjiService;
import com.kitsuno.service.KatakanaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Characters",
        description = "APIs for retrieving and working with the three main Japanese character sets: " +
                "Hiragana, Katakana, and Kanji.")
public class CharacterRestController {

    private final HiraganaService hiraganaService;
    private final KatakanaService katakanaService;
    private final KanjiService kanjiService;

    @Autowired
    public CharacterRestController(HiraganaService hiraganaService, KatakanaService katakanaService,
                                   KanjiService kanjiService) {
        this.hiraganaService = hiraganaService;
        this.katakanaService = katakanaService;
        this.kanjiService = kanjiService;
    }

    @Operation(summary = "Get all Hiragana characters",
            description = "Retrieve a list of all available Hiragana characters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Hiragana list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hiragana.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/hiragana")
    public List<Hiragana> getAllHiragana(@RequestHeader("API-KEY") String apiKey) {
        return hiraganaService.findAll();
    }

    @Operation(summary = "Get Hiragana character by romaji",
            description = "Retrieve a specific Hiragana character by its romaji representation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Hiragana character",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hiragana.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Hiragana character not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/hiragana/{romaji}")
    public Hiragana getHiraganaByCharacter(@RequestHeader("API-KEY") String apiKey, @PathVariable String romaji) {
        return hiraganaService.findByCharacter(romaji);
    }

    @Operation(summary = "Get all Katakana characters",
            description = "Retrieve a list of all available Katakana characters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Katakana list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Katakana.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/katakana")
    public List<Katakana> getAllKatakana(@RequestHeader("API-KEY") String apiKey) {
        return katakanaService.findAll();
    }

    @Operation(summary = "Get Katakana character by romaji",
            description = "Retrieve a specific Katakana character by its romaji representation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Katakana character",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Katakana.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Katakana character not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/katakana/{romaji}")
    public Katakana getKatakanaByCharacter(@RequestHeader("API-KEY") String apiKey, @PathVariable String romaji) {
        return katakanaService.findByCharacter(romaji);
    }

    @Operation(summary = "Get all Kanji characters",
            description = "Retrieve a list of all available Kanji characters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Kanji list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Kanji.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/kanji")
    public List<Kanji> getAllKanji(@RequestHeader("API-KEY") String apiKey) {
        return kanjiService.findAll();
    }

    @Operation(summary = "Get Kanji character by ID",
            description = "Retrieve a specific Kanji character by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Kanji character",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Kanji.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Kanji character not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/kanji/{id}")
    public Kanji getKanjiById(@RequestHeader("API-KEY") String apiKey, @PathVariable int id) {
        return kanjiService.findKanjiById(id);
    }
}
