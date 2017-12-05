/*
 * PlanService.java created by Tianxin(tianjige@163.com) on 2015年6月15日 上午11:24:22
 */
package org.darwin.myweb.service;

import java.util.List;

import org.darwin.myweb.bo.Plan;

/**
 * 计划服务接口
 */
public interface PlanService {

  /**
   * 返回所有的 Plan 对象，如果底层分库，该方法没有正确实现
   *
   * @return  Plan 对象列表
   */
  List<Plan> findAll();

  /**
   * 返回所有分库的所有的 Plan 对象
   *
   * @return  Plan 对象列表
   */
  List<Plan> findAllShards();

  /**
   * 返回特定用户的所有 Plan 对象
   *
   * @param   userId  指定的用户ID
   * @return  Plan 对象列表
   */
  List<Plan> findByUser(int userId);

  /**
   * 创建 Plan 对象
   *
   * @param   plan  待创建的 Plan 对象
   */
  void create(Plan plan);

}
