package org.darwin.shardingDataSource.dataSource;

import org.darwin.shardingDataSource.dataSource.rule.Selector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

/**
 * Description: 带有冗余的数据源
 * Created by xiongjie.wxj on 2017/7/27.
 */
public class RedundancyDataSource extends AbstractShardDataSource {

  private long checkInterval = 3L; // 默认 30 sec 检查一次延迟

  class PoolThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public PoolThreadFactory(String poolName) {
      SecurityManager s = System.getSecurityManager();
      group = (s != null) ? s.getThreadGroup() :
              Thread.currentThread().getThreadGroup();
      namePrefix = "pool-" +
              poolName + "-thread-";
    }

    public Thread newThread(Runnable r) {
      Thread t = new Thread(group, r,
              namePrefix + threadNumber.getAndIncrement(),
              0);
      if (t.isDaemon())
        t.setDaemon(false);
      if (t.getPriority() != Thread.NORM_PRIORITY)
        t.setPriority(Thread.NORM_PRIORITY);
      return t;
    }
  }

  private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new PoolThreadFactory("ds-monitor"));

  /**
   * 主库的数据源
   */
  private List<DataSource> availableDataSources = new ArrayList<>();

  private String dataSourceName;

  /**
   * 从库的数据源
   */
  private List<DataSource> dataSources = new ArrayList<>();

  private Selector selector;

  @PostConstruct
  private void monitor() {
    // new monitor thread do change available datasource
    logger.info("monitor start for " + dataSourceName);
    scheduler.scheduleWithFixedDelay(new Runnable() {
      @Override
      public void run() {
        try {
          logger.debug("monitor schedule for datasource " + dataSourceName);
          List<DataSource> newAvailableDs = new ArrayList<>();
          if (CollectionUtils.isEmpty(dataSources)) {
            return;
          }
          for (DataSource dataSource : dataSources) {
            if (checkState(dataSource)) {
              newAvailableDs.add(dataSource);
            } else {
              logger.warn("monitor data source not valid from " + dataSourceName);
            }
          }
          if (newAvailableDs.size() > 0) {
            if (newAvailableDs.size() < dataSources.size()) {
              logger.debug("monitor bad data source delete.");
            }
            availableDataSources = newAvailableDs;
          } else {
            logger.error("monitor all data source can not used from " + dataSourceName);
          }
        } catch (Throwable ex) {
          logger.error("monitor failed.");
          ex.printStackTrace();
        }

      }
    }, checkInterval, checkInterval, TimeUnit.SECONDS);

  }

  @PreDestroy
  public void stopWithoutTransaction() {
    scheduler.shutdownNow();
    logger.info("monitor task stoped " + dataSourceName);
  }

  @Override
  protected DataSource chooseSuitableDataSource() {
    return selector.getCurrentIndex(this);
  }

  @Override
  public String getDataSourceName() {
    return dataSourceName;
  }

  public void setDataSourceName(String dataSourceName) {
    this.dataSourceName = dataSourceName;
  }

  /**
   * 返回的是可用的数据源
   * @return
   */
  public List<DataSource> getDataSources() {
    return availableDataSources;
  }

  public void setDataSources(List<DataSource> dataSources) {
    this.dataSources = dataSources;

    // 初始默认所有的数据源都是可用的
    availableDataSources.addAll(dataSources);
  }

  public Selector getSelector() {
    return selector;
  }

  public void setSelector(Selector selector) {
    this.selector = selector;
  }

  private boolean checkState(DataSource dataSource) {
    boolean state = false;
    Connection con = null;
    try {
      con = dataSource.getConnection();
      PreparedStatement ps = con.prepareStatement("select 1;");
      ResultSet rs = ps.executeQuery();
      if (rs.next() && (rs.getInt(1) == 1)) {
        state = true;
      }
    } catch (SQLException e) {
      state = false;
      e.printStackTrace();
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return state;
  }

}
