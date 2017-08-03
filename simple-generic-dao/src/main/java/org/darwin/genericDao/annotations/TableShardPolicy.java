package org.darwin.genericDao.annotations;

/**
 *
 * 分库分布策略，实现必须是线程安全的
 *
 * Created by hexiufeng on 2017/8/3.
 */
public interface TableShardPolicy {
  /**
   * 生成表名字
   * @param db
   * @param table
   * @param shardKey
   * @return
   * <br/>created by Tianxin on 2016年6月29日 下午5:13:32
   */
  String generateName(String db, String table, int shardCount, Object shardKey);
}
