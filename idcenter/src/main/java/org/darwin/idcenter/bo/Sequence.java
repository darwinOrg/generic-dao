/**
 * org.darwin.idcenter.bo.Sequence.java
 * created by Tianxin(tianjige@163.com) on 2015年8月6日 下午1:39:33
 */
package org.darwin.idcenter.bo;

import java.util.Date;

import org.darwin.genericDao.annotations.Table;
import org.darwin.genericDao.annotations.enums.ColumnStyle;
import org.darwin.genericDao.bo.BaseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个sequence对象
 * <br/>created by Tianxin on 2015年8月6日 下午1:39:33
 */
@Table(db = "sequence", name = "sequence", columnStyle = ColumnStyle.JAVA_TO_MYSQL)
public class Sequence extends BaseObject<Integer> {
  
  /**
   * sequence的名称
   */
  private String seqName;
  
  /**
   * sequence增长的步长
   */
  private int step;
  
  /**
   * 当前该sequence的值
   */
  private long curValue;
  
  /**
   * 最后更新时间
   */
  private Date lastModified;
  
  /**
   * 客户端缓存的数量
   */
  private int cacheSize;
  
  public String getSeqName() {
    return seqName;
  }

  public void setSeqName(String seqName) {
    this.seqName = seqName;
  }

  public int getStep() {
    return step;
  }

  public void setStep(int step) {
    this.step = step;
  }

  public long getCurValue() {
    return curValue;
  }

  public void setCurValue(long curValue) {
    this.curValue = curValue;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }
  
  public int getCacheSize() {
    return cacheSize;
  }

  public void setCacheSize(int cacheSize) {
    this.cacheSize = cacheSize;
  }

  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(Sequence.class);
}
