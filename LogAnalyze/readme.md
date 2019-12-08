
# 1. 概述
这个工程为大数据实时流处理日志系统的日志分析部分，采用 Flume + kafka + Spark + Hbase 的架构。

script 目录下的 generate_log.py 脚本为日志生成脚本，可以模拟日志的生成，Flume 会将生成的日志迁移到 Kafka 中，
SparkStreaming 从 Kafka 中读取日志，并将处理后得到的统计数据存入 HBase 中。

# 2. 版本
- jdk1.8
- scala 2.11.8
- Hadoop-2.8.1
- zookeeper-3.4.6
- Hbase-1.2.1
- spark-2.4.0-bin-hadoop2.7
- Apache-flume-1.9.0
- kafka_2.11-0.10.2.1
- apache-flume-1.6.0  

# 3. 环境配置

## 3.1 集群配置

集群节点分配

|           | 本地 |        hdp-01       |          hdp-02         |     hdp-03     |
|:---------:|:----:|:-------------------:|:-----------------------:|:--------------:|
|    HDFS   |      | NameNode、 DataNode |         DataNode        |    DataNode    |
| Zookeeper |      |    QuorumPeerMain   |      QuorumPeerMain     | QuorumPeerMain |
|   HBase   |      |    HRegionServer    | HMaster、 HRegionServer |  HRegionServer |
|   Kafka   |      |        Kafka        |          Kafka          |      Kafka     |
| SparkStreaming   | Main |                     |                         |                |
|   Flume   |   √  |                     |                         |                |
|    log    |   √  |                     |                         |                |
|           |      |                     |                         |                |

HBase 需要依赖 HDFS 和 Zookeeper,按顺序启动集群：
```
HDFS->Zookeeper->HBase->Kafka->Spark
```


## 3.2 创建HBase表
HBase表需要提前创建好
```shell
bin/start-hbase.sh
bin/hbase shell
```
```
创建名字空间 ns1：create_namespace 'ns1'
创建课程访问统计表，列族为info：create 'ns1:courses_clickcount', 'info'
创建搜索引擎统计表，列族为info：create 'ns1:courses_search_clickcount', 'info'
```

## 3.3 flume配置文件

streaming_log.conf
```
exec-memory-kafka.sources = exec-source  #exec的源，用于监控某个文件是否有     数据追加
exec-memory-kafka.sinks = kafka-sink
exec-memory-kafka.channels = memory-channel
 
exec-memory-kafka.sources.exec-source.type = exec
exec-memory-kafka.sources.exec-source.command = tail -F /home/hadoop/data/click.log  #被监控的文件,目录必须正确
exec-memory-kafka.sources.exec-source.shell = /bin/sh -c
 
exec-memory-kafka.channels.memory-channel.type = memory 
 
exec-memory-kafka.sinks.kafka-sink.type = org.apache.flume.sink.kafka.KafkaSink 
exec-memory-kafka.sinks.kafka-sink.brokerList = hdp-01:9092,hdp-02:9092,hdp-03:9092
exec-memory-kafka.sinks.kafka-sink.topic = streamtopic
exec-memory-kafka.sinks.kafka-sink.batchSize = 10
exec-memory-kafka.sinks.kafka-sink.requiredAcks = 1
 
exec-memory-kafka.sources.exec-source.channels = memory-channel   #关联三大组件
exec-memory-kafka.sinks.kafka-sink.channel = memory-channel
```


## 3.4 创建Kafka主题
Kafka 的 topic 需要提前创建好
```shell
bin/kafka-topics.sh --create --zookeeper hdp-01:2181,hdp-02:2181,hdp-03:2181 --replication-factor 3 --partitions 3 --topic streamtopic
bin/kafka-topics.sh --list --zookeeper hdp-01:2181,hdp-02:2181,hdp-03:2181
bin/kafka-topics.sh --describe --zookeeper hdp-01:2181,hdp-02:2181,hdp-03:2181 --topic streamtopic
bin/kafka-console-consumer.sh --zookeeper hdp-01:2181,hdp-02:2181,hdp-03:2181 --topic streamtopic --from-beginning
```

# 4. 启动

## 4.1 生成日志
```shell
python script/generate.log
```
可以使用Linux的crontab命令，周期性调用python脚本，源源不断产生数据，
该脚本会在当前目录生成日志文件。
因此，python脚本与Flume配置文件中监控的目录必须要一直一致，否则将无法收集。

## 4.2 启动Kafka
所有机器执行：
```sbtshell
bin/kafka-server-start.sh -daemon config/server.properties
```

## 4.3 启动Flume

```shell
bin/flume-ng agent -n exec-memory-kafka -c ./conf -f conf/streaming_log.conf -Dflume.root.logger=DEBUG,console -no-reload-conf
```

## 4.4 启动SparkStreaming
运行
```
com.jp.spark.streaming.application.CountByStreaming
```
参数为：
```sbtshell
<kafkaAddr> <group> <topics> <threadNum>
```

启动之后，可以看到HBase中插入了数据：
```sbtshell
Hbase> list     
Hbase> scan 'ns1:courses_search_clickcount'
```
