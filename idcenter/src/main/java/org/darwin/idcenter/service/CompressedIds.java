/**
 * org.darwin.idcenter.CompressedIds.java
 * created by Tianxin(tianjige@163.com) on 2015年8月6日 下午12:56:58
 */
package org.darwin.idcenter.service;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 一个ID列表的压缩类
 * <br/>created by Tianxin on 2015年8月6日 下午12:56:58
 */
public class CompressedIds implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 压缩内核
   */
  private CompressMeta[] metas = null;

  /**
   * 构造函数
   */
  public CompressedIds(long[] ids) {
    metas = compress(ids);
  }

  /**
   * 将一个id数组压缩为N个压缩信息元
   * @param ids
   * @return
   * <br/>created by Tianxin on 2016年6月17日 上午10:59:13
   */
  private CompressMeta[] compress(long[] ids) {
    ArrayList<CompressMeta> metas = new ArrayList<CompressMeta>();

    //初始化压缩单元
    CompressMeta meta = new CompressMeta();
    metas.add(meta);
    meta.start = ids[0];
    meta.count = 1;
    if (ids.length > 1) {
      meta.step = ids[1] - ids[0];
    }

    //从第一个开始
    for (int i = 1; i < ids.length; i++) {

      //如果还是按步长增长
      if (ids[i] == meta.start + meta.step * meta.count) {
        meta.count += 1;
        continue;
      }

      //如果步长变化了，重新初始化一个压缩单元
      meta = new CompressMeta();
      metas.add(meta);
      meta.start = ids[i];
      meta.count = 1;
      if (i + 1 < ids.length) {
        meta.step = ids[i + 1] - ids[i];
      }
    }
    return metas.toArray(new CompressMeta[metas.size()]);
  }

  /**
   * 获取全部的值
   * @return
   * <br/>created by Tianxin on 2015年8月6日 下午1:05:34
   */
  public long[] values() {
    if (metas == null || metas.length == 0) {
      return new long[0];
    }

    //确定一共有多少个数值
    int count = 0;
    for (CompressMeta meta : metas) {
      count += meta.count;
    }

    //装载数组
    long[] values = new long[count];
    int index = 0;
    for (CompressMeta meta : metas) {
      for (int i = 0; i < meta.count; i++) {
        values[index++] = meta.start + meta.step * i;
      }
    }

    //装载对象
    return values;
  }

  /**
   * 压缩单元
   * 
   * <br/>created by Tianxin on 2016年6月17日 上午11:05:14
   */
  private class CompressMeta implements Serializable {
    private static final long serialVersionUID = 1L;
    private long start;
    private long step;
    private int count;
  }
}
