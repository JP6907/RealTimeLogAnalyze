package com.jp.spark.streaming.application

import com.jp.spark.streaming.dao.{CourseClickCountDao, CourseSearchClickCountDao}
import com.jp.spark.streaming.domain.{ClickLog, CourseClickCount, CourseSearchClickCount}
import com.jp.spark.streaming.utils.DateUtils
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges, KafkaUtils}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

/**
  * 项目程序入口
  * 流式处理数据
  */
object CountByStreaming {

  /**
    * 需要接收的参数：zookeeper服务器的ip，kafka消费组
    * hdp-01:9092,hdp-02:9092,hdp-03::9092 logGroup streamtopic 1
    * @param args
    */
  def main(args: Array[String]): Unit = {
    if(args.length!=4){
      System.err.println("Error:you need to input:<kafka> <group> <topics> <threadNum>")
      System.exit(1)
    }


    val Array(kafkaAddress,group,topics,threadNum) = args

    //本地运行
    val sparkConf = new SparkConf().setAppName("CountByStreaming").setMaster("local[4]")
    //间隔60秒
    val ssc = new StreamingContext(sparkConf,Seconds(60))
    //使用kafka作为数据源
    //val topicsMap = topics.split(",").map((_,threadNum.toInt)).toMap
    val topicArray = topics.split(",")

    //配置kafka的参数
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> kafkaAddress,  //本地启动kafka
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> group,
      "auto.offset.reset" -> "earliest", //latest
      "enable.auto.commit" -> (false: java.lang.Boolean)  //是否自动提交偏移量
    )
    //在Kafka中记录读取偏移量
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      //位置策略
      PreferConsistent,
      //订阅的策略
      Subscribe[String, String](topicArray, kafkaParams)
    )

    //得到原始的日志数据
    val logResourcesDS = stream.map(_.value)

    //{ip}\t{local_time}\t\"GET /{url} HTTP/1.1\"\t{status_code}\t{referer}
    //124.110.98.30 2019-12-06 14:35:54 "GET /class/250.html HTTP/1.1"  200 http://cn.bing.com/search?q=Spark SQL实战
    /**
      * (1)清洗数据，把它封装到ClickLog中
      * (2)过滤掉非法的数据
      */
    val cleanedData = logResourcesDS.map(line => {
      val splits = line.split("\t")
      if(splits.length != 5) {      //不合法的数据直接封装默认赋予错误值，filter会将其过滤
        ClickLog("", "", 0, 0, "")
      }
      else {
        val ip = splits(0)   //获得日志中用户的ip
        val time = DateUtils.parseToMinute(splits(1)) //获得日志中用户的访问时间，并调用DateUtils格式化时间
        val status = splits(3).toInt  //获得访问状态码
        val referer = splits(4)
        val url = splits(2).split(" ")(1)  //获得搜索url
        var courseId = 0
        if(url.startsWith("/class")){
          val courseIdHtml = url.split("/")(2)
          courseId = courseIdHtml.substring(0,courseIdHtml.lastIndexOf(".")).toInt
        }
        ClickLog(ip,time,courseId,status,referer)  //将清洗后的日志封装到ClickLog中
      }
    }).filter(x => x.courseId != 0 )   //过滤掉非实战课程

    /**
      * (1) 统计数据
      * (2) 把计算结果写入HBase
      */
    cleanedData.map(line => {
      //这里相当于定义HBase表"ns1:courses_clickcount"的RowKey，
      // 将‘日期_课程’作为RowKey,意义为某天某门课的访问数
      (line.time.substring(0,8) + "_" + line.courseId,1)
    }).reduceByKey(_+_)
        .foreachRDD(rdd =>{
          rdd.foreachPartition(partition => {
            val list = new ListBuffer[CourseClickCount]
            partition.foreach(item => {
              list.append(CourseClickCount(item._1,item._2))
            })
            CourseClickCountDao.save(list)
          })
        })
    /**
      * 统计至今为止通过各个搜索引擎而来的实战课程的总点击数
      * (1)统计数据
      * (2)把统计结果写进HBase中去
      */
    cleanedData.map(line => {
      val referer = line.referer
      val time = line.time.substring(0,8)
      var url = ""
      if(referer=="-"){ //过滤非法url
        (url,time)
      }else{
        //取出搜索引擎名字
        url = referer.replaceAll("//","/").split("/")(1)
        (url,time)
      }
    }).filter(x => x._1 != "").map(line => {
      //这里相当于定义HBase表"ns1:courses_search_clickcount"的RowKey，
      // 将'日期_搜索引擎名'作为RowKey,意义为某天通过某搜搜引擎访问课程的次数
      (line._2 + "_" + line._1,1)
    }).reduceByKey(_+_)
        .foreachRDD(rdd => {
          rdd.foreachPartition(partition => {
            val list = new ListBuffer[CourseSearchClickCount]
            partition.foreach(item => {
              list.append(CourseSearchClickCount(item._1,item._2))
            })
            CourseSearchClickCountDao.save(list)
          })
        })

    ssc.start()
    ssc.awaitTermination()

  }


}
