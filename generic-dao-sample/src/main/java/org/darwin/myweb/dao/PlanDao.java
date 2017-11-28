/**
 * org.darwin.myweb.dao.PlanDao.java
 * created by Tianxin(tianjige@163.com) on 2015年6月15日 上午11:22:17
 */
package org.darwin.myweb.dao;

import java.util.List;

import org.darwin.genericDao.dao.BaseDao;
import org.darwin.myweb.bo.Plan;

/**
 * 计划的DAO
 * created by Tianxin on 2015年6月15日 上午11:22:17
 */
public interface PlanDao extends BaseDao<Integer, Plan> {
  
  List<Plan> findByUser(int userId);

}
