package com.kitsuno.service;

import com.kitsuno.dao.KatakanaDAO;
import com.kitsuno.entity.Katakana;
import com.kitsuno.exception.rest.CharacterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KatakanaServiceImpl implements KatakanaService {

    private final KatakanaDAO katakanaDAO;

    @Autowired
    public KatakanaServiceImpl(KatakanaDAO katakanaDAO) {
        this.katakanaDAO = katakanaDAO;
    }

    @Override
    public List<Katakana> findAll() {
        return katakanaDAO.findAll();
    }

    @Override
    public Katakana findByCharacter(String romaji) {
        Katakana katakana =  katakanaDAO.findByCharacter(romaji);
        if (katakana == null) {
            throw new CharacterNotFoundException("Katakana romaji not found: " + romaji);
        }
        return katakana;
    }
}
