/*
 * PlanController.java created by Tianxin(tianjige@163.com) on 2015年6月15日 上午11:29:32
 */
package org.darwin.myweb.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.darwin.myweb.bo.Plan;
import org.darwin.myweb.service.PlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 计划的controller的定义
 */
@Controller
public class PlanController {

  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(PlanController.class);

  /**
   * 构造函数
   */
  public PlanController() {
    LOG.info("controller loaded");
  }

  @Autowired
  private PlanService planService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String userId = request.getParameter("userid");
    int iUserId = Integer.parseInt(userId);
    List<Plan> plans = planService.findByUser(iUserId);
    PrintWriter pw = response.getWriter();
    pw.write("<html><head></head><body><table>");

    pw.write("<a href='./add?userid=" + request.getParameter("userid") + "&name=" + System.currentTimeMillis() % 100 + "&planid=" + System.currentTimeMillis() % 100000 + "' target='_blank'>add</a>");
    for (Plan plan : plans) {
      pw.write("<tr>");

      pw.write("<td>");
      pw.write(plan.getId());
      pw.write("</td>");

      pw.write("<td>");
      pw.write(plan.getName());
      pw.write("</td>");

      pw.write("<td>");
      pw.write(plan.getUserId());
      pw.write("</td>");

      pw.write("<td>");
      pw.write(plan.getAddTime().toGMTString());
      pw.write("</td>");

      pw.write("</tr>");
    }
    pw.write("</table></body></html>");
  }

  @RequestMapping(value = "/listAll", method = RequestMethod.GET)
  @ResponseBody
  public void listAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

    List<Plan> plans = planService.findAllShards();
    PrintWriter pw = response.getWriter();
    pw.write("<html><head></head><body><table>");

    pw.write("<a href='./add?userid=" + request.getParameter("userid") + "&name=" + System.currentTimeMillis() % 100 + "' target='_blank'>添加</a>");
    for (Plan plan : plans) {
      pw.write("<tr>");

      pw.write("<td>");
      pw.write(plan.getId());
      pw.write("</td>");

      pw.write("<td>");
      pw.write(plan.getName());
      pw.write("</td>");

      pw.write("<td>");
      pw.write(plan.getUserId());
      pw.write("</td>");

      pw.write("<td>");
      pw.write(plan.getAddTime().toGMTString());
      pw.write("</td>");

      pw.write("</tr>");
    }
    pw.write("</table></body></html>");
  }

  @RequestMapping(value = "/add")
  @ResponseBody
  public void add(HttpServletRequest request, HttpServletResponse response) {
    int userId = Integer.parseInt(request.getParameter("userid"));
    int planId = Integer.parseInt(request.getParameter("planid"));
    String name = request.getParameter("name");
    name = name == null ? "test" : name;
    Plan plan = Plan.from(userId, name);
    plan.setId(planId);
    planService.create(plan);
  }

  @RequestMapping(value = "/test")
  @ResponseBody
  public void test() {
    System.out.println("here!");
  }

}
