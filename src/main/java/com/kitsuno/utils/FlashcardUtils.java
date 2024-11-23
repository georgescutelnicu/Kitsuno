package com.kitsuno.utils;

import com.kitsuno.dto.rest.FlashcardRestDTO;
import com.kitsuno.entity.Flashcard;

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
}
