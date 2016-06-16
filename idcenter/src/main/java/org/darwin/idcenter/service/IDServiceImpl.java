/**
 * org.darwin.idcenter.impl.IDServiceImpl.java
 * created by Tianxin(tianjige@163.com) on 2015年8月6日 下午1:06:49
 */
package org.darwin.idcenter.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.darwin.common.utils.DateUtils;
import org.darwin.common.utils.Utils;
import org.darwin.idcenter.bo.Sequence;
import org.darwin.idcenter.dao.SequenceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 最终对外暴露的ID服务对象
 * <br/>created by Tianxin on 2015年8月6日 下午1:06:49
 */
@Service
public class IDServiceImpl implements IDService {

  /**
   * 数据缓存
   */
  Map<String, SequenceCache> caches = new HashMap<String, SequenceCache>();
  
  @Autowired
  private SequenceDao sequenceDao;
  
  /**
   * 更新DB的最多重试次数
   */
  private int maxRetryTimes = 3;
  
  public void setMaxRetryTimes(int maxRetryTimes) {
    this.maxRetryTimes = maxRetryTimes;
  }

  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(IDServiceImpl.class);

  public long nextValue(String sequence) {
    long[] vals = nextValues(sequence, 1);
    return vals[0];
  }

  public long[] nextValues(String sequence, int count) {
    //获取缓存，如果没有缓存则初始化缓存
    SequenceCache cache = caches.get(sequence);
    if (cache == null) {
      cache = new SequenceCache(sequence);
      caches.put(sequence, cache);
      refreshCache(cache, count);
    }

    //如果正常获取了sequence，则直接返回
    long[] values = cache.nextValues(count);
    if (values.length == count) {
      return values;
    }

    //如果没获取到足够的ID，则需要重新初始化缓存
    int leftCount = count - values.length;
    refreshCache(cache, leftCount);
    long[] newValues = cache.nextValues(leftCount);


    //构造返回的结果
    long[] result = new long[count];
    System.arraycopy(values, 0, result, 0, values.length);
    System.arraycopy(newValues, 0, result, values.length, newValues.length);
    return result;
  }

  /**
   * 刷新缓存
   * @param cache
   * @param count
   * <br/>created by Tianxin on 2015年11月25日 下午4:12:02
   */
  private void refreshCache(SequenceCache cache, int count) {
    Sequence sequence = sequenceDao.getByName(cache.getSequenceName());
    int totalCount = count + sequence.getCacheSize();
    long nextValue = nextValue(sequence, totalCount, maxRetryTimes);
    cache.refresh(nextValue, sequence.getStep(), nextValue + sequence.getStep() * totalCount);
  }
  
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
    if(newSeq.getStep() != step){
      throw new RuntimeException("两次重试过程中step发生了变化!");
    }
    return nextValue(newSeq, size, retryTimes);
  }

  public Map<String, long[]> nextValues(Map<String, Integer> sequenceSizes) {
    Map<String, long[]> map = new HashMap<String, long[]>(sequenceSizes.size() * 2);
    for (Entry<String, Integer> entry : sequenceSizes.entrySet()) {
      String seqName = entry.getKey();
      int count = entry.getValue();
      map.put(seqName, nextValues(seqName, count));
    }
    return map;
  }

  public CompressedIds nextCompressedValues(String sequence, int count) {
    return new CompressedIds(nextValues(sequence, count));
  }

  public int nextIntValue(String sequence) {
    long nextVal = nextValue(sequence);
    return (int) nextVal;
  }

  public int[] nextIntValues(String sequence, int count) {
    long[] vals = nextValues(sequence, count);
    return trans2IntArray(vals);
  }

  public Map<String, int[]> nextIntValues(Map<String, Integer> sequenceSizes) {
    Map<String, int[]> map = new HashMap<String, int[]>(sequenceSizes.size() * 2);
    for (Entry<String, Integer> entry : sequenceSizes.entrySet()) {
      String seqName = entry.getKey();
      int count = entry.getValue();
      map.put(seqName, nextIntValues(seqName, count));
    }
    return map;
  }

  /**
   * 将long数组转换为int数组
   * @param vals
   * @return
   * <br/>created by Tianxin on 2015年11月25日 下午3:07:09
   */
  public final static int[] trans2IntArray(long[] vals) {
    int[] iVals = new int[vals.length];
    for (int i = 0; i < vals.length; i++) {
      iVals[i] = (int) vals[i];
    }
    return iVals;
  }

  public static void main(String[] args) {
    System.out.println(Long.MIN_VALUE);
    System.out.println(Long.MAX_VALUE);
  }
}
