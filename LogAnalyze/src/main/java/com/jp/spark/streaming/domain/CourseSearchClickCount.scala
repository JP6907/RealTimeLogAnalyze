package com.jp.spark.streaming.domain

/**
  * 封装统计通过搜索引擎多来的课程的点击量
  * @param day_serach_course 日期+搜索途径，当天通过某搜索引擎过来的实战课程
  * @param click_count 点击数
  */
case class CourseSearchClickCount (day_search_course:String,click_count:Int)
