package com.applepieme.controller;

import com.applepieme.bean.Cart;
import com.applepieme.bean.User;
import com.applepieme.service.FactoryService;
import com.applepieme.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 用户Controller
 * 处理用户相关请求
 *
 * @author applepieme@yeah.net
 * @date 2020/6/27 20:35
 */
@WebServlet("*.user")
public class UserController extends HttpServlet {
    /**
     * UserService实例对象
     */
    UserService userService = FactoryService.getService(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求路径
        String methodName = req.getServletPath();
        // 从请求路径获取方法名
        methodName = methodName.substring(1, methodName.length() - 5);
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
     * 用户登录
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        List<User> userList = userService.listUsers();
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                Cart userCart = new Cart();
                userCart.setUserId(user.getUserId());
                userCart.setUsername(username);
                req.getSession().setAttribute("userCart", userCart);
                resp.getWriter().println(200);
                return;
            }
        }
        resp.getWriter().println(400);
    }

    /**
     * 用户注销
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("userCart") != null) {
            req.getSession().setAttribute("userCart", null);
            resp.getWriter().println(200);
        } else {
            resp.getWriter().println(400);
        }
    }
}
