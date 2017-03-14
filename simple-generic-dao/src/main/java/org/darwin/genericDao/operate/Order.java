/**
 * org.darwin.genericDao.query.Order.java
 * created by Tianxin(tianjige@163.com) on 2015年5月27日 下午12:08:14
 */
package org.darwin.genericDao.operate;

import java.util.Collections;
import java.util.List;

import org.darwin.common.utils.Utils;

/**
 * created by Tianxin on 2015年5月27日 下午12:08:14
 */
public class Order implements Operate {

  /**
   * 返回一个以column的正向排序
   * created by Tianxin on 2015年5月27日 下午2:41:24
   */
  public static Order asc(String column) {
    return new Order(column, true);
  }

  /**
   * 返回一个以column的反向排序
   * created by Tianxin on 2015年5月27日 下午2:41:50
   */
  public static Order desc(String column) {
    return new Order(column, false);
  }

  private Order() {}

  private Order(String column, boolean asc) {
    checkColumn(column);
    this.column = column;
    this.asc = asc;
  }

  /**
   * 校验该字符串是否是一个合法的字段名
   * @param s
   * <br/>created by Tianxin on 2016年12月16日 上午11:23:16
   */
  private static void checkColumn(String s) {
    
    //不能为null
    if( s == null){
      throw new RuntimeException("order by column can not be null!");
    }
    
    //不能为空串
    s = s.trim();
    if(s.length() == 0){
      throw new RuntimeException("order by column can not be empty!");
    }
    
    //字符必须都合法
    for(int i = 0 ; i < s.length() - 1; i ++){
      char c = s.charAt(i);
      if((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122) || c == 95){
        continue;
      }
      throw new RuntimeException("illegal column value : " + s);
    }
  }

  private String column;
  private boolean asc;

  public String getOperate() {
    return asc ? column : Utils.concat(column, " desc");
  }

  public List<Object> getParams() {
    return Collections.emptyList();
  }

  public boolean isEmpty() {
    return false;
  }
}
