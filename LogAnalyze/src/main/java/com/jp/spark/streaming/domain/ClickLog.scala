package com.jp.spark.streaming.domain

/**
  * 封装清洗后的数据
  * @param ip 日志访问的ip地址
  * @param time 日志访问的时间
  * @param courseId 日志访问的课程编号
  * @param statusCode 日志访问的状态码
  * @param referer 日志访问的referer信息
  */
case class ClickLog (ip:String,time:String,courseId:Int,statusCode:Int,referer:String)
