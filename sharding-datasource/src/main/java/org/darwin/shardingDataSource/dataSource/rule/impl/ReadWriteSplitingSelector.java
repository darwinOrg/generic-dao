package org.darwin.shardingDataSource.dataSource.rule.impl;

import org.darwin.shardingDataSource.dataSource.AbstractShardDataSource;
import org.darwin.shardingDataSource.dataSource.rule.Selector;

import java.util.List;

import javax.sql.DataSource;

/**
 * Description:
 * Created by xiongjie.wxj on 2017/7/28.
 */
public class ReadWriteSplitingSelector implements Selector {

  public ReadWriteSplitingSelector(String dataBaseName) {
    this.dataBaseName = dataBaseName;
  }

  private String dataBaseName = "DEFAULT";

  @Override
  public DataSource getCurrentIndex(AbstractShardDataSource dataSource) {

    List<DataSource> dataSources = dataSource.getDataSources();
    if(dataSource.getVisitAttribute(dataBaseName) == AbstractShardDataSource.VisitAttribute.SLAVE && dataSources.size() >= 2) {
      dataSources.get(1);
    }
    return dataSources.get(0);
  }

  public String getDataBaseName() {
    return dataBaseName;
  }

  public void setDataBaseName(String dataBaseName) {
    this.dataBaseName = dataBaseName;
  }
}
