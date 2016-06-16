/**
 * org.darwin.common.utils.Converter.java
 * created by Tianxin(tianjige@163.com) on 2015年7月23日 下午2:19:10
 */
package org.darwin.common.utils;

/**
 * 
 * <br/>created by Tianxin on 2015年7月23日 下午2:19:10
 */
public interface Converter<F, T> {
  
  T convert(F from);

}
