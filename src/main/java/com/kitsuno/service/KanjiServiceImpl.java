package com.kitsuno.service;

import com.kitsuno.dao.KanjiDAO;
import com.kitsuno.entity.Kanji;
import com.kitsuno.exception.rest.CharacterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class KanjiServiceImpl implements KanjiService {

    private final KanjiDAO kanjiDAO;

    @Autowired
    public KanjiServiceImpl(KanjiDAO kanjiDAO) {
        this.kanjiDAO = kanjiDAO;
    }

    @Override
    public Kanji findKanjiById(int id) {
        Kanji kanji = kanjiDAO.findKanjiById(id);
        if (kanji == null) {
            throw new CharacterNotFoundException("Kanji character not found for id: " + id);
        }
        return kanji;
    }

    @Override
    public Kanji findKanjiByCharacter(String character) {
        Kanji kanji = kanjiDAO.findKanjiByCharacter(character);
        if (kanji == null) {
            throw new CharacterNotFoundException("Kanji character not found: " + character);
        }
        return kanji;
    }

    @Override
    public List<Kanji> findAll() {
        return kanjiDAO.findAll();
    }

    @Override
    public List<Kanji> findAllByGrade(int grade) {
        List<Kanji> kanjiList = kanjiDAO.findAllByGrade(grade);
        if (kanjiList.isEmpty()) {
            throw new CharacterNotFoundException("Kanji characters not found for grade: " + grade);
        }
        return kanjiList;
    }

    @Override
    public List<Integer> findAllGrades() {
        return kanjiDAO.findAllGrades();
    }

    @Override
    public Map<Integer, List<Kanji>> findAllGroupedByGrade() {
        List<Kanji> kanjiList = this.kanjiDAO.findAll();
        kanjiList.sort(Comparator.comparingInt(Kanji::getGrade));

        Map<Integer, List<Kanji>> kanjiMap = new LinkedHashMap<>();

        for (Kanji kanji : kanjiList) {
            int grade = kanji.getGrade();

            if (!kanjiMap.containsKey(grade)) {
                kanjiMap.put(grade, new ArrayList<>());
            }

            kanjiMap.get(grade).add(kanji);
        }

        return kanjiMap;
    }
}