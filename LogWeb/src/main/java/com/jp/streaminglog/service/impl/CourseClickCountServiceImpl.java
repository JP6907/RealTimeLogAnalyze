package com.jp.streaminglog.service.impl;

import com.jp.streaminglog.dao.CourseClickCountDao;
import com.jp.streaminglog.domain.CourseCount;
import com.jp.streaminglog.service.CourseClickCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service("CourseClickCountService")
public class CourseClickCountServiceImpl implements CourseClickCountService {

    @Autowired
    private CourseClickCountDao courseClickCountDao;

    /**
     * 将点击率Top5的课程装进list
     * @param tableName
     * @param date
     * @return
     */
    @Override
    public List<CourseCount> findCourseClickCount(String tableName, String date) {
        List<CourseCount> list = new ArrayList<CourseCount>();
        Set<CourseCount> set = courseClickCountDao.findCourseClickCount(tableName,date);
        Iterator<CourseCount> iterator = set.iterator();
        int i = 0;
        while(iterator.hasNext() && i++ <5){
            list.add(iterator.next());
        }
        return list;
    }
}
