package com.kitsuno.service;

import com.kitsuno.dao.KatakanaDAO;
import com.kitsuno.entity.Hiragana;
import com.kitsuno.entity.Katakana;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KatakanaServiceImpl implements KatakanaService {

    private KatakanaDAO katakanaDAO;

    @Autowired
    public KatakanaServiceImpl(KatakanaDAO katakanaDAO) {
        this.katakanaDAO = katakanaDAO;
    }

    @Override
    public List<Katakana> findAll() {
        return katakanaDAO.findAll();
    }

    @Override
    public Katakana findByCharacter(String character) {
        return katakanaDAO.findByCharacter(character);
    }
}
