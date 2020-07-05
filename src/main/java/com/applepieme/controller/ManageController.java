package com.applepieme.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 管理员Controller
 * 处理管理员相关请求
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 21:51
 */
@WebServlet("*.manage")
public class ManageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求路径
        String methodName = req.getServletPath();
        // 从请求路径获取方法名
        methodName = methodName.substring(1, methodName.length() - 7);
        try {
            // 获取封装了前面方法名对应方法的Method对象
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
                    HttpServletResponse.class);
            // 传递实例对象，调用对应方法
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 管理员登录
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 响应类型设为application/json
        resp.setContentType("application/json");
        // 获取表单中输入的用户名
        String username = req.getParameter("username");
        // 获取表单中输入的密码
        String password = req.getParameter("password");
        // 管理员用户名为：manage，密码为：manage
        if ("manage".equals(username) && "manage".equals(password)) {
            // 把管理员的用户名存放到session中
            req.getSession().setAttribute("manage", "manage");
            // 登录成功，返回状态码200
            resp.getWriter().println(200);
        } else {
            // 登录失败，返回状态码400
            resp.getWriter().println(400);
        }
    }

    /**
     * 管理员退出
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 响应类型设为application/json
        resp.setContentType("application/json");
        // 如果管理员已登录
        if (req.getSession().getAttribute("manage") != null) {
            // 清空session中的管理员用户名
            req.getSession().setAttribute("manage", null);
            // 退出成功，返回状态码200
            resp.getWriter().println(200);
        } else {
            // 失败，返回400
            resp.getWriter().println(400);
        }
    }
}
