package com.kitsuno.dao;

import com.kitsuno.entity.Kanji;
import java.util.List;

public interface KanjiDAO {

    Kanji findKanjiById(int id);
    Kanji findKanjiByCharacter(String character);
    List<Kanji> findAll();
    List<Integer> findAllGrades();
    List<Kanji> findAllByGrade(int grade);

}