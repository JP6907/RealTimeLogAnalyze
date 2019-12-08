package com.jp.streaminglog.Utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HBaseUtils {

    private Configuration configuration = null;
    private Connection connection = null;
    private static HBaseUtils hbaseInstance = null;

    public HBaseUtils() {
        try{
            configuration = new Configuration();
            configuration.set("hbase.zookeeper.quorum", "hdp-01:2181,hdp-02:2181,hdp-03:2181");
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

    /**
     * 根据日期查询统计结果
     * @param tableName
     * @param date
     * @return
     */
    public Map<String,Long> getClickCount(String tableName,String date){
        Map<String,Long> map = new HashMap<String,Long>();
        try{
            HTable table = getInstance().getTable(tableName);
            //列族
            String cf = "info";
            //列
            String qualifier = "click_count";
            //前缀过滤器，只扫描给定日期的row
            Filter filter = new PrefixFilter(Bytes.toBytes(date));
            Scan scan = new Scan();
            scan.setFilter(filter);
            ResultScanner results = table.getScanner(scan);
            for(Result result : results){
                String rowKey = Bytes.toString(result.getRow());
                Long clickCount = Bytes.toLong(result.getValue(cf.getBytes(),qualifier.getBytes()));
                map.put(rowKey,clickCount);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return map;
    }


}
