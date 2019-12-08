package com.jp.spark.streaming.dao

import com.jp.spark.streaming.domain.CourseSearchClickCount
import com.jp.spark.streaming.utils.HBaseUtils
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.mutable.ListBuffer

object CourseSearchClickCountDao {

  val tableName = "ns1:courses_search_clickcount"
  val cf = "info"
  val qualifer = "click_count"

  def save(list:ListBuffer[CourseSearchClickCount]) : Unit = {
    val table = HBaseUtils.getInstance().getTable(tableName)
    for(item <- list){
      table.incrementColumnValue(Bytes.toBytes(item.day_search_course),
        Bytes.toBytes(cf),
        Bytes.toBytes(qualifer),
        item.click_count)
    }
  }

}
