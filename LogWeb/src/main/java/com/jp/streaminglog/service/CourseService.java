package com.jp.streaminglog.service;

import com.jp.streaminglog.domain.Course;

import java.util.List;

public interface CourseService {

    Course findCourseById(String id);

    List<Course> findAll();
}
