package com.kitsuno.dao;

import com.kitsuno.entity.Hiragana;

import java.util.List;

public interface HiraganaDAO {

    List<Hiragana> findAll();
    Hiragana findByCharacter(String romaji);
}
