/**
 * org.darwin.shardingDataSource.transaction.ShardingTransactionManger.java
 * created by Tianxin(tianjige@163.com) on 2015年6月15日 下午1:39:43
 */
package org.darwin.shardingDataSource.transaction;

import javax.sql.DataSource;

import org.darwin.common.ThreadContext;
import org.darwin.shardingDataSource.dataSource.AbstractShardDataSource;
import org.darwin.shardingDataSource.exception.DataSourceRoutingException;
import org.darwin.shardingDataSource.shardKey.ShardKeyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * 
 * created by Tianxin on 2015年6月15日 下午1:39:43
 */
public class ShardingTransactionManager extends DataSourceTransactionManager {
  /**
   * 
   * created by Tianxin on 2015年6月15日 下午1:41:36
   */
  private static final long serialVersionUID = 1L;
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(ShardingTransactionManager.class);

  private ShardKeyProvider shardKeyProvider = new ShardKeyProvider() {
    @Override
    public Long providerShardKey() {
      return (Long) ThreadContext.getShardingKey();
    }
  };

  @Override
  public void setDataSource(DataSource dataSource) {
    super.setDataSource(dataSource);
  }

  @Override
  protected void doBegin(Object transaction, TransactionDefinition definition) {

    doInitialBeforeBegin(transaction, definition);

    super.doBegin(transaction, definition);
  }

  /**
   * 初始化事务的读写属性，依据是transaction的readonly属性设置
   *
   * @param transaction transaction object
   * @param definition  transaction definition
   */
  private void doInitialBeforeBegin(Object transaction, TransactionDefinition definition) {

    DataSource ds = getDataSource();
    if (!(ds instanceof AbstractShardDataSource)) {
      throw new DataSourceRoutingException("data source is not a ShardingDataSource");
    }

    AbstractShardDataSource vds = (AbstractShardDataSource) ds;
    Long shardKey = fetchShardKey();
    if (shardKey != null) {
      vds.putShardKey(shardKey);
    } else {
      LOG.warn("no shardkey found in threadlocal for datasource {}.", vds.getDataSourceName());
      vds.putShardKey(shardKey);
    }

    if (definition.isReadOnly()) {
      vds.putVisitAttribute(vds.getDataSourceName(), AbstractShardDataSource.VisitAttribute.SLAVE);
    } else {
      vds.putVisitAttribute(vds.getDataSourceName(), AbstractShardDataSource.VisitAttribute.MASTER);
    }
  }

  private Long fetchShardKey() {
    return shardKeyProvider.providerShardKey();
  }

  @Override
  protected void doCleanupAfterCompletion(Object transaction) {

    DataSource ds = getDataSource();
    if (!(ds instanceof AbstractShardDataSource)) {
      super.doCleanupAfterCompletion(transaction);
      throw new DataSourceRoutingException("data source is not a VirtualDataSource");
    }

    AbstractShardDataSource vds = (AbstractShardDataSource) ds;
    vds.clearShardKey();
    vds.clearVisitAttribute();

    super.doCleanupAfterCompletion(transaction);

  }

  public ShardKeyProvider getShardKeyProvider() {
    return shardKeyProvider;
  }

  public void setShardKeyProvider(ShardKeyProvider shardKeyProvider) {
    this.shardKeyProvider = shardKeyProvider;
  }
}
