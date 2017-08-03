package org.darwin.genericDao.annotations;

/**
 * Created by hexiufeng on 2017/8/3.
 */
public class NullTableShardPolicy implements TableShardPolicy {
  @Override
  public String generateName(String db, String table, int shardCount, Object shardKey) {
    // do nothing
    return null;
  }
}
