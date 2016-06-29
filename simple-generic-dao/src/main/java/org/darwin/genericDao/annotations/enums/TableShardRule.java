/**
 * org.darwin.genericDao.annotations.enums.ShardRule.java
 * created by Tianxin(tianjige@163.com) on 2016年6月29日 下午5:06:23
 */
package org.darwin.genericDao.annotations.enums;

import org.darwin.common.utils.Utils;


/**
 * 分表规则的枚举
 * <br/>created by Tianxin on 2016年6月29日 下午5:06:23
 */
public enum TableShardRule {

  /**
   * 最普通的表名规则，没有任何分库分表的
   */
  NORMAL {
    @Override
    public String generateName(String db, String table, int shardCount, Object shardKey) {
      if (Utils.isEmpty(db)) {
        return table;
      }
      return Utils.concat(db, '.', table);
    }
  };

  /**
   * 生成表名字
   * @param db
   * @param table
   * @param shardKey
   * @return
   * <br/>created by Tianxin on 2016年6月29日 下午5:13:32
   */
  public abstract String generateName(String db, String table, int shardCount, Object shardKey);
}
