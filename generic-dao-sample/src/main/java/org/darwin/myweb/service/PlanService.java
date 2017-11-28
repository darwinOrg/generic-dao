/**
 * org.darwin.myweb.service.PlanService.java
 * created by Tianxin(tianjige@163.com) on 2015年6月15日 上午11:24:22
 */
package org.darwin.myweb.service;

import java.util.List;

import org.darwin.myweb.bo.Plan;


/**
 * 加护的服务接口
 * created by Tianxin on 2015年6月15日 上午11:24:22
 */
public interface PlanService {
  
  List<Plan> findAll();
  
  List<Plan> findByUser(int userId);
  
  void create(Plan plan);

  /**
   * @return
   * created by Tianxin on 2015年6月15日 下午8:35:13
   */
  List<Plan> findAllShards();
}
