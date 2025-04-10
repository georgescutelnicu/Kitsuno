package com.kitsuno.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hiragana")
public class Hiragana implements Kana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "character")
    private String character;

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

    public Hiragana() {
    }

    public Hiragana(String character, String romaji, String audio,
                    String mnemonic, String story,String strokeOrder) {
        this.character = character;
        this.romaji = romaji;
        this.audio = audio;
        this.mnemonic = mnemonic;
        this.story = story;
        this.strokeOrder = strokeOrder;
    }

    public int getId() {
        return id;
    }

    public String getCharacter() {
        return character;
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
