package com.kitsuno.dto;

public class FlashcardResponseDTO {
    private int id;
    private String kanjiCharacter;
    private int kanjiGrade;
    private String kanjiMeanings;
    private String[] onyomiReadings;
    private String[] kunyomiReadings;
    private String[] vocabulary;
    private String notes;

    public FlashcardResponseDTO(int id, String kanjiCharacter, int kanjiGrade, String kanjiMeanings, String[] onyomiReadings, String[] kunyomiReadings, String[] vocabulary, String notes) {
        this.id = id;
        this.kanjiCharacter = kanjiCharacter;
        this.kanjiGrade = kanjiGrade;
        this.kanjiMeanings = kanjiMeanings;
        this.onyomiReadings = onyomiReadings;
        this.kunyomiReadings = kunyomiReadings;
        this.vocabulary = vocabulary;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getKanjiCharacter() {
        return kanjiCharacter;
    }

    public int getKanjiGrade() {
        return kanjiGrade;
    }

    public String getKanjiMeanings() {
        return kanjiMeanings;
    }

    public String[] getOnyomiReadings() {
        return onyomiReadings;
    }

    public String[] getKunyomiReadings() {
        return kunyomiReadings;
    }

    public String[] getVocabulary() {
        return vocabulary;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKanjiCharacter(String kanjiCharacter) {
        this.kanjiCharacter = kanjiCharacter;
    }

    public void setKanjiGrade(int kanjiGrade) {
        this.kanjiGrade = kanjiGrade;
    }

    public void setKanjiMeanings(String kanjiMeanings) {
        this.kanjiMeanings = kanjiMeanings;
    }

    public void setOnyomiReadings(String[] onyomiReadings) {
        this.onyomiReadings = onyomiReadings;
    }

    public void setKunyomiReadings(String[] kunyomiReadings) {
        this.kunyomiReadings = kunyomiReadings;
    }

    public void setVocabulary(String[] vocabulary) {
        this.vocabulary = vocabulary;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
