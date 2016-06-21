/**
 * org.darwin.tools.ConcurrentExecutor.java
 * created by Tianxin(tianjige@163.com) on 2016年6月17日 下午1:20:12
 */
package org.darwin.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.darwin.common.ThreadContext;

/**
 * 在所有切片上并行执行代码的执行器
 * <br/>created by Tianxin on 2016年6月17日 下午1:20:12
 */
public class AllShardsConcurrentExecutor {
  
  /**
   * 执行操作的线程池
   */
  private ExecutorService threadPool = null;
  
  /**
   * 所有的分切片的shardKey
   */
  private Set<Object> shardKeys = null;
  
  /**
   * 重新设定线程数，该操作会出发重新初始化线程池，当前线程池会被关闭
   * @param threadCount
   * <br/>created by Tianxin on 2016年6月17日 下午2:57:01
   */
  public void setThreadCount(int threadCount) {
    if(threadPool != null){
      threadPool.shutdownNow();
    }
    
    threadPool = Executors.newFixedThreadPool(threadCount);
  }
  
  public void setShardKeys(Set<Object> shardKeys) {
    this.shardKeys = shardKeys;
  }

  /**
   * 核心的逻辑执行方法
   * @param shardExecutor
   * @param timeout
   * <br/>created by Tianxin on 2016年6月17日 下午3:38:33
   */
  //TODO 缺少执行的逻辑内核
  public void execute(final ShardExecutor shardExecutor, long timeout){
    
    shardExecutor.shardCount = shardKeys.size();
    for(Object key : shardKeys){
      ShardRunnable executor = new ShardRunnable(key, shardExecutor);
      threadPool.execute(executor);
    }
    
    synchronized (shardExecutor) {
      try {
        shardExecutor.wait(timeout);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    
    if(shardExecutor.results.size() < shardKeys.size()){
      throw new RuntimeException("执行未结束!");
    }
    
    //TODO 如何返回结果
  }
  
  private static class Merge{
    
    public Merge(int count) {
      this.count = count;
    }
    
    private int count;
    
    private int geted = 0;
    
    public synchronized void callback(){
      geted += 1;
      if(geted == count){
          this.notify();
      }
    }
    
    public void merge() throws InterruptedException{
      this.wait();
      System.out.println("end");
    }
  }
  
  private class ShardRunnable implements Runnable{
    
    /**
     * 构造函数
     */
    public ShardRunnable(Object shardKey, ShardExecutor merger) {
      this.shardKey = shardKey;
      this.merger = merger;
    }
    
    private Object shardKey;
    
    private ShardExecutor merger;

    public void run() {
      Object oldKey = ThreadContext.getShardingKey();
      ThreadContext.putShardingKey(shardKey);
      
      //TODO 执行核心的事情
      
      ThreadContext.putShardingKey(oldKey);
    }
  }
  
  public static abstract class ShardExecutor{
    
    private int shardCount = 0;
    
    private List<Object> results;
    
    public void setShardCount(int shardCount) {
      this.shardCount = shardCount;
      results = new ArrayList<Object>(shardCount);
    }

    public abstract Object executeInDB();
    
    public abstract Object merge(List<Object> results);
    
  }
}
