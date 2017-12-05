/*
 * UserDaoImpl.java created by Tianxin(tianjige@163.com) on 2015年6月15日 下午2:10:52
 */
package org.darwin.myweb.dao;

import javax.annotation.Resource;

import org.darwin.genericDao.dao.impl.GenericDao;
import org.darwin.genericDao.operate.Matches;
import org.darwin.genericDao.operate.Modifies;
import org.darwin.genericDao.param.SQLParams;
import org.darwin.myweb.bo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 用户 DAO 实现
 */
@Repository
public class UserDaoImpl extends GenericDao<Integer, User> implements UserDao {


  @Override
  @Resource(name = "capJdbcTemplate")
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    super.setJdbcTemplate(jdbcTemplate);
  }

  public void increasePlanCount(int userId) {
    update(Modifies.init().modify("plan_count", SQLParams.increase(1)), Matches.one("id", userId));
  }

}
