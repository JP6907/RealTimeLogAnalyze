package com.jp.streaminglog.domain;

//映射到HBase
public class CourseCount implements Comparable<CourseCount>{
    private String id;  //课程编号
    private String name; //课程名
    private Long count; //点击计数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    //降序排列
    @Override
    public int compareTo(CourseCount courseCount) {
        return courseCount.count.intValue() - this.count.intValue();
    }
}
