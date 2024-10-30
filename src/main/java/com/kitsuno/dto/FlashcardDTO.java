package com.kitsuno.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class FlashcardDTO {

    @Size(max = 8, message = "Vocabulary word must not exceed 8 characters")
    private String vocabWord1;

    @Size(max = 24, message = "Vocabulary meaning must not exceed 24 characters")
    private String vocabMeaning1;

    @Size(max = 8, message = "Vocabulary word must not exceed 8 characters")
    private String vocabWord2;

    @Size(max = 24, message = "Vocabulary meaning must not exceed 24 characters")
    private String vocabMeaning2;

    @Size(max = 8, message = "Vocabulary word must not exceed 8 characters")
    private String vocabWord3;

    @Size(max = 24, message = "Vocabulary meaning must not exceed 24 characters")
    private String vocabMeaning3;

    @Size(max = 8, message = "Vocabulary word must not exceed 8 characters")
    private String vocabWord4;

    @Size(max = 24, message = "Vocabulary meaning must not exceed 24 characters")
    private String vocabMeaning4;

    @Size(max = 200, message = "Notes must not exceed 200 characters")
    private String notes;

    @NotNull(message = "Kanji ID cannot be null.")
    private Integer kanjiId;

    @NotNull(message = "User ID cannot be null.")
    private Integer userId;

    public FlashcardDTO() {
    }

    public String getVocabWord1() {
        return vocabWord1;
    }

    public String getVocabMeaning1() {
        return vocabMeaning1;
    }

    public String getVocabWord2() {
        return vocabWord2;
    }

    public String getVocabMeaning2() {
        return vocabMeaning2;
    }

    public String getVocabWord3() {
        return vocabWord3;
    }

    public String getVocabMeaning3() {
        return vocabMeaning3;
    }

    public String getVocabWord4() {
        return vocabWord4;
    }

    public String getVocabMeaning4() {
        return vocabMeaning4;
    }

    public String getNotes() {
        return notes;
    }

    public Integer getKanjiId() {
        return kanjiId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setVocabWord1(String vocabWord1) {
        this.vocabWord1 = vocabWord1;
    }

    public void setVocabMeaning1(String vocabMeaning1) {
        this.vocabMeaning1 = vocabMeaning1;
    }

    public void setVocabWord2(String vocabWord2) {
        this.vocabWord2 = vocabWord2;
    }

    public void setVocabMeaning2(String vocabMeaning2) {
        this.vocabMeaning2 = vocabMeaning2;
    }

    public void setVocabWord3(String vocabWord3) {
        this.vocabWord3 = vocabWord3;
    }

    public void setVocabMeaning3(String vocabMeaning3) {
        this.vocabMeaning3 = vocabMeaning3;
    }

    public void setVocabWord4(String vocabWord4) {
        this.vocabWord4 = vocabWord4;
    }

    public void setVocabMeaning4(String vocabMeaning4) {
        this.vocabMeaning4 = vocabMeaning4;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setKanjiId(Integer kanjiId) {
        this.kanjiId = kanjiId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String[] getVocabularyAsArray() {
        List<String> vocabList = new ArrayList<>();

        String[] vocabWords = {vocabWord1, vocabWord2, vocabWord3, vocabWord4};
        String[] vocabMeanings = {vocabMeaning1, vocabMeaning2, vocabMeaning3, vocabMeaning4};

        for (int i = 0; i < vocabWords.length; i++) {
            if (vocabWords[i] != null && !vocabWords[i].trim().isEmpty() &&
                    vocabMeanings[i] != null && !vocabMeanings[i].trim().isEmpty()) {

                String cleanWord = vocabWords[i].trim().replaceAll("\\s+", "");
                String cleanMeaning = vocabMeanings[i].trim().replaceAll("\\s+", " ");

                vocabList.add(cleanWord + " " + cleanMeaning);
            }
        }

        return vocabList.toArray(new String[0]);
    }


    public boolean hasAtLeastOneVocabPair() {
        return (vocabWord1 != null && !vocabWord1.isEmpty() && vocabMeaning1 != null && !vocabMeaning1.isEmpty()) ||
                (vocabWord2 != null && !vocabWord2.isEmpty() && vocabMeaning2 != null && !vocabMeaning2.isEmpty()) ||
                (vocabWord3 != null && !vocabWord3.isEmpty() && vocabMeaning3 != null && !vocabMeaning3.isEmpty()) ||
                (vocabWord4 != null && !vocabWord4.isEmpty() && vocabMeaning4 != null && !vocabMeaning4.isEmpty());
    }
}
