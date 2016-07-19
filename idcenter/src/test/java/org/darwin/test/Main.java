/**
 * org.darwin.test.Main.java
 * created by Tianxin(tianjige@163.com) on 2016年7月19日 下午12:11:35
 */
package org.darwin.test;

import org.darwin.idcenter.service.IDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * <br/>created by Tianxin on 2016年7月19日 下午12:11:35
 */
public class Main {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(Main.class);
  
  public static void main(String[] args) {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
    IDService idService = ctx.getBean(IDService.class);
    System.out.println(idService.nextIntValue("wordId"));
    System.out.println(idService.nextIntValue("wordId"));
    System.out.println(idService.nextIntValue("wordId"));
    System.out.println(idService.nextIntValue("wordId"));
    System.out.println(idService.nextIntValues("wordId", 100)[0]);
  }
}
