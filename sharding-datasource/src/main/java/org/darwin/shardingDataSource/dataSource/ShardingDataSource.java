/**
 * org.darwin.shardingDataSource.dataSource.ShardingDataSource.java
 * created by Tianxin(tianjige@163.com) on 2015年6月8日 上午10:13:07
 */
package org.darwin.shardingDataSource.dataSource;

import org.darwin.shardingDataSource.dataSource.rule.Selector;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * 数据库分shard的数据源
 * created by Tianxin on 2015年6月8日 上午10:13:07
 */
public class ShardingDataSource extends AbstractShardDataSource {

  private int shardCount = 0;

  private String dataSourceName = "DEFAULT";

  @Override
  public List<DataSource> getDataSources() {
    return shardingDataSources;
  }

  /**
   * 数据源列表
   */
  private List<DataSource> shardingDataSources = null;

  /**
   * sharding的规则
   */
  private Selector selector;

  public void setShardingDataSources(List<DataSource> shardingDataSources) {

    if (shardingDataSources == null || shardingDataSources.size() <= 1) {
      throw new RuntimeException("不能使用切片的数据源,数据源个数比需要大于1! it is " + (shardingDataSources == null ? "null" : shardingDataSources.size()));
    }

    this.shardingDataSources = new ArrayList<DataSource>(shardingDataSources);
  }

  /**
   * 选择合适的数据源,该方法确保返回不为空
   * @return
   * created by Tianxin on 2015年6月8日 上午10:28:05
   */
  @Override
  protected final DataSource chooseSuitableDataSource() {
    return selector.getCurrentIndex(this);
  }

  public int getShardCount() {
    return shardCount;
  }

  public void setShardCount(int shardCount) {
    this.shardCount = shardCount;
  }

  public String getDataSourceName() {
    return dataSourceName;
  }

  public void setDataSourceName(String dataSourceName) {
    this.dataSourceName = dataSourceName;
  }

  public List<DataSource> getShardingDataSources() {
    return shardingDataSources;
  }

  public Selector getSelector() {
    return selector;
  }

  public void setSelector(Selector selector) {
    this.selector = selector;
  }


}
