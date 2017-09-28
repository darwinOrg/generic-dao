/**
 * org.darwin.shardingDataSource.dataSource.rule.RandomSelector.java
 * created by Tianxin(tianjige@163.com) on 2015年6月8日 上午11:06:38
 */
package org.darwin.shardingDataSource.dataSource.rule.impl;

import java.util.List;
import java.util.Random;

import org.darwin.shardingDataSource.dataSource.AbstractShardDataSource;
import org.darwin.shardingDataSource.dataSource.rule.Selector;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

/**
 * 从库的随机选择器
 * created by Tianxin on 2015年6月8日 上午11:06:38
 */
public class RandomSelector implements Selector {

  public RandomSelector() {}

  /**
   * 随机选取器
   */
  private Random random = new Random();

  @Override
  public DataSource getCurrentIndex(AbstractShardDataSource dataSource) {
    List<DataSource> dataSources = dataSource.getDataSources();
    int index = random.nextInt(dataSources.size());
    return dataSources.get(index);
  }

}
