package com.jp.streaminglog.dao.impl;

import com.jp.streaminglog.Utils.HBaseUtils;
import com.jp.streaminglog.dao.SearchEngineCountDao;
import com.jp.streaminglog.dao.SearchEngineMapper;
import com.jp.streaminglog.domain.SearchEngine;
import com.jp.streaminglog.domain.SearchEngineCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("SearchEngineCountDao")
public class SearchEngineCountDaoImpl implements SearchEngineCountDao {

    @Autowired
    SearchEngineMapper searchEngineMapper;

    @Override
    public List<SearchEngineCount> findSearchEngineCount(String tableName, String date) {
        Map<String,Long> map = new HashMap<String,Long>();
        List<SearchEngineCount> list = new ArrayList<SearchEngineCount>();
        SearchEngineCount searchEngineCount = new SearchEngineCount();

        map = HBaseUtils.getInstance().getClickCount(tableName,date);
        for(Map.Entry<String,Long> entry : map.entrySet()){
            //搜索引擎英文名字
            //20191206_cn.bing.com
            String engilshName = entry.getKey().split("\\.")[1];
            String cnName = searchEngineMapper.findNameById(engilshName);
            searchEngineCount.setName(cnName);
            searchEngineCount.setCount(entry.getValue());
            list.add(searchEngineCount);
        }
        return list;
    }
}
