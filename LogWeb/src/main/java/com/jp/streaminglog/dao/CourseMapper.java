package com.jp.streaminglog.dao;

import com.jp.streaminglog.domain.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("select id,name from course where id = #{id}")
    Course findCourseById(String id);

    @Select("select * from course")
    List<Course> findAll();
}
