/**
 * org.darwin.idcenter.dao.impl.SequenceDaoImpl.java
 * created by Tianxin(tianjige@163.com) on 2015年8月6日 下午1:35:03
 */
package org.darwin.idcenter.dao.impl;

import java.util.Date;

import org.darwin.genericDao.dao.impl.GenericDao;
import org.darwin.genericDao.operate.Matches;
import org.darwin.genericDao.operate.Modifies;
import org.darwin.idcenter.bo.Columns;
import org.darwin.idcenter.bo.Sequence;
import org.darwin.idcenter.dao.SequenceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * sequenceDao的实现
 * <br/>created by Tianxin on 2015年8月6日 下午1:35:03
 */
@Repository
public class SequenceDaoImpl extends GenericDao<Integer, Sequence> implements SequenceDao {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(SequenceDaoImpl.class);
  
  @Qualifier("jdbcTemplate")
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    super.setJdbcTemplate(jdbcTemplate);
  }

  public Sequence getByName(String seqName) {
    return findOne(Matches.one("seq_type", seqName));
  }

  public boolean modifyCurValue(long newValue, int id, long curValue, Date version) {
    return update(Modifies.one(Columns.curValue, newValue), 
        Matches.one(Columns.id, id).match(Columns.curValue, curValue).match(Columns.lastModified, version)) == 1;
  }

  public boolean modifyStep(int seqId, int step) {
    return update(Modifies.one(Columns.step, step).modify(Columns.lastModified, new Date()), 
        Matches.one(Columns.id, seqId)) == 1;
  }

}
