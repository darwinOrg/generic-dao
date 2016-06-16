/**
 * org.darwin.idcenter.IDService.java
 * created by Tianxin(tianjige@163.com) on 2015年8月6日 下午12:51:12
 */
package org.darwin.idcenter.service;

import java.util.Map;

/**
 * 获取ID的服务接口
 * <br/>created by Tianxin on 2015年8月6日 下午12:51:12
 */
public interface IDService {
  
  /**
   * 获取新的ID
   * @param sequence
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午12:58:50
   */
  long nextValue(String sequence);
  
  /**
   * 获取count个新的ID
   * @param sequence
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午12:59:08
   */
  long[] nextValues(String sequence, int count);
  
  /**
   * 一次从多个Key中获取ID
   * @param sequenceSizes
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午1:00:54
   */
  Map<String, long[]> nextValues(Map<String, Integer> sequenceSizes);
  
  /**
   * 从一个key中获取ID，采用压缩方式返回
   * @param sequence
   * @param count
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午1:01:18
   */
  CompressedIds nextCompressedValues(String sequence, int count);
  
  /**
   * 获取下一个int类型的ID
   * @param sequence
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午1:01:38
   */
  int nextIntValue(String sequence);
  
  /**
   * 获取count个新的ID
   * @param sequence
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午1:01:52
   */
  int[] nextIntValues(String sequence, int count);
  
  /**
   * 获取多个sequence的id
   * @param sequenceSizes
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午1:01:56
   */
  Map<String, int[]> nextIntValues(Map<String, Integer> sequenceSizes);
}
