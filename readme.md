# 1. 主要内容
这是一个大数据实时流处理分析系统 Demo，实现对用户日志的实时分析，采用 Flume + kafka + SparkStreaming + Hbase + SSM + Echarts 的架构。

主要内容包括：
- 编写 python 脚本，模拟源源不断产生网站的用户行为日志。
- 使用 Flume 收集产生的日志，并发送到 Kafka。
- 使用 Spark Streaming 消费 Kafka 的用户日志。
- Spark Streaming 将数据清洗过滤非法数据，然后分析日志中课程点击量和搜索引擎访问量。
- 将 Spark Streaming 处理的结果写入 HBase 中。
- 前端使用 Spring MVC、 Spring、 MyBatis 整合作为数据展示平台。
- 使用 Ajax 异步传输数据到 jsp 页面，并使用 Echarts 框架展示数据。
- 项目使用 IDEA 作为开发工具。
  
# 2. 工程结构
系统分为两个子系统：
- [日志实时分析系统 (LogAnlyize)](./LogAnalyize)：实时处理不断产生的用户日志，存入 HBase 中。
- [统计数据展示系统 (LogWeb)](./LogWeb)：从 HBase 中取出数据，通过 Web 页面展示。

# 3. 版本

1. 日志分析系统架构
- jdk1.8
- scala 2.11.8
- Hadoop-2.8.1
- zookeeper-3.4.6
- Hbase-1.2.1
- spark-2.4.0-bin-hadoop2.7
- Apache-flume-1.9.0
- kafka_2.11-0.10.2.1
- apache-flume-1.6.0   


2. 统计数据展示系统
- SpringBoot-2.2.1
- Spring MVC-4.3.3.RELEASE
- Spring-4.3.3.RELEASE
- MyBatis-3.2.1
- h2-1.4.192
- Echarts 4.3.0