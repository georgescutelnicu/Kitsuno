package com.kitsuno.entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "flashcards")
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "vocabulary")
    private String[] vocabulary;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "kanji_id", nullable = false)
    private Kanji kanji;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Flashcard() {
    }

    public Flashcard(String[] vocabulary, String notes, Kanji kanji, User user) {
        this.vocabulary = vocabulary;
        this.notes = notes;
        this.kanji = kanji;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "id=" + id +
                ", vocabulary=" + Arrays.toString(vocabulary) +
                ", notes='" + notes + '\'' +
                ", kanji=" + kanji.getCharacter() +
                ", user=" + user.getUsername() +
                '}';
    }

    public int getId() {
        return id;
    }

    public String[] getVocabulary() {
        return vocabulary;
    }

    public String getNotes() {
        return notes;
    }

    public Kanji getKanji() {
        return kanji;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVocabulary(String[] vocabulary) {
        this.vocabulary = vocabulary;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
