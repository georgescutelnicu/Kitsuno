package com.kitsuno.entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "kanji")
public class Kanji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "character")
    private String character;

    @Column(name = "jlpt")
    private String jlpt;

    @Column(name = "meanings")
    private String meanings;

    @Column(name = "onyomi_readings")
    private String[] onyomiReadings;

    @Column(name = "kunyomi_readings")
    private String[] kunyomiReadings;

    @Column(name = "onyomi_vocab")
    private String[] onyomiVocab;

    @Column(name = "kunyomi_vocab")
    private String[] kunyomiVocab;

    @Column(name = "category")
    private String category;

    @Column(name = "stroke_count")
    private int strokeCount;

    public Kanji() {
    }

    public Kanji(String character, String jlpt, String meanings, String[] onyomiReadings, String[] kunyomiReadings,
                 String[] onyomiVocab, String[] kunyomiVocab, String category, int strokeCount) {
        this.character = character;
        this.jlpt = jlpt;
        this.meanings = meanings;
        this.onyomiReadings = onyomiReadings;
        this.kunyomiReadings = kunyomiReadings;
        this.onyomiVocab = onyomiVocab;
        this.kunyomiVocab = kunyomiVocab;
        this.category = category;
        this.strokeCount = strokeCount;
    }

    @Override
    public String toString() {
        return "Kanji{" +
                "id=" + id +
                ", character='" + character + '\'' +
                ", jlpt='" + jlpt + '\'' +
                ", meanings='" + meanings + '\'' +
                ", onyomiReadings=" + Arrays.toString(onyomiReadings) +
                ", kunyomiReadings=" + Arrays.toString(kunyomiReadings) +
                ", onyomiVocab=" + Arrays.toString(onyomiVocab) +
                ", kunyomiVocab=" + Arrays.toString(kunyomiVocab) +
                ", category='" + category + '\'' +
                ", strokeCount=" + strokeCount +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getCharacter() {
        return character;
    }

    public String getJlpt() {
        return jlpt;
    }

    public String getMeanings() {
        return meanings;
    }

    public String[] getOnyomiReadings() {
        return onyomiReadings;
    }

    public String[] getKunyomiReadings() {
        return kunyomiReadings;
    }

    public String[] getOnyomiVocab() {
        return onyomiVocab;
    }

    public String[] getKunyomiVocab() {
        return kunyomiVocab;
    }

    public String getCategory() {
        return category;
    }

    public int getStrokeCount() {
        return strokeCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setJlpt(String jlpt) {
        this.jlpt = jlpt;
    }

    public void setMeanings(String meanings) {
        this.meanings = meanings;
    }

    public void setOnyomiReadings(String[] onyomiReadings) {
        this.onyomiReadings = onyomiReadings;
    }

    public void setKunyomiReadings(String[] kunyomiReadings) {
        this.kunyomiReadings = kunyomiReadings;
    }

    public void setOnyomiVocab(String[] onyomiVocab) {
        this.onyomiVocab = onyomiVocab;
    }

    public void setKunyomiVocab(String[] kunyomiVocab) {
        this.kunyomiVocab = kunyomiVocab;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStrokeCount(int strokeCount) {
        this.strokeCount = strokeCount;
    }
}
