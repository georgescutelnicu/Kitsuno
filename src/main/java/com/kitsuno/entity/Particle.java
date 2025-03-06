package com.kitsuno.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "particle")
public class Particle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "particle")
    private String particle;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "description")
    private String description;

    @Column(name = "formation")
    private String formation;

    @Column(name = "example")
    private String example;

    public Particle() {
    }

    public Particle(String particle, String meaning, String description, String formation, String example) {
        this.particle = particle;
        this.meaning = meaning;
        this.description = description;
        this.formation = formation;
        this.example = example;
    }

    public int getId() {
        return id;
    }

    public String getParticle() {
        return particle;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getDescription() {
        return description;
    }

    public String getFormation() {
        return formation;
    }

    public String getExample() {
        return example;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParticle(String particle) {
        this.particle = particle;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
