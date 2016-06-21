/**
 * org.darwin.sample.AbstractWlDao.java
 * created by Tianxin(tianjige@163.com) on 2016年6月16日 下午5:31:00
 */
package org.darwin.sample;

import java.io.Serializable;

import javax.annotation.Resource;

import org.darwin.common.utils.Utils;
import org.darwin.genericDao.annotations.Table;
import org.darwin.genericDao.bo.BaseObject;
import org.darwin.genericDao.dao.impl.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * <br/>created by Tianxin on 2016年6月16日 下午5:31:00
 */
public class AbstractWlDao<KEY extends Serializable, ENTITY extends BaseObject<KEY>> extends GenericDao<KEY, ENTITY> {

  @Override
  protected String generateShardTableName(Object shardKey, Table table) {

    Long lShardKey = (Long) shardKey;
    if (Utils.isEmpty(table.db())) {
      return Utils.concat(table.name(), "000", lShardKey % table.shardCount());
    }
    return Utils.concat(table.db(), "000", lShardKey % table.shardCount(), '.', table.name(), "000", lShardKey % table.shardCount());
  }

  @Resource(name = "wolongJdbcTemplate")
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    super.setJdbcTemplate(jdbcTemplate);
  }

  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(AbstractWlDao.class);
}
