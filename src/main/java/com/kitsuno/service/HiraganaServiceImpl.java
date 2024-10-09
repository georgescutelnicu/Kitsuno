package com.kitsuno.service;

import com.kitsuno.dao.HiraganaDAO;
import com.kitsuno.entity.Hiragana;
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
}
