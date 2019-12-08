package com.jp.streaminglog.service;

import com.jp.streaminglog.domain.SearchEngineCount;

import java.util.List;

public interface SearchEngineCountService {

    public List<SearchEngineCount> findSearchEngineCount(String tableName,String date);
}
