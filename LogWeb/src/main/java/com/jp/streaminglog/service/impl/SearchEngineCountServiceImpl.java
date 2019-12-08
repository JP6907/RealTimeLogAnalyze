package com.jp.streaminglog.service.impl;

import com.jp.streaminglog.dao.SearchEngineCountDao;
import com.jp.streaminglog.domain.SearchEngineCount;
import com.jp.streaminglog.service.SearchEngineCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SearchEngineCountService")
public class SearchEngineCountServiceImpl implements SearchEngineCountService {

    @Autowired
    private SearchEngineCountDao searchEngineCountDao;

    @Override
    public List<SearchEngineCount> findSearchEngineCount(String tableName, String date) {
        return searchEngineCountDao.findSearchEngineCount(tableName,date);
    }
}
