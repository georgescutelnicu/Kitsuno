package com.kitsuno.service;

import com.kitsuno.entity.Flashcard;
import com.kitsuno.entity.Kanji;

import java.util.Map;
import java.util.List;

public interface KanjiService {

    Kanji findKanjiById(int id);
    Kanji findKanjiByCharacter(String character);
    Map<String, List<Kanji>> findAllGroupedByCategory();
}
