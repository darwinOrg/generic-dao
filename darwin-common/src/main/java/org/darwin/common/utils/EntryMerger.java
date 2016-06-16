/**
 * org.darwin.common.utils.EntryMerger.java
 * created by Tianxin(tianjige@163.com) on 2015年8月10日 下午2:50:19
 */
package org.darwin.common.utils;

/**
 * 对value进行merge的一个一个操作符
 * <br/>created by Tianxin on 2015年8月10日 下午2:50:19
 */
public interface EntryMerger<ENTITY, K, V> extends KeyGetter<K, ENTITY> {
  
  /**
   * 获取entity中的value，并与之前的value对比，获取出新的向map中put的value 
   * @param entity
   * @param oldValue
   * @return
   * <br/>created by Tianxin on 2015年8月10日 下午2:52:34
   */
  V getValue(ENTITY entity, V oldValue);
}
