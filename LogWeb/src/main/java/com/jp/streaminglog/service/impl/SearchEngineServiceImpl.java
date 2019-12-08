package com.jp.streaminglog.service.impl;

import com.jp.streaminglog.dao.SearchEngineMapper;
import com.jp.streaminglog.domain.SearchEngine;
import com.jp.streaminglog.service.SearchEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("searchEngineService")
public class SearchEngineServiceImpl implements SearchEngineService {

    @Autowired
    SearchEngineMapper searchEngineMapper;

    @Override
    public String findNameById(String englishId) {
        return searchEngineMapper.findNameById(englishId);
    }

    @Override
    public List<SearchEngine> findAll() {
        return searchEngineMapper.findAll();
    }
}
