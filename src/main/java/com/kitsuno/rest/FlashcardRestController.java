package com.kitsuno.rest;

import com.kitsuno.dto.FlashcardDTO;
import com.kitsuno.dto.rest.FlashcardRestDTO;
import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.User;
import com.kitsuno.service.FlashcardService;
import com.kitsuno.service.KanjiService;
import com.kitsuno.service.UserService;
import com.kitsuno.utils.FlashcardUtils;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
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

    @GetMapping("/flashcards")
    public List<FlashcardRestDTO> getAllFlashcardsForUser(@RequestHeader("API-KEY") String apiKey) {
        Optional<User> apiKeyUser = userService.findByApiKey(apiKey);
        int userId = apiKeyUser.get().getId();

        List<Flashcard> flashcards = flashcardService.getAllFlashcardsByUserId(userId);
        List<FlashcardRestDTO> flashcardRestDTOs = flashcards.stream()
                .map(FlashcardUtils::toDTO)
                .collect(Collectors.toList());

        return flashcardRestDTOs;
    }

    @GetMapping("/flashcards/{flashcardId}")
    public FlashcardRestDTO getFlashcardByUserAndId(@RequestHeader("API-KEY") String apiKey,
                                                         @PathVariable("flashcardId") int flashcardId) {
        Optional<User> apiKeyUser = userService.findByApiKey(apiKey);
        int userId = apiKeyUser.get().getId();

        Flashcard flashcard = flashcardService.getFlashcardByUserAndId(userId, flashcardId);
        FlashcardRestDTO flashcardRestDTO = FlashcardUtils.toDTO(flashcard);

        return flashcardRestDTO;
    }

    @PostMapping("/flashcards")
    public void createFlashcard(@RequestHeader("API-KEY") String apiKey,
                                        @RequestBody @Valid FlashcardDTO flashcardDTO) {
        Optional<User> apiKeyUser = userService.findByApiKey(apiKey);
        int userId = apiKeyUser.get().getId();
        flashcardDTO.setUserId(userId);

        String kanjiCharacter = kanjiService.findKanjiById(flashcardDTO.getKanjiId()).getCharacter();

        FlashcardUtils.checkFlashcardExists(flashcardDTO, kanjiCharacter, userId, flashcardService);
        FlashcardUtils.validateVocabPairs(flashcardDTO);

        flashcardService.saveOrUpdateFlashcard(flashcardDTO, userId, kanjiCharacter);
    }

    @PutMapping("/flashcards/{flashcardId}")
    public void updateFlashcard(@RequestHeader("API-KEY") String apiKey,
                                @PathVariable int flashcardId,
                                @RequestBody @Valid FlashcardDTO flashcardDTO) {

        Optional<User> apiKeyUser = userService.findByApiKey(apiKey);
        int userId = apiKeyUser.get().getId();

        FlashcardUtils.checkFlashcardExistsForUpdate(flashcardDTO, flashcardId, userId, flashcardService);
        FlashcardUtils.validateVocabPairs(flashcardDTO);

        flashcardDTO.setUserId(userId);
        flashcardDTO.setKanjiId(flashcardService.getFlashcardByUserAndId(userId, flashcardId).getKanji().getId());

        String kanjiCharacter = kanjiService.findKanjiById(flashcardDTO.getKanjiId()).getCharacter();

        flashcardService.saveOrUpdateFlashcard(flashcardDTO, userId, kanjiCharacter);
    }

    @DeleteMapping("/flashcards/{flashcardId}")
    public void deleteFlashcard(@RequestHeader("API-KEY") String apiKey,
                                           @PathVariable("flashcardId") int flashcardId) {
        Optional<User> apiKeyUser = userService.findByApiKey(apiKey);
        int userId = apiKeyUser.get().getId();

        Flashcard flashcard = flashcardService.getFlashcardByUserAndId(userId, flashcardId);

        flashcardService.deleteFlashcard(flashcard.getId());
    }
}
