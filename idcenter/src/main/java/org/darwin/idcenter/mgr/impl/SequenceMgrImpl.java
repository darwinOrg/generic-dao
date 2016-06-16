/**
 * org.darwin.idcenter.mgr.impl.SequenceMgrImpl.java
 * created by Tianxin(tianjige@163.com) on 2015年8月6日 下午1:50:45
 */
package org.darwin.idcenter.mgr.impl;

import java.util.Date;
import java.util.List;

import org.darwin.common.utils.DateUtils;
import org.darwin.common.utils.Utils;
import org.darwin.idcenter.bo.Sequence;
import org.darwin.idcenter.dao.SequenceDao;
import org.darwin.idcenter.mgr.SequenceMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * sequence的mgr层实现
 * <br/>created by Tianxin on 2015年8月6日 下午1:50:45
 */
@Component
public class SequenceMgrImpl implements SequenceMgr{
  
  @Autowired
  private SequenceDao sequenceDao;
  
  /**
   * 更新DB的最多重试次数
   */
  private int maxRetryTimes = 3;
  
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(SequenceMgrImpl.class);

  /**
   * 从数据库中获取相应的值
   * @param seq
   * @param size
   * @param retryTimes  标明本次是第几次重试
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午5:22:53
   */
  private long nextValue(Sequence seq, int size, int retryTimes) {
    
    //条件
    int id = seq.getId();
    Date version = seq.getLastModified();
    
    //计算目标值
    long curValue = seq.getCurValue();
    int step = seq.getStep();
    long newValue = curValue + size * step;
    
    //如果成功，返回新的值
    boolean success = sequenceDao.modifyCurValue(newValue, id, curValue, version);
    if(success){
      return newValue;
    }

    //如果已经达到最大重试次数
    if(retryTimes == maxRetryTimes){
      throw new RuntimeException(Utils.connectBySplit(", ", seq.getSeqName(), seq.getCurValue(), DateUtils.format(seq.getLastModified())));
    }
    
    //进行重试
    retryTimes += 1;
    Sequence newSeq = sequenceDao.get(id);
    return nextValue(newSeq, size, retryTimes);
  }

  public long getNextValues(String seqName, int size) {
    Sequence seq = sequenceDao.getByName(seqName);
    return nextValue(seq, size, 0);
  }

  public void setMaxRetryTimes(int maxRetryTimes) {
    this.maxRetryTimes = maxRetryTimes;
  }

  public void createSequence(String seqName, int curValue, int step) {
    Sequence sequence = new Sequence();
    sequence.setCurValue(curValue);
    sequence.setLastModified(new Date());
    sequence.setSeqName(seqName);
    sequence.setStep(step);
    sequenceDao.create(sequence);
  }

  public boolean modifyStep(int seqId, int step) {
    return sequenceDao.modifyStep(seqId, step);
  }

  public List<Sequence> findAll() {
    return sequenceDao.findAll();
  }
}
