package com.jp.streaminglog.service;

import com.jp.streaminglog.domain.CourseCount;

import java.util.List;

public interface CourseClickCountService {

    public List<CourseCount> findCourseClickCount(String tableName,String date);
}
