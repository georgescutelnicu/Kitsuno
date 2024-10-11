package com.kitsuno.service;

import com.kitsuno.dao.KanjiDAO;
import com.kitsuno.entity.Kanji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
public class KanjiServiceImpl implements KanjiService {

    private KanjiDAO kanjiDAO;

    @Autowired
    public KanjiServiceImpl(KanjiDAO kanjiDAO) {
        this.kanjiDAO = kanjiDAO;
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
