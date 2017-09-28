package org.darwin.shardingDataSource.dataSource;

import org.springframework.jdbc.datasource.AbstractDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

/**
 * Description:
 * Created by xiongjie.wxj on 2017/7/26.
 */
public abstract class AbstractShardDataSource extends AbstractDataSource implements DataSourceCollector {

  /**
   * 选择合适的数据源,该方法确保返回不为空
   *
   * @return
   */
  protected abstract DataSource chooseSuitableDataSource();


  public abstract String getDataSourceName();

  /**
   * 存储线程上下文中的数据库shardkey
   *
   * @param shardKey 数据库分库使用的分库因子
   */
  public final void putShardKey(Long shardKey) {
    RouteContext.putShardKey(getDataSourceName(), shardKey);
  }

  /**
   * 获取存储线程上下文中的数据库shardKey
   *
   * @return 数据库分库使用的分库因子
   */
  public final Long getShardKey() {
    return RouteContext.getShardKey(getDataSourceName());
  }

  public final void clearShardKey() {
    RouteContext.clearShardKey(getDataSourceName());
  }

  /**
   * 存储数据库的访问主从属性
   *
   * @param dbName    访问的数据库名称
   * @param attribute 访问的主从数据库指定
   */
  public final void putVisitAttribute(String dbName, VisitAttribute attribute) {
    RouteContext.putVisitAttribute(dbName, attribute);
  }

  /**
   * 获取存储数据库的访问主从属性
   *
   * @param dbName 访问的数据库名称
   */
  public final VisitAttribute getVisitAttribute(String dbName) {
    return RouteContext.getVisitAttribute(dbName);
  }

  /**
   * 删除上下文中的dbName对应数据库的读写属性
   *
   */
  public final void clearVisitAttribute() {
    RouteContext.clearVisitAttribute(getDataSourceName());
  }

  /**
   * 清空线程上下文
   */
  public final void clean() {
    RouteContext.clean();
  }



  private static class RouteContext {

    /**
     * 线程上下文变量的持有者
     */
    private static final ThreadLocal<Map<String, Object>> CTX_HOLDER = new ThreadLocal<Map<String, Object>>();

    /**
     * 添加内容到线程上下文中
     *
     * @param key   关键字
     * @param value 值
     */
    private static final void putContext(String key, Object value) {
      Map<String, Object> ctx = CTX_HOLDER.get();
      if (ctx == null) {
        init();
        ctx = CTX_HOLDER.get();
      }
      ctx.put(key, value);
    }

    /**
     * 从线程上下文中获取内容
     *
     * @param key 关键字
     *
     * @return 关键字关联的值
     */
    private static final <T extends Object> T getContext(String key) {
      Map<String, Object> ctx = CTX_HOLDER.get();
      if (ctx == null) {
        return null;
      }
      @SuppressWarnings("unchecked")
      T result = (T) ctx.get(key);
      return result;
    }

    /**
     * 上下文中是否包含此key
     *
     * @param key 上下文关键字
     *
     * @return 是否包含，如果包含<code>key</code>则返回<code>true</code>
     */
    public static final boolean contains(String key) {
      Map<String, Object> ctx = CTX_HOLDER.get();
      if (ctx != null) {
        return ctx.containsKey(key);
      }
      return false;
    }

    /**
     * 存储线程上下文中的数据库shardkey
     * @param dataSourceName
     * @param shardKey 数据库分库使用的分库因子
     */
    public static final void putShardKey(String dataSourceName, Long shardKey) {
      putContext(SHARD_KEY + dataSourceName, shardKey);
    }

    /**
     * 获取存储线程上下文中的数据库shardKey
     *
     * @return 数据库分库使用的分库因子
     */
    public static final Long getShardKey(String dataSourceName) {
      return getContext(SHARD_KEY + dataSourceName);
    }

    /**
     * 删除上下文中的shardKey
     *
     */
    public static final void clearShardKey(String dataSourceName) {
      Map<String, Object> ctx = CTX_HOLDER.get();
      if (ctx != null) {
        ctx.remove(SHARD_KEY + dataSourceName);
      }
    }

    /**
     * 存储数据库的访问主从属性
     *
     * @param dbName    访问的数据库名称
     * @param attribute 访问的主从数据库指定
     */
    protected static final void putVisitAttribute(String dbName, VisitAttribute attribute) {
      putContext(VISIT_ATTRIBUTE_KEY + dbName, attribute);
    }

    /**
     * 获取存储数据库的访问主从属性
     *
     * @param dbName 访问的数据库名称
     */
    protected static final VisitAttribute getVisitAttribute(String dbName) {
      return getContext(VISIT_ATTRIBUTE_KEY + dbName);
    }

    /**
     * 删除上下文中的dbName对应数据库的读写属性
     *
     * @param dbName 数据库名称
     */
    protected static final void clearVisitAttribute(String dbName) {
      Map<String, Object> ctx = CTX_HOLDER.get();
      if (ctx != null) {
        ctx.remove(VISIT_ATTRIBUTE_KEY + dbName);
      }
    }

    /**
     * 清空线程上下文
     */
    protected static final void clean() {
      CTX_HOLDER.set(null);
    }

    /**
     * 初始化线程上下文
     */
    protected static final void init() {
      CTX_HOLDER.set(new HashMap<String, Object>());
    }

    private static final String SHARD_KEY = "SHARD_KEY";
    private static final String VISIT_ATTRIBUTE_KEY = "VISIT_ATTRIBUTE_KEY";
  }



  @Override
  public Connection getConnection() throws SQLException {
    return chooseSuitableDataSource().getConnection();
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return chooseSuitableDataSource().getConnection(username, password);
  }

  public enum VisitAttribute {

    MASTER(),
    SLAVE();

  }
}
