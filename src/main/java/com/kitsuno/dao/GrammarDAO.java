package com.kitsuno.dao;

import com.kitsuno.entity.Grammar;
import java.util.List;

public interface GrammarDAO {
    List<Grammar> findAll();

    Grammar findById(int id);

    Grammar findPrevious(int id);

    Grammar findNext(int id);
}
