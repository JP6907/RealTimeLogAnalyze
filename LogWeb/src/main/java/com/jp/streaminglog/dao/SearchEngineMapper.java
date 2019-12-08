package com.jp.streaminglog.dao;

import com.jp.streaminglog.domain.SearchEngine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchEngineMapper {

    @Select("select name from search_engine where id = #{englishId}")
    String findNameById(String englishId);

    @Select("select * from search_engine")
    List<SearchEngine> findAll();
}
