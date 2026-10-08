package com.kitsuno.service;

import com.kitsuno.entity.Kanji;
import java.util.List;
import java.util.Map;

public interface KanjiService {

    Kanji findKanjiById(int id);
    Kanji findKanjiByCharacter(String character);
    List<Kanji> findAll();
    List<Kanji> findAllByGrade(int grade);
    List<Integer> findAllGrades();
    Map<Integer, List<Kanji>> findAllGroupedByGrade();

}