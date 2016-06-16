/**
 * org.darwin.common.utils.FilterSort.java
 * created by Tianxin(tianjige@163.com) on 2015年9月1日 下午12:04:44
 */
package org.darwin.common.utils;

import java.util.Comparator;


/**
 * 负责过滤与排序的对象，一般联合Utils.pageAndFilter使用
 * <br/>created by Tianxin on 2015年9月1日 下午12:04:44
 */
public interface FilterSort<E> extends Comparator<E>{
  
  /**
   * 判断E是否符合筛选需求
   * @param e
   * @return
   * <br/>created by Tianxin on 2015年9月1日 下午12:21:29
   */
  boolean accept(E e);

}
