/*
 * PlanDaoImpl.java created by Tianxin(tianjige@163.com) on 2015年6月15日 上午11:22:43
 */
package org.darwin.myweb.dao;

import java.util.List;

import org.darwin.genericDao.bo.UserObject.Columns;
import org.darwin.genericDao.dao.impl.GenericDao;
import org.darwin.myweb.bo.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 计划 DAO 实现
 */
@Repository
public class PlanDaoImpl extends GenericDao<Integer, Plan> implements PlanDao {

  @Override
  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    super.setJdbcTemplate(jdbcTemplate);
  }

  public List<Plan> findByUser(int userId) {
    return find(Columns.userId, userId);
  }
  
  

}
