/**
 * org.darwin.generic.sample.dao.impl.CreativeDaoImpl.java
 * created by Tianxin(tianjige@163.com) on 2015年6月9日 下午2:33:21
 */
package org.darwin.generic.sample.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.darwin.generic.sample.bo.Creative;
import org.darwin.generic.sample.dao.CreativeDao;
import org.darwin.genericDao.bo.UserObject.Columns;
import org.darwin.genericDao.dao.impl.GenericDao;
import org.darwin.genericDao.operate.Matches;
import org.darwin.genericDao.operate.Modifies;
import org.darwin.genericDao.param.SQLParams;

public class CreativeDaoImpl extends GenericDao<Long, Creative> implements CreativeDao {

  public List<Creative> findByTitle(String title) {
    return find("title", SQLParams.not(Arrays.asList("", "")));
  }

  public int updateTitle(String title) {
    return update(Modifies.init().modify("title", title).modify("planid", 1).modify("count", SQLParams.increase(1)), Matches.one("groupid", 1));
  }


  //planid,groupid,unitid
  //groupid=1 and planid=2
  //planid=1 and unitid=3
  //groupid=22 and unitid=-44

  public static void main(String[] args) {
    
    Creative c = new Creative();
    c.setId(1L);
    c.setAddTime(new Date());
    c.setAddUser(1);
//    new CreativeDaoImpl().createOnDuplicate(Arrays.asList(c, c, c), "user_id=values(user_id), add_user=3");
    new CreativeDaoImpl().update(Arrays.asList(c, c, c), Columns.userId, "add_user", "add_time");
  }

  /**
   * 
   * <br/>created by Tianxin on 2015年7月17日 下午12:28:10
   */
  private void findRecords() {
    Collection<Long> ids = new ArrayList<>();
    ids.add(1L);
    ids.add(2L);
    ids.add(3L);
    find(Matches.init().match("id", ids));
//    find(Matches.or().addMatches(Matches.one("id", 1)).addMatches(Matches.one("id", 2)).addMatches(Matches.one("id", 3)));
//    find(Matches.one("id", SQLParams.express(" in (1,2,3)")));
  }

}
