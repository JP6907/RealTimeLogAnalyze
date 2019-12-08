package com.jp.spark.streaming.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;

import java.io.IOException;


/**
 * HBase 工具类
 * 连接HBase
 */
public class HBaseUtils {

    private Configuration configuration = null;
    private Connection connection = null;
    private static HBaseUtils hbaseInstance = null;

    public HBaseUtils() {
        try{
            configuration = new Configuration();
            configuration.set("hbase.zookeeper.quorum", "hdp-01:2181");
            connection = ConnectionFactory.createConnection(configuration);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取HBase连接实例
     * @return
     */
    public static HBaseUtils getInstance(){
        if(hbaseInstance==null){
            synchronized (HBaseUtils.class){
                if(hbaseInstance==null) {
                    hbaseInstance = new HBaseUtils();
                }
            }
        }
        return hbaseInstance;
    }

    /**
     * 根据表名获得一个表的实例
     * @param tableName
     * @return
     */
    public HTable getTable(String tableName){
        HTable hTable = null;
        try {
            hTable = (HTable)connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hTable;
    }

    private void createTable(String tableName){

    }

    private void deleteTable(String tableName){

    }
}
