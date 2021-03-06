/*
 * UserDao.java created by Tianxin(tianjige@163.com) on 2015年6月15日 下午2:09:40
 */
package org.darwin.myweb.dao;

import org.darwin.genericDao.dao.BaseDao;
import org.darwin.myweb.bo.User;

/**
 * 用户 DAO
 */
public interface UserDao extends BaseDao<Integer, User> {

  void increasePlanCount(int userId);

}
