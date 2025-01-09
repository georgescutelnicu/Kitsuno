package com.kitsuno.service;

import com.kitsuno.entity.Kanji;

import java.util.Map;
import java.util.List;

public interface KanjiService {

    Kanji findKanjiById(int id);
    Kanji findKanjiByCharacter(String character);
    List<Kanji> findAll();
    List<Kanji> findAllByCategory(String category);
    List<String> findAllCategories();
    Map<String, List<Kanji>> findAllGroupedByCategory();
}
