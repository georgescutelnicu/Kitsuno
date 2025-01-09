package com.kitsuno.service;

import com.kitsuno.entity.Katakana;

import java.util.List;

public interface KatakanaService {

    List<Katakana> findAll();
    Katakana findByCharacter(String romaji);
}
