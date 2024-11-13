package com.kitsuno.service;

import com.kitsuno.dao.HiraganaDAO;
import com.kitsuno.entity.Hiragana;
import com.kitsuno.exception.rest.CharacterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HiraganaServiceImpl implements HiraganaService {

    private HiraganaDAO hiraganaDAO;

    @Autowired
    public HiraganaServiceImpl(HiraganaDAO hiraganaDAO) {
        this.hiraganaDAO = hiraganaDAO;
    }

    @Override
    public List<Hiragana> findAll() {
        return hiraganaDAO.findAll();
    }

    @Override
    public Hiragana findByCharacter(String character) {
        Hiragana hiragana = hiraganaDAO.findByCharacter(character);
        if (hiragana == null) {
            throw new CharacterNotFoundException("Hiragana character not found: " + character);
        }
        return hiragana;
    }
}
