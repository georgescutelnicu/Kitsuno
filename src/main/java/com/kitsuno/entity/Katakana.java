package com.kitsuno.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "katakana")
public class Katakana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "letter")
    private String letter;

    @Column(name = "romaji")
    private String romaji;

    @Column(name = "audio")
    private String audio;

    @Column(name = "mnemonic")
    private String mnemonic;

    @Column(name = "story")
    private String story;

    @Column(name = "stroke_order")
    private String strokeOrder;

    public Katakana() {
    }

    public Katakana(int id, String letter, String romaji, String audio,
                    String mnemonic, String story,String strokeOrder) {
        this.id = id;
        this.letter = letter;
        this.romaji = romaji;
        this.audio = audio;
        this.mnemonic = mnemonic;
        this.story = story;
        this.strokeOrder = strokeOrder;
    }

    public int getId() {
        return id;
    }

    public String getLetter() {
        return letter;
    }

    public String getRomaji() {
        return romaji;
    }

    public String getAudio() {
        return audio;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public String getStory() {
        return story;
    }

    public String getStrokeOrder() {
        return strokeOrder;
    }
}
