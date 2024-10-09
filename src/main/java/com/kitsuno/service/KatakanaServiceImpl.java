package com.kitsuno.service;

import com.kitsuno.dao.KatakanaDAO;
import com.kitsuno.entity.Katakana;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KatakanaServiceImpl implements KatakanaService {

    private KatakanaDAO KatakanaDAO;

    @Autowired
    public KatakanaServiceImpl(KatakanaDAO KatakanaDAO) {
        this.KatakanaDAO = KatakanaDAO;
    }

    @Override
    public List<Katakana> findAll() {
        return KatakanaDAO.findAll();
    }
}
