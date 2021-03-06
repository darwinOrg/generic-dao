/**
 * org.darwin.genericDao.query.Operate.java
 * created by Tianxin(tianjige@163.com) on 2015年5月27日 下午2:15:15
 */
package org.darwin.genericDao.operate;

import org.darwin.genericDao.dao.ColumnNameConverter;

import java.util.List;

/**
 * created by Tianxin on 2015年5月27日 下午2:15:15
 */
public interface Operate {

  /**
   * 该操作对应的SQL中的操作字符串
   * @param columnNameConverter
   * created by Tianxin on 2015年5月27日 下午2:40:24
   * modified by hexiufeng to support fieldName as query column name
   */
  String getOperate(ColumnNameConverter columnNameConverter);

  /**
   * 该操作在SQL执行时的param是哪些
   * created by Tianxin on 2015年5月27日 下午2:41:01
   */
  List<Object> getParams();

  /**
   * 这是否是一个空操作，比如一个matches可能没有任何条件等
   * @return
   * created by Tianxin on 2015年5月27日 下午6:10:47
   */
  boolean isEmpty();
}
