/*
 * PlanServiceImpl.java created by Tianxin(tianjige@163.com) on 2015年6月15日 上午11:24:48
 */
package org.darwin.myweb.service;

import java.util.List;

import org.darwin.myweb.bo.Plan;
import org.darwin.myweb.bo.User;
import org.darwin.myweb.dao.PlanDao;
import org.darwin.myweb.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 计划服务接口实现
 */
@Service
public class PlanServiceImpl implements PlanService {

  @Autowired
  private PlanDao planDao;

  @Autowired
  private UserDao userDao;

  @Transactional
  public List<Plan> findAll() {
    return planDao.findAll();
  }

  public List<Plan> findAllShards() {
    // TODO 实现跨分库返回所有的 Plan 对象
    return null;
  }

  public List<Plan> findByUser(int userId) {
    return planDao.findByUser(userId);
  }

  @Transactional
  public void create(Plan plan) {

    //判断用户是否存在
    int userId = plan.getUserId();
    User user = userDao.get(userId);
    if (user == null) {
      throw new RuntimeException("用户" + userId + "不存在！");
    }
    System.out.println("当前用户为:" + user.getName());

    //创建plan
    planDao.create(plan);
    userDao.increasePlanCount(userId);
    System.out.println(plan.getId());
  }

}
