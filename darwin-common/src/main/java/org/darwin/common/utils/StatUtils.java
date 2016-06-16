/**
 * org.darwin.common.utils.StatUtils.java
 * created by Tianxin(tianjige@163.com) on 2015年6月4日 下午4:24:15
 */
package org.darwin.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * 报表系统需要使用的方法集.天全部按照yyyyMMdd的格式，月按照yyyyMM格式，年按照yyyy格式
 * created by Tianxin on 2015年6月4日 下午4:24:15
 */
public class StatUtils {

  private final static long millisOneDay = 1000 * 60 * 60 * 24;

  /**
   * 以yyyyMMdd格式返回今天的时间数字。<b>直接使用DateUtils中的同名方法。</b>
   * @return
   * created by Tianxin on 2015年6月4日 下午4:29:33
   */
  @Deprecated
  public final static int getTodayInt() {
    Date date = new Date();
    return getDateInt(date);
  }

  /**
   * 以yyyyMMdd格式返回昨天的时间数字。<b>直接使用DateUtils中的同名方法。</b>
   * @return
   * created by Tianxin on 2015年6月4日 下午4:30:04
   */
  @Deprecated
  public final static int getYesterdayInt() {
    Date date = new Date(System.currentTimeMillis() - millisOneDay);
    return getDateInt(date);
  }

  /**
   * 以yyyyMMdd格式返回当天的时间数字。<b>直接使用DateUtils中的同名方法。</b>
   * @param date
   * @return
   * created by Tianxin on 2015年6月4日 下午4:30:16
   */
  @Deprecated
  public final static int getDateInt(Date date) {
    SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
    String sDate = formater.format(date);
    return Integer.parseInt(sDate);
  }

  /**
   * 将参数转为相应的date类型。<b>直接使用DateUtils中的同名方法。</b>
   * @param yyyyMMdd
   * @return
   * created by Tianxin on 2015年6月4日 下午4:45:49
   */
  @Deprecated
  public final static Date getDateFromInt(int yyyyMMdd) {
    Calendar calendar = new GregorianCalendar();
    calendar.set(yyyyMMdd / 10000, yyyyMMdd % 10000 / 100 - 1, yyyyMMdd % 100, 0, 0, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }

  /**
   * 将参数转为相应的date类型。<b>直接使用DateUtils中的同名方法。</b>
   * @param yyyyMMdd
   * @return
   * created by Tianxin on 2015年6月4日 下午4:45:49
   */
  @Deprecated
  public final static String getDateStringFromInt(int yyyyMMdd) {
    String date = String.valueOf(yyyyMMdd);
    return Utils.connect(date.substring(0, 4), '-',date.substring(4, 6), '-', date.substring(6)); 
  }

  /**
   * 获取date的下一天。<b>直接使用DateUtils中的同名方法。</b>
   * @param date
   * @return
   * created by Tianxin on 2015年6月4日 下午4:55:49
   */
  @Deprecated
  public static int getNextDateInt(int date) {
    try {
      SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
      Date oDate = formater.parse(String.valueOf(date));
      Date next = new Date(oDate.getTime() + millisOneDay);
      return getDateInt(next);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取date的前一天。<b>直接使用DateUtils中的同名方法。</b>
   * @param date
   * @return
   * <br/>created by Tianxin on 2015年6月23日 下午1:44:08
   */
  @Deprecated
  public static int getLastDateInt(int date) {
    try {
      SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
      Date oDate = formater.parse(String.valueOf(date));
      Date next = new Date(oDate.getTime() - millisOneDay);
      return getDateInt(next);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * 获取date的下一天。<b>直接使用DateUtils中的同名方法。</b>
   * @param date
   * @return
   * created by Tianxin on 2015年6月4日 下午4:55:49
   */
  @Deprecated
  public static Date getNextDate(int date) {
    try {
      SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
      Date oDate = formater.parse(String.valueOf(date));
      return new Date(oDate.getTime() + millisOneDay);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * 获取date的前一天。<b>直接使用DateUtils中的同名方法。</b>
   * @param date
   * @return
   * <br/>created by Tianxin on 2015年6月23日 下午1:44:08
   */
  @Deprecated
  public static Date getLastDate(int date) {
    try {
      SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
      Date oDate = formater.parse(String.valueOf(date));
      return new Date(oDate.getTime() - millisOneDay);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  

  /**
   * 返回标准的时间格式。<b>直接使用DateUtils中的同名方法。</b>
   * @param date
   * @return
   * created by Tianxin on 2015年6月16日 上午10:35:12
   */
  @Deprecated
  public static Object getStringDate(Date date) {
    if (date == null) {
      return null;
    }
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format.format(date);
  }
  
  /**
   * 将list中的对象按照对象的key分组，放到map中。map的value为sumtype字段的累加值.
   * @param list
   * @param getter
   * @return
   * <br/>created by Tianxin on 2015年7月14日 下午2:38:46
   */
  @Deprecated
  public static <KEY, NUM, ENTITY> Map<KEY, NUM> trans2SumMap(List<ENTITY> list, NumGetter<ENTITY, KEY, NUM> getter){
    Map<KEY, NUM> map = Utils.newMap();
    for(ENTITY e : list){
      KEY key = getter.getKey(e);
      NUM num = getter.getNum(e);
      
      NUM sum = map.get(key);
      if(sum == null){
        map.put(key, num);
      }else{
        map.put(key, getter.sum(sum, num));
      }
    }
    
    return map;
  }
  
}
