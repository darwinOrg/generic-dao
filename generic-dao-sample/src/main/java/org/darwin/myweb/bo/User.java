/*
 * User.java created by Tianxin(tianjige@163.com) on 2015年6月15日 下午2:08:08
 */
package org.darwin.myweb.bo;

import org.darwin.genericDao.annotations.Table;
import org.darwin.genericDao.annotations.enums.ColumnStyle;
import org.darwin.genericDao.bo.BaseObject;

/**
 * 测试用的 User 对象
 */
@Table(db = "cap", name = "user", columnStyle = ColumnStyle.LOWER_CASE)
public class User extends BaseObject<Integer> {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
