/**
 * org.darwin.myweb.dao.PlanDaoImpl.java
 * created by Tianxin(tianjige@163.com) on 2015年6月15日 上午11:22:43
 */
package org.darwin.myweb.dao;

import java.util.List;

import org.darwin.common.utils.Utils;
import org.darwin.genericDao.annotations.Table;
import org.darwin.genericDao.bo.UserObject.Columns;
import org.darwin.genericDao.dao.impl.GenericDao;
import org.darwin.myweb.bo.Plan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * DAO的实现
 * created by Tianxin on 2015年6月15日 上午11:22:43
 */
@Repository
public class PlanDaoImpl extends GenericDao<Integer, Plan> implements PlanDao{
  
  @Override
  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    super.setJdbcTemplate(jdbcTemplate);
  }
  
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(PlanDaoImpl.class);

  public List<Plan> findByUser(int userId) {
    return find(Columns.userId, userId);
  }
}
