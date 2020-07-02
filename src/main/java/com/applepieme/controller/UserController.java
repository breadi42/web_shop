package com.applepieme.controller;

import com.applepieme.bean.Cart;
import com.applepieme.bean.Goods;
import com.applepieme.bean.User;
import com.applepieme.service.FactoryService;
import com.applepieme.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // 获取数据库中所有用户信息
        List<User> userList = userService.listUsers();
        for (User user : userList) {
            // 当用户名和密码都正确时登录成功
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // 为当前登录的用户创建一个购物车
                Cart userCart = new Cart();
                // 初始化用户id和用户名
                userCart.setUserId(user.getUserId());
                userCart.setUsername(username);
                // 把购物车存放到session中
                req.getSession().setAttribute("userCart", userCart);
                resp.getWriter().println(200);
                return;
            }
        }
        resp.getWriter().println(400);
    }

    /**
     * 用户退出
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        if (req.getSession().getAttribute("userCart") != null) {
            // 把购物车从session中清空
            req.getSession().setAttribute("userCart", null);
            resp.getWriter().println(200);
        } else {
            resp.getWriter().println(400);
        }
    }

    /**
     * 用户注册
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    private void signup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        // 创建一个用户对象
        User user = new User();
        // 用表单中的信息来初始化用户
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        user.setPhone(req.getParameter("phone"));
        user.setAddress(req.getParameter("address"));
        // 把用户信息保存到数据库中
        int row = userService.addUser(user);
        if (row >= 1) {
            resp.getWriter().println(200);
        } else {
            resp.getWriter().println(400);
        }
    }

    /**
     * 用户主页 获取商品数据
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    private void welcome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("initIndex.goods").forward(req, resp);
    }

    /**
     * 购物车
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    private void userCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        // 获取购物车对象
        Cart userCart = (Cart) session.getAttribute("userCart");
        // 获取购物车中的商品列表
        List<Goods> cartGoodsList = userCart.getCartGoodsList();
        if (cartGoodsList != null) {
            // 商品数大于 9 时需要分页显示
            if (cartGoodsList.size() > 9) {
                // 获取当前页码数
                int page = Integer.parseInt(req.getParameter("page"));
                /* 计算总共多少页
                *  需要使用商品数 - 1 来除以每页个数再 + 1
                *  + 1 是因为当商品数不是每页个数的整数倍时，由于是int类型，会向下取整，所以需要 + 1
                *  商品数需要 - 1 是因为如果商品个数恰好是每页个数的整数倍，那么在本身不需要分页的情况下，会多出1页空页 */
                int total = (cartGoodsList.size() - 1) / 9 + 1;
                // 页码数字数组，封装页码中的数字
                int[] array = new int[999];
                // 赋值
                for (int i = 0; i < total; i++) {
                    array[i] = i + 1;
                }
                // 当前页面显示的商品列表
                List<Goods> pageGoods = new ArrayList<>();
                // 有多少页就进行多少次循环
                for (int i = 0; i < page; i++) {
                    // 每次翻页都情况当前页的商品列表
                    pageGoods.clear();
                    // 每页 9 个商品
                    for (int j = 0; j < 9; j++) {
                        // 当前的索引值等于商品数的时候说明已经遍历完商品列表
                        if ((j + (page - 1) * 9) == cartGoodsList.size()) {
                            break;
                        }
                        // 把商品添加到当前页的商品列表
                        pageGoods.add(cartGoodsList.get(j + (page - 1) * 9));
                    }
                }
                // 设置请求参数
                req.setAttribute("page", page);
                req.setAttribute("total", total);
                req.setAttribute("array", array);
                req.setAttribute("goodsList", pageGoods);
            } else {
                req.setAttribute("goodsList", cartGoodsList);
            }
        }
        req.getRequestDispatcher("WEB-INF/front/cart.jsp").forward(req, resp);
    }

}
