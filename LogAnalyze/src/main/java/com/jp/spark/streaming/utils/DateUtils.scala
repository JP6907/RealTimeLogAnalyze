package com.jp.spark.streaming.utils

import org.apache.commons.lang3.time.FastDateFormat

/**
  * 格式化日期工具类
  */
object DateUtils {

  //指定的输入日期格式
  val YYYYMMDDHMMSS_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd hh:mm:ss");
  //指定输出格式
  val TARGET_FORMAT = FastDateFormat.getInstance("yyyyMMddhhmmss");

  //输入String
  def getTime(time:String) = {
    YYYYMMDDHMMSS_FORMAT.parse(time).getTime
  }

  def parseToMinute(time:String)={
    TARGET_FORMAT.format(getTime(time))
  }

}
