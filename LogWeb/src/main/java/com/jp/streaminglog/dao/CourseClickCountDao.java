package com.jp.streaminglog.dao;

import com.jp.streaminglog.domain.CourseCount;

import java.util.Set;

//课程统计结果
public interface CourseClickCountDao {

    public Set<CourseCount> findCourseClickCount(String tableName,String date);

}
