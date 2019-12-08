package com.jp.spark.streaming.dao

import com.jp.spark.streaming.domain.CourseClickCount

import scala.collection.mutable.ListBuffer

object CourseClickCountDaoTest {
  def main(args: Array[String]): Unit = {
    val list = new ListBuffer[CourseClickCount]
    list.append(CourseClickCount("2019-11-11 12:11:22",12))
    list.append(CourseClickCount("2018-11-11 14:12:33",29))
    CourseClickCountDao.save(list)
  }
}
