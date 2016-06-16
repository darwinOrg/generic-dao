/**
 * org.darwin.idcenter.mgr.SequenceMgr.java
 * created by Tianxin(tianjige@163.com) on 2015年8月6日 下午1:49:46
 */
package org.darwin.idcenter.mgr;

import java.util.List;

import org.darwin.idcenter.bo.Sequence;

/**
 * Sequence管理的mgr层
 * <br/>created by Tianxin on 2015年8月6日 下午1:49:46
 */
public interface SequenceMgr {
  
  /**
   * 向数据库中申请size个ID
   * @param seqName
   * @param size
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午1:34:23
   */
  long getNextValues(String seqName, int size);
  
  /**
   * 新建一个sequence
   * @param seqName
   * @param curValue
   * @param step
   * <br/>created by Tianxin on 2015年8月6日 下午5:59:37
   */
  void createSequence(String seqName, int curValue, int step);
  
  /**
   * 修改一个Sequence
   * @param seqId
   * @param step
   * <br/>created by Tianxin on 2015年8月6日 下午6:00:44
   */
  boolean modifyStep(int seqId,  int step);
  
  /**
   * 获取所有的sequence
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午6:01:19
   */
  List<Sequence> findAll();
}
