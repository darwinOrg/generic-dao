/**
 * org.darwin.myweb.dao.UserDaoImpl.java
 * created by Tianxin(tianjige@163.com) on 2015年6月15日 下午2:10:52
 */
package org.darwin.myweb.dao;

import javax.annotation.Resource;

import org.darwin.genericDao.dao.impl.GenericDao;
import org.darwin.genericDao.operate.Matches;
import org.darwin.genericDao.operate.Modifies;
import org.darwin.genericDao.param.SQLParams;
import org.darwin.myweb.bo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 
 * created by Tianxin on 2015年6月15日 下午2:10:52
 */
@Repository
public class UserDaoImpl extends GenericDao<Integer, User> implements UserDao {
  
  
  @Override  
  @Resource(name = "capJdbcTemplate")
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    super.setJdbcTemplate(jdbcTemplate);
  }
  
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

  public void increasePlanCount(int userId) {
    update(Modifies.init().modify("plan_count", SQLParams.increase(1)), Matches.one("id", userId));
    
  }
}
