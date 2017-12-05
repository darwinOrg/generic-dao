/*
 * Plan.java created by Tianxin(tianjige@163.com) on 2015年6月15日 上午11:19:56
 */
package org.darwin.myweb.bo;

import java.util.Date;

import org.darwin.genericDao.annotations.Table;
import org.darwin.genericDao.annotations.enums.ColumnStyle;
import org.darwin.genericDao.bo.BaseObject;
import org.darwin.tools.CreateTableHelper;

/**
 * 测试用的 Plan 对象
 */
@Table(db = "darwin", name = "plan", columnStyle = ColumnStyle.LOWER_CASE, shardCount = 2)
public class Plan extends BaseObject<Integer> {

  private String name;
  private int userId;
  private int status;
  private int addUser;
  private int modUser;
  private Date addTime;
  private Date modTime;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getAddUser() {
    return addUser;
  }

  public void setAddUser(int addUser) {
    this.addUser = addUser;
  }

  public int getModUser() {
    return modUser;
  }

  public void setModUser(int modUser) {
    this.modUser = modUser;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  public Date getModTime() {
    return modTime;
  }

  public void setModTime(Date modTime) {
    this.modTime = modTime;
  }

  /**
   * 根据用户ID和用户名创建 Plan 对象
   *
   * @param userId 用户ID
   * @param name   用户名
   * @return 创建的 Plan 对象
   */
  public static Plan from(int userId, String name) {
    Plan plan = new Plan();
    plan.setUserId(userId);
    plan.setName(name);
    plan.setStatus(1);
    plan.setAddTime(new Date());
    plan.setModTime(new Date());
    plan.setAddUser(userId);
    plan.setModUser(userId);
    return plan;
  }

  public static void main(String[] args) {
    CreateTableHelper.generateCreateTableSQL(Plan.class);
    CreateTableHelper.generateCreateTableSQL(User.class);
  }

}
