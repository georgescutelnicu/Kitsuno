package com.kitsuno.dao;

import com.kitsuno.entity.Kanji;

import java.util.List;

public interface KanjiDAO {

    List<Kanji> findAll();
}
