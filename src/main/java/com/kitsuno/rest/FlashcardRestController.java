package com.kitsuno.rest;

import com.kitsuno.dto.FlashcardDTO;
import com.kitsuno.dto.FlashcardResponseDTO;
import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.User;
import com.kitsuno.service.FlashcardService;
import com.kitsuno.service.KanjiService;
import com.kitsuno.service.UserService;
import com.kitsuno.utils.FlashcardUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Tag(name = "Flashcards",
        description = "APIs for handling flashcards to support personalized vocabulary and kanji learning.")
public class FlashcardRestController {

    private final FlashcardService flashcardService;
    private final UserService userService;
    private final KanjiService kanjiService;

    public FlashcardRestController(FlashcardService flashcardService, UserService userService,
                                   KanjiService kanjiService) {
        this.flashcardService = flashcardService;
        this.userService = userService;
        this.kanjiService = kanjiService;
    }

    @Operation(summary = "Get all flashcards",
            description = "Retrieve all flashcards associated with the user linked to the provided API key.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved flashcards",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FlashcardResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/flashcards")
    public List<FlashcardResponseDTO> getAllFlashcardsForUser(@RequestHeader("API-KEY") String apiKey) {
        Optional<User> apiKeyUser = userService.findByApiKey(apiKey);
        int userId = apiKeyUser.get().getId();

        List<Flashcard> flashcards = flashcardService.getAllFlashcardsByUserId(userId);
        return flashcards.stream()
                .map(FlashcardUtils::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a flashcard by ID",
            description = "Retrieve a flashcard by its ID, ensuring it belongs to the user associated with " +
                    "the provided API key.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved flashcard",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FlashcardResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Flashcard not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/flashcards/{flashcardId}")
    public FlashcardResponseDTO getFlashcardByUserAndId(@RequestHeader("API-KEY") String apiKey,
                                                        @PathVariable("flashcardId") int flashcardId) {
        Optional<User> apiKeyUser = userService.findByApiKey(apiKey);
        int userId = apiKeyUser.get().getId();

        Flashcard flashcard = flashcardService.getFlashcardByUserAndId(userId, flashcardId);
        return FlashcardUtils.toDTO(flashcard);
    }

    @Operation(summary = "Create a new flashcard",
            description = "Create a new flashcard for the user linked to the provided API key.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created flashcard",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/flashcards")
    public void createFlashcard(@RequestHeader("API-KEY") String apiKey,
                                @RequestBody @Valid FlashcardDTO flashcardDTO) {
        Optional<User> apiKeyUser = userService.findByApiKey(apiKey);
        int userId = apiKeyUser.get().getId();
        flashcardDTO.setUserId(userId);

        String kanjiCharacter = kanjiService.findKanjiById(flashcardDTO.getKanjiId()).getCharacter();

        FlashcardUtils.checkFlashcardExists(kanjiCharacter, userId, flashcardService);
        FlashcardUtils.validateVocabPairs(flashcardDTO);

        flashcardService.saveOrUpdateFlashcard(flashcardDTO, userId, kanjiCharacter);
    }

    @Operation(summary = "Update a flashcard",
            description = "Update an existing flashcard by its ID, ensuring it belongs to the user linked " +
                    "to the provided API key.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated flashcard",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Flashcard not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PutMapping("/flashcards/{flashcardId}")
    public void updateFlashcard(@RequestHeader("API-KEY") String apiKey,
                                @PathVariable int flashcardId,
                                @RequestBody @Valid FlashcardDTO flashcardDTO) {

        Optional<User> apiKeyUser = userService.findByApiKey(apiKey);
        int userId = apiKeyUser.get().getId();

        FlashcardUtils.checkFlashcardExistsForUpdate(flashcardId, userId, flashcardService);
        FlashcardUtils.validateVocabPairs(flashcardDTO);

        flashcardDTO.setUserId(userId);
        flashcardDTO.setKanjiId(flashcardService.getFlashcardByUserAndId(userId, flashcardId).getKanji().getId());

        String kanjiCharacter = kanjiService.findKanjiById(flashcardDTO.getKanjiId()).getCharacter();

        flashcardService.saveOrUpdateFlashcard(flashcardDTO, userId, kanjiCharacter);
    }

    @Operation(summary = "Delete a flashcard",
            description = "Delete a flashcard by its ID, ensuring it belongs to the user associated " +
                    "with the provided API key.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully deleted flashcard",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Flashcard not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @DeleteMapping("/flashcards/{flashcardId}")
    public void deleteFlashcard(@RequestHeader("API-KEY") String apiKey,
                                @PathVariable("flashcardId") int flashcardId) {
        Optional<User> apiKeyUser = userService.findByApiKey(apiKey);
        int userId = apiKeyUser.get().getId();

        Flashcard flashcard = flashcardService.getFlashcardByUserAndId(userId, flashcardId);
        flashcardService.deleteFlashcard(flashcard.getId());
    }
}
