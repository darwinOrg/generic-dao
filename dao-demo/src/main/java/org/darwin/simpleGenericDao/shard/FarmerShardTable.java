package org.darwin.simpleGenericDao.shard;

import com.google.common.hash.Hashing;
import org.darwin.genericDao.annotations.TableShardPolicy;

import java.nio.charset.StandardCharsets;

/**
 * @author hexiufeng
 * @date 2018/12/29下午12:11
 */
public class FarmerShardTable implements TableShardPolicy {


  @Override
  public String generateName(String db, String table, int shardCount, Object shardKey) {
    long hc = Hashing.farmHashFingerprint64()
            .hashString(shardKey.toString(), StandardCharsets.UTF_8).asLong();
    long index = hc % (long) shardCount;
    if (index < 0L) {
      index = -index;
    }
    String tableNamePrefix = table;
    if(db != null && db.length() > 0) {
      tableNamePrefix = db + "." + table;
    }
    return tableNamePrefix + "_" + index;
  }
}
