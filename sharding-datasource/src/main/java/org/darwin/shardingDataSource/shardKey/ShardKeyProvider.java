package org.darwin.shardingDataSource.shardKey;

/**
 * Description:
 * Created by xiongjie.wxj on 2017/10/25.
 */
public interface ShardKeyProvider {
  /**
   * 提供shardKey
   * @return
   */
  Long providerShardKey();
}
