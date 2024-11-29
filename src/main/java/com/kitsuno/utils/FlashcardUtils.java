package com.kitsuno.utils;

import com.kitsuno.dto.FlashcardDTO;
import com.kitsuno.dto.rest.FlashcardRestDTO;
import com.kitsuno.entity.Flashcard;
import com.kitsuno.exception.rest.FlashcardAlreadyExistsException;
import com.kitsuno.exception.rest.FlashcardNotFoundException;
import com.kitsuno.service.FlashcardService;

public class FlashcardUtils {

    public static FlashcardRestDTO toDTO(Flashcard flashcard) {
        return new FlashcardRestDTO(
                flashcard.getId(),
                flashcard.getKanji().getCharacter(),
                flashcard.getKanji().getJlpt(),
                flashcard.getKanji().getMeanings(),
                flashcard.getKanji().getOnyomiReadings(),
                flashcard.getKanji().getKunyomiReadings(),
                flashcard.getVocabulary(),
                flashcard.getNotes()
        );
    }

    public static void checkFlashcardExists(FlashcardDTO flashcardDTO, String kanjiCharacter, int userId,
                                            FlashcardService flashcardService) {
        Flashcard flashcard = flashcardService.getFlashcardByUserAndKanji(userId, kanjiCharacter);
        if (flashcard != null) {
            throw new FlashcardAlreadyExistsException("Flashcard with kanji character " + kanjiCharacter +
                    " already exists for the current user. Try to update it instead.");
        }
    }

    public static void validateVocabPairs(FlashcardDTO flashcardDTO) {
        if (!flashcardDTO.hasAtLeastOneVocabPair()) {
            throw new IllegalArgumentException("Flashcard must have at least one vocabulary word and meaning pair.");
        }
    }

    public static void checkFlashcardExistsForUpdate(FlashcardDTO flashcardDTO, int flashcardId, int userId,
                                                     FlashcardService flashcardService) {
        Flashcard flashcard = flashcardService.getFlashcardByUserAndId(userId, flashcardId);
        if (flashcard == null) {
            throw new FlashcardNotFoundException("Flashcard with id " + flashcardId +
                    " was not found for the current user.");
        }
    }
}
