/**
 * org.darwin.idcenter.CompressedIds.java
 * created by Tianxin(tianjige@163.com) on 2015年8月6日 下午12:56:58
 */
package org.darwin.idcenter.service;

import java.io.Serializable;

/**
 * 一个ID列表的压缩类
 * <br/>created by Tianxin on 2015年8月6日 下午12:56:58
 */
public class CompressedIds implements Serializable{
  
  private static final long serialVersionUID = 1L;
  
  long[] lines = null;

  /**
   * 构造函数
   */
  public CompressedIds(long[] ids) {
    if(ids.length != 0){
      
    }
  }
  
  /**
   * 获取全部的值
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午1:05:34
   */
  public long[] values(){
    if(lines == null){
      return new long[0];
    }
    
    //确定一共有多少个数值
    int count = 0;
    for(int i = 0 ; i < lines.length; i += 3){
      long start = lines[i];
      long end = lines[i + 2];
      long step = lines[i + 1];
      count += (end - start) / step + 1;
    }
    
    //装载对象
    return null;
  }
}
