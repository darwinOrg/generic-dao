/**
 * org.darwin.genericDao.annotations.enums.ShardRule.java
 * created by Tianxin(tianjige@163.com) on 2016年6月29日 下午5:06:23
 */
package org.darwin.genericDao.annotations.enums;

import org.darwin.common.utils.Utils;
import org.darwin.genericDao.annotations.TableShardPolicy;


/**
 * 分表规则的枚举
 * <br/>created by Tianxin on 2016年6月29日 下午5:06:23
 */
public enum TableShardRule implements TableShardPolicy {

  /**
   * 最普通的表名规则，没有任何分库分表的
   */
  NORMAL {
    public String generateName(String db, String table, int shardCount, Object shardKey) {
      if (Utils.isEmpty(db)) {
        return table;
      }
      return Utils.concat(db, '.', table);
    }
  }, 
  
  /**
   * 只有table有后缀，db名没有后缀
   */
  OLNY_TABLE_MOD {
    public String generateName(String db, String table, int shardCount, Object shardKey) {
      
      //计算index
      int index = 0;
      if(shardKey instanceof Long){
        index = (int)((Long)shardKey % shardCount);
      }else{
        index = (Integer) shardKey % shardCount;
      }
      
      if (Utils.isEmpty(db)) {
        return Utils.concat(table, index);
      }
      return Utils.concat(db, '.', table, index);
    }
  }, 
  
  /**
   * db和table都有后缀，按常规模8的方式区分
   */
  DB_TABLE_MOD {
    public String generateName(String db, String table, int shardCount, Object shardKey) {
      
      //计算index
      int index = 0;
      if(shardKey instanceof Long){
        index = (int)((Long)shardKey % shardCount);
      }else{
        index = (Integer) shardKey % shardCount;
      }
      
      if (Utils.isEmpty(db)) {
        return Utils.concat(table, index);
      }
      return Utils.concat(db, index, '.', table, index);
    }
  }, 
  
  /**
   * TDDL的拼接方式
   */
  TDDL_MODE {
    public String generateName(String db, String table, int shardCount, Object shardKey) {
      
      //计算index
      int tableIndex = 0;
      int dbIndex = 0;
      if(shardKey instanceof Long){
        tableIndex = (int)((Long)shardKey % shardCount);
        dbIndex = (int)((Long)shardKey % 8);
      }else{
        tableIndex = (Integer) shardKey % shardCount;
        dbIndex = (int)((Integer)shardKey % 8);
      }
      
      
      StringBuilder sb = new StringBuilder().append(10000 + tableIndex);
      sb.setCharAt(0, '_');
      String tableSurffix = sb.toString();
      
      if (Utils.isEmpty(db)) {
        return Utils.concat(table, tableSurffix);
      }
      return Utils.concat(db, "_000", dbIndex, '.', table, tableSurffix);
    }
  },
  
  /**
   * OPLOG的拼接方式
   */
  OPLOG_MODE {
    public String generateName(String db, String table, int shardCount, Object shardKey) {
      
      //计算index
      int tableIndex = 0;
      if(shardKey instanceof Long){
        tableIndex = (int)((Long)shardKey % shardCount);
      }else{
        tableIndex = (Integer) shardKey % shardCount;
      }
      
      StringBuilder sb = new StringBuilder().append(10000 + tableIndex);
      sb.setCharAt(0, '_');
      String tableSurffix = sb.toString();
      
      if (Utils.isEmpty(db)) {
        return Utils.concat(table, tableSurffix);
      }
      return Utils.concat(db, '.', table, tableSurffix);
    }
  },
  
  /**
   * HONGQIANG的拼接方式
   */
  ONLY_SHARD_DB {
    public String generateName(String db, String table, int shardCount, Object shardKey) {
      
      //如果没有DB名字
      if (Utils.isEmpty(db)) {
        return table;
      }
      
      //计算index
      int dbIndex = 0;
      if(shardKey instanceof Long){
        dbIndex = (int)((Long)shardKey % 8);
      }else{
        dbIndex = (int)((Integer)shardKey % 8);
      }
      return Utils.concat(db, "_000", dbIndex, '.', table);
    }
  };
}
