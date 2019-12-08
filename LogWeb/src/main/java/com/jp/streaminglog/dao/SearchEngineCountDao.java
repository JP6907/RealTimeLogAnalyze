package com.jp.streaminglog.dao;

import com.jp.streaminglog.domain.SearchEngineCount;

import java.util.List;

//搜索引擎统计结果
public interface SearchEngineCountDao {

    public List<SearchEngineCount> findSearchEngineCount(String tableName,String date);
}
