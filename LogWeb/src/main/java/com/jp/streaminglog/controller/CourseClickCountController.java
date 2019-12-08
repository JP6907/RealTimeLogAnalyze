package com.jp.streaminglog.controller;

import com.jp.streaminglog.domain.CourseCount;
import com.jp.streaminglog.service.CourseClickCountService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController //=Controller + ResponseBody
@Controller
public class CourseClickCountController {

    @Autowired
    private CourseClickCountService courseClickCountService;

    //页面跳转
    @RequestMapping("/courseClick")
    public String toGetCourseClick(){
        return "courseclick";
    }

    @ResponseBody
    @RequestMapping(value = "/getCourseClickCount",method = RequestMethod.GET)
    public JSONArray courseClickCount(String date){
        if(date==null || date.equals("")){//默认值
            date = "20191206";
        }
        List<CourseCount> list = null;
        list = courseClickCountService.findCourseClickCount("ns1:courses_clickcount",date);
        return JSONArray.fromObject(list);
    }
}