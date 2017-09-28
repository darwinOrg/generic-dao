package org.darwin.shardingDataSource.dataSource.rule.impl;

import org.darwin.common.ThreadContext;
import org.darwin.shardingDataSource.dataSource.AbstractShardDataSource;
import org.darwin.shardingDataSource.dataSource.rule.Selector;

import java.util.List;

import javax.sql.DataSource;

/**
 * Description:
 * Created by xiongjie.wxj on 2017/7/28.
 */
public class ModShardingSelector implements Selector {

  @Override
  public DataSource getCurrentIndex(AbstractShardDataSource dataSource) {

    List<DataSource> dataSources = dataSource.getDataSources();
    int shardCount = dataSources.size();
    if (shardCount <= 1) {
      throw new RuntimeException("切片数量小于2，不能使用切片数据源");
    }

    long shardingNum = dataSource.getShardKey();
    int index = (int)shardingNum % shardCount;
    return dataSources.get(index);
  }
}
