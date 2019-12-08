package com.jp.streaminglog.dao.impl;

import com.jp.streaminglog.Utils.HBaseUtils;
import com.jp.streaminglog.dao.CourseClickCountDao;
import com.jp.streaminglog.dao.CourseMapper;
import com.jp.streaminglog.domain.Course;
import com.jp.streaminglog.domain.CourseCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Repository("CourseClickCountDao")
public class CourseClickCountDaoImpl implements CourseClickCountDao {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Set<CourseCount> findCourseClickCount(String tableName, String date) {
        Map<String,Long> map = new HashMap<String,Long>();
        CourseCount courseCount = new CourseCount();
        Set<CourseCount> set = new TreeSet<CourseCount>();
        map = HBaseUtils.getInstance().getClickCount(tableName,date);
        for(Map.Entry<String,Long> entry : map.entrySet()){
            //Rowkey的结构:20190330_112,112为课程的id
            String id = entry.getKey().substring(9);
            //查询课程名
            Course course = courseMapper.findCourseById(id);
            courseCount.setId(course.getId());
            courseCount.setName(course.getName());
            courseCount.setCount(entry.getValue());
            set.add(courseCount);
        }
        return set;
    }
}
