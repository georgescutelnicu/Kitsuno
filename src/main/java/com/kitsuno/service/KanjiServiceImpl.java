package com.kitsuno.service;

import com.kitsuno.dao.KanjiDAO;
import com.kitsuno.entity.Kanji;
import com.kitsuno.exception.rest.CharacterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
    public List<Kanji> findAllByCategory(String category) {
        List<Kanji> kanjiList =  kanjiDAO.findAllByCategory(category);
        if (kanjiList.isEmpty()) {
            throw new CharacterNotFoundException("Kanji characters not found for category: " + category);
        }
        return kanjiList;
    }

    @Override
    public List<String> findAllCategories() {
        return kanjiDAO.findAllCategories();
    }

    @Override
    public Map<String, List<Kanji>> findAllGroupedByCategory() {
        List<Kanji> kanjiList = this.kanjiDAO.findAll();
        Map<String, List<Kanji>> kanjiMap = new LinkedHashMap<>();

        for(Kanji kanji: kanjiList) {
            String category = kanji.getCategory();

            if(!kanjiMap.containsKey(category)) {
                kanjiMap.put(category, new ArrayList<>());
            }

            kanjiMap.get(category).add(kanji);
        }

        return kanjiMap;
    }
}
