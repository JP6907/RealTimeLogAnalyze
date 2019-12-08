package com.jp.spark.streaming.domain

/**
  * 封装课程的总点击数结果
  * @param day_course 时间+课程，对应于Hbase中的RowKey
  * @param click_count 总点击数
  */
case class CourseClickCount (day_course:String,click_count:Int)
