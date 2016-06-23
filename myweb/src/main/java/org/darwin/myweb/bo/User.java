/**
 * org.darwin.myweb.bo.User.java
 * created by Tianxin(tianjige@163.com) on 2015年6月15日 下午2:08:08
 */
package org.darwin.myweb.bo;

import org.darwin.genericDao.annotations.Table;
import org.darwin.genericDao.annotations.enums.ColumnStyle;
import org.darwin.genericDao.bo.BaseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户对象
 * created by Tianxin on 2015年6月15日 下午2:08:08
 */
@Table(db = "cap", name = "user", columnStyle = ColumnStyle.LOWER_CASE)
public class User extends BaseObject<Integer>{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(User.class);

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
