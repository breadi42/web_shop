package com.applepieme.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 页面跳转Controller
 * WEB-INF目录下是受保护的页面，使用ajax异步请求的时候无法直接进行跳转，该servlet负责进行页面间的跳转
 *
 * @author applepieme@yeah.net
 * @date 2020/6/28 16:12
 */
@WebServlet("*.page")
public class PageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求路径
        String pageInfo = req.getServletPath();
        // 从请求路径获取跳转信息
        pageInfo = pageInfo.substring(1, pageInfo.length() - 5);
        // 点击标题回到对应的首页
        if ("index".equals(pageInfo)) {
            // 若管理员已登录 则跳转到管理员主页
            if (req.getSession().getAttribute("manage") != null) {
                req.getRequestDispatcher("WEB-INF/manage/welcome.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("welcome.user");
            }
            return;
        }
        // 把跳转信息分成两部分 第一部分是包名 第二部分是页面名称
        String[] tmp = pageInfo.split("_");
        // 请求转发到指定页面
        if (tmp.length == 2) {
            req.getRequestDispatcher("WEB-INF/" + tmp[0] + "/" + tmp[1] + ".jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("WEB-INF/" + pageInfo + ".jsp").forward(req, resp);
        }
    }
}
