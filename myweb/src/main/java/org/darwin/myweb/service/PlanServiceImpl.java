/**
 * org.darwin.myweb.service.PlanServiceImpl.java
 * created by Tianxin(tianjige@163.com) on 2015年6月15日 上午11:24:48
 */
package org.darwin.myweb.service;

import java.util.List;

import org.darwin.myweb.bo.Plan;
import org.darwin.myweb.bo.User;
import org.darwin.myweb.dao.AllPlanDao;
import org.darwin.myweb.dao.PlanDao;
import org.darwin.myweb.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务接口
 * created by Tianxin on 2015年6月15日 上午11:24:48
 */
@Service
public class PlanServiceImpl implements PlanService {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(PlanServiceImpl.class);

  @Autowired
  private PlanDao planDao;
  
  @Autowired
  private UserDao userDao;
  
  @Autowired
  private AllPlanDao allPlanDao;

  @Transactional
  public List<Plan> findAll() {
    return planDao.findAll();
  }

  @Transactional
  public void create(Plan plan) {
    
    //判断用户是否存在
    int userId = plan.getUserId();
    User user = userDao.get(userId);
    if(user == null){
      throw new RuntimeException("用户" + userId + "不存在！");
    }
    System.out.println("当前用户为:" + user.getName());
    
    //创建plan
    planDao.create(plan);
    userDao.increasePlanCount(userId);
    System.out.println(plan.getId());
  }

  public List<Plan> findAllShards() {
    return null;
  }

  public List<Plan> findByUser(int userId) {
    return planDao.findByUser(userId);
  }
}
