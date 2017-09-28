/**
 * org.darwin.shardingDataSource.dataSource.ReadWriteSplitingDataSource.java
 * created by Tianxin(tianjige@163.com) on 2015年6月8日 上午10:13:58
 */
package org.darwin.shardingDataSource.dataSource;

import org.darwin.shardingDataSource.dataSource.rule.Selector;
import org.darwin.shardingDataSource.dataSource.rule.impl.ReadWriteSplitingSelector;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * 数据库作为读写分离的数据源
 * created by Tianxin on 2015年6月8日 上午10:13:58
 */
public class ReadWriteSplitingDataSource extends AbstractShardDataSource {

  public ReadWriteSplitingDataSource(String dataSourceName) {
    this.dataSourceName = dataSourceName;
    this.selector = new ReadWriteSplitingSelector(dataSourceName);
  }

  /**
   * 选择合适的数据源,该方法确保返回不为空
   * @return
   * created by Tianxin on 2015年6月8日 上午10:28:05
   */
  protected final DataSource chooseSuitableDataSource() {
    return selector.getCurrentIndex(this);
  }

  @Override
  public List<DataSource> getDataSources() {
    return dataSources;
  }

  /**
   * 主库的数据源
   */
  private DataSource masterDataSource;

  /**
   * 从库的数据源列表。
   */
  private DataSource slaveDataSource;

  /**
   * 库的选取策略
   */
  private Selector selector = null;

  public void setMasterDataSource(DataSource masterDataSource) {
    this.masterDataSource = masterDataSource;
    dataSources.add(0, slaveDataSource);
  }

  public void setSlaveDataSource(DataSource slaveDataSource) {
    this.slaveDataSource = slaveDataSource;
    dataSources.add(1, slaveDataSource);
  }

  private String dataSourceName = "DEFAULT";
  private List<DataSource> dataSources = new ArrayList<>(2);

//  private final static int RANDOM = 0;
//  private final static int BY_ORDER = 1;
//  private final static int BY_WEIGHT = 2;

  public String getDataSourceName() {
    return dataSourceName;
  }

  public void setDataSourceName(String dataSourceName) {
    this.dataSourceName = dataSourceName;
  }

  public DataSource getMasterDataSource() {
    return masterDataSource;
  }

  public DataSource getSlaveDataSource() {
    return slaveDataSource;
  }

}
