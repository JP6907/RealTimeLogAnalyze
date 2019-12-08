package com.jp.streaminglog.service;

import com.jp.streaminglog.domain.SearchEngine;

import java.util.List;

public interface SearchEngineService {

    String findNameById(String englishId);

    List<SearchEngine> findAll();
}
