package com.kitsuno.service;

import com.kitsuno.entity.Grammar;
import java.util.List;

public interface GrammarService {
    List<Grammar> findAll();

    Grammar findById(int id);

    Grammar findPrevious(int id);

    Grammar findNext(int id);
}