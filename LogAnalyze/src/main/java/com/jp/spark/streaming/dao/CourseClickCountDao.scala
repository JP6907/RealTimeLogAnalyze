package com.jp.spark.streaming.dao

import com.jp.spark.streaming.domain.CourseClickCount
import com.jp.spark.streaming.utils.HBaseUtils
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.mutable.ListBuffer

/**
  * 课程点击数统计访问层
  */
object CourseClickCountDao {

  val tableName = "ns1:courses_clickcount"  //表名
  val cf = "info" //列族
  val qualifer = "click_count" //列

  /**
    * 保存数据到HBase
    * @param list (day_course:String,click_count:Int) 统计后当天每门课程的总点击数
    */
  def save(list:ListBuffer[CourseClickCount]) : Unit = {
    val table = HBaseUtils.getInstance().getTable(tableName)
    for(item <- list){
      table.incrementColumnValue(Bytes.toBytes(item.day_course),
        Bytes.toBytes(cf),
        Bytes.toBytes(qualifer),
        item.click_count)
    }
  }

}
