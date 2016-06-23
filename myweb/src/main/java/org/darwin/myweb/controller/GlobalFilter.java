/**
 * org.darwin.myweb.controller.GlobaFilter.java
 * created by Tianxin(tianjige@163.com) on 2015年6月15日 上午11:47:49
 */
package org.darwin.myweb.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.darwin.common.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * created by Tianxin on 2015年6月15日 上午11:47:49
 */
public class GlobalFilter implements Filter {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(GlobalFilter.class);

  public void init(FilterConfig filterConfig) throws ServletException {}

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    ThreadContext.init();
    try {
      request.setCharacterEncoding("UTF-8");
      int userId = Integer.parseInt(request.getParameter("userid"));
      ThreadContext.put("shardingKey", userId);
      chain.doFilter(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
    ThreadContext.clean();
  }

  public void destroy() {}
}
