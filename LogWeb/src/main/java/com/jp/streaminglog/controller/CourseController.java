package com.jp.streaminglog.controller;

import com.jp.streaminglog.domain.Course;
import com.jp.streaminglog.domain.SearchEngine;
import com.jp.streaminglog.service.CourseService;
import com.jp.streaminglog.service.SearchEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //=Controller + ResponseBody
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private SearchEngineService searchEngineService;

    @GetMapping("/getAllCourse")
    public List<Course> getAllCourse(){
        List<Course> list = courseService.findAll();
        return list;
    }

    @GetMapping("/getAllSearchEngine")
    public List<SearchEngine> getAllSearchEngine(){
        List<SearchEngine> list = searchEngineService.findAll();
        return list;
    }

    @GetMapping("/hello")
    public String test(){
        return "Hello World!";
    }
}
