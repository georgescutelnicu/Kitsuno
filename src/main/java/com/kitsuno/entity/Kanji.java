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

    @Column(name = "stroke_count")
    private int strokeCount;

    @Column(name = "grade")
    private int grade;

    @Column(name = "meanings")
    private String meanings;

    @Column(name = "onyomi_readings")
    private String[] onyomiReadings;

    @Column(name = "kunyomi_readings")
    private String[] kunyomiReadings;

    @Column(name = "stroke_order_svg")
    private String strokeOrderSvg;

    @Column(name = "vocab")
    private String[] vocab;

    public Kanji() {
    }

    public Kanji(String character, int strokeCount, int grade, String meanings, 
                 String[] onyomiReadings, String[] kunyomiReadings, 
                 String strokeOrderSvg, String[] vocab) {
        this.character = character;
        this.strokeCount = strokeCount;
        this.grade = grade;
        this.meanings = meanings;
        this.onyomiReadings = onyomiReadings;
        this.kunyomiReadings = kunyomiReadings;
        this.strokeOrderSvg = strokeOrderSvg;
        this.vocab = vocab;
    }

    @Override
    public String toString() {
        return "Kanji{" +
                "id=" + id +
                ", character='" + character + '\'' +
                ", strokeCount=" + strokeCount +
                ", grade=" + grade +
                ", meanings='" + meanings + '\'' +
                ", onyomiReadings=" + Arrays.toString(onyomiReadings) +
                ", kunyomiReadings=" + Arrays.toString(kunyomiReadings) +
                ", strokeOrderSvg='" + strokeOrderSvg + '\'' +
                ", vocab=" + Arrays.toString(vocab) +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getStrokeCount() {
        return strokeCount;
    }

    public void setStrokeCount(int strokeCount) {
        this.strokeCount = strokeCount;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getMeanings() {
        return meanings;
    }

    public void setMeanings(String meanings) {
        this.meanings = meanings;
    }

    public String[] getOnyomiReadings() {
        return onyomiReadings;
    }

    public void setOnyomiReadings(String[] onyomiReadings) {
        this.onyomiReadings = onyomiReadings;
    }

    public String[] getKunyomiReadings() {
        return kunyomiReadings;
    }

    public void setKunyomiReadings(String[] kunyomiReadings) {
        this.kunyomiReadings = kunyomiReadings;
    }

    public String getStrokeOrderSvg() {
        return strokeOrderSvg;
    }

    public void setStrokeOrderSvg(String strokeOrderSvg) {
        this.strokeOrderSvg = strokeOrderSvg;
    }

    public String[] getVocab() {
        return vocab;
    }

    public void setVocab(String[] vocab) {
        this.vocab = vocab;
    }
}
