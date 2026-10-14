package com.kitsuno.service;

import com.kitsuno.dao.GrammarDAO;
import com.kitsuno.entity.Grammar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GrammarServiceImpl implements GrammarService {

    private final GrammarDAO grammarDAO;

    @Autowired
    public GrammarServiceImpl(GrammarDAO grammarDAO) {
        this.grammarDAO = grammarDAO;
    }

    @Override
    public List<Grammar> findAll() {
        return grammarDAO.findAll();
    }

    @Override
    public Grammar findById(int id) {
        return grammarDAO.findById(id);
    }

    @Override
    public Grammar findPrevious(int id) {
        return grammarDAO.findPrevious(id);
    }

    @Override
    public Grammar findNext(int id) {
        return grammarDAO.findNext(id);
    }
}