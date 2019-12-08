package com.jp.spark.streaming.dao

import com.jp.spark.streaming.domain.{CourseClickCount, CourseSearchClickCount}

import scala.collection.mutable.ListBuffer

object CourseSearchClickCountDaoTest {
  def main(args: Array[String]): Unit = {
    val list = new ListBuffer[CourseSearchClickCount]
    list.append(CourseSearchClickCount("2019-11-11 12:11:22",12))
    list.append(CourseSearchClickCount("2018-11-11 14:12:33",32))
    CourseSearchClickCountDao.save(list)
  }
}
