/**
 * org.darwin.idcenter.dao.SequenceDao.java
 * created by Tianxin(tianjige@163.com) on 2015年8月6日 下午1:18:07
 */
package org.darwin.idcenter.dao;

import java.util.Date;

import org.darwin.genericDao.dao.BaseDao;
import org.darwin.idcenter.bo.Sequence;

/**
 * 操作sequence的Dao定义
 * <br/>created by Tianxin on 2015年8月6日 下午1:18:07
 */
public interface SequenceDao extends BaseDao<Integer, Sequence> {

  /**
   * 根据sequence的type获取到相应的对象
   * @param seqName
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午1:53:04
   */
  Sequence getByName(String seqName);

  /**
   * 将sequence的当前水位提升至newValue
   * @param newValue    新的sequence水位
   * @param id  sequence项的ID
   * @param curValue    现在的值
   * @param version  时间版本
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午5:27:11
   */
  boolean modifyCurValue(long newValue, int id, long curValue, Date version);

  /**
   * 修改sequence的步长
   * @param seqId
   * @param step
   * <br/>created by Tianxin on 2015年8月6日 下午6:09:18
   */
  boolean modifyStep(int seqId, int step);
}
