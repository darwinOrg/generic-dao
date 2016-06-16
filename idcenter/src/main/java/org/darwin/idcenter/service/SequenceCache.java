/**
 * org.darwin.idcenter.service.SequenceCache.java
 * created by Tianxin(tianjige@163.com) on 2015年11月25日 下午4:00:28
 */
package org.darwin.idcenter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sequence的缓存
 * <br/>created by Tianxin on 2015年11月25日 下午4:00:28
 */
public class SequenceCache {

  /**
   * Sequence记录的名字
   */
  private String sequenceName;

  /**
   * Sequence当前的缓存最大值，要注意改值为开区间，不能使用
   */
  private long maxValue;

  /**
   * 当前Sequence的值
   */
  private long currentValue;

  /**
   * 缓存的步长
   */
  private int step = 1;

  /**
   * 构造函数
   * @param sequenceName
   * @param currentValue
   * @param maxValue
   */
  public SequenceCache(String sequenceName) {
    this.sequenceName = sequenceName;
  }

  /**
   * 获取下一个值
   * @return
   * 下午4:44:49 created by Darwin(Tianxin)
   */
  synchronized long nextValue() {
    long value = currentValue < maxValue ? currentValue : -step;
    currentValue += step;
    return value;
  }

  /**
   * 获取下n个值
   * @param count
   * @return
   * 下午4:56:38 created by Darwin(Tianxin)
   */
  synchronized long[] nextValues(int count) {

    //计算当前缓存中的容量能力
    int capability = (int) (maxValue - currentValue);
    int arraySize = capability >= count ? count : capability;
    long[] values = new long[arraySize];
    for (int i = 0; i < arraySize; i++) {
      values[i] = currentValue++;
    }

    return values;
  }

  /**
   * 刷新次缓存条目
   * @param newFootLine
   * @param keepCount
   * 上午9:54:36 created by Darwin(Tianxin)
   */
  synchronized void refresh(long newFootLine, int step, long topLine) {
    this.step = step;
    this.currentValue = newFootLine;
    this.maxValue = topLine;
  }


  /**
   * 获取该缓存的名字
   * @return
   * 上午10:31:37 created by Darwin(Tianxin)
   */
  String getSequenceName() {
    return this.sequenceName;
  }

  /**
   * 缓存中不在有多余的可供使用的ID
   * @return
   * 下午1:44:06 created by Darwin(Tianxin)
   */
  boolean isEmpty() {
    return this.currentValue >= this.maxValue || this.currentValue <= 0;
  }

  public long getCurrentValue() {
    return currentValue;
  }

  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(SequenceCache.class);
}
