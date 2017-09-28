package org.darwin.shardingDataSource.dataSource.rule.impl;

import org.darwin.shardingDataSource.dataSource.AbstractShardDataSource;
import org.darwin.shardingDataSource.dataSource.rule.Selector;

import javax.sql.DataSource;

/**
 * Description: 优先级选择器，始终选择第一个
 * Created by xiongjie.wxj on 2017/7/28.
 */
public class PrioritySelector implements Selector {
  @Override
  public DataSource getCurrentIndex(AbstractShardDataSource dataSource) {
    return dataSource.getDataSources().get(0);
  }
}
