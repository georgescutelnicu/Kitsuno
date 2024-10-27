package com.kitsuno.dao;

import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.Kanji;

import java.util.List;

public interface KanjiDAO {

    Kanji findKanjiById(int id);
    Kanji findKanjiByCharacter(String character);
    List<Kanji> findAll();
}
