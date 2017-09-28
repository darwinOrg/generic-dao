/**
 * org.darwin.shardingDataSource.dataSource.rule.Selector.java
 * created by Tianxin(tianjige@163.com) on 2015年6月8日 上午10:58:48
 */
package org.darwin.shardingDataSource.dataSource.rule;

import org.darwin.shardingDataSource.dataSource.AbstractShardDataSource;

import javax.sql.DataSource;

/**
 * 多数据源的选取规则
 * created by Tianxin on 2015年6月8日 上午10:58:48
 */
public interface Selector {

  /**
   * 获取本次选取的库的index
   * @return
   * created by Tianxin on 2015年6月8日 上午10:59:15
   */
  DataSource getCurrentIndex(AbstractShardDataSource dataSource);
}
