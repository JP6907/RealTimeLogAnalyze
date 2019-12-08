package com.jp.streaminglog.service.impl;

import com.jp.streaminglog.dao.CourseMapper;
import com.jp.streaminglog.domain.Course;
import com.jp.streaminglog.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Course findCourseById(String id) {
        return courseMapper.findCourseById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseMapper.findAll();
    }
}
