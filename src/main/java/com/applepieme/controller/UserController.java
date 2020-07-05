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
     * 服务层实现类对象，负责调用DAO层
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
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 把响应类型设为application/json
        resp.setContentType("application/json");
        // 获取表单中的用户名
        String username = req.getParameter("username");
        // 获取表单中的密码
        String password = req.getParameter("password");
        // 获取数据库中所有用户信息
        List<User> userList = userService.listUsers();
        for (User user : userList) {
            // 当用户名和密码都正确时登录成功
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // 为当前登录的用户创建一个购物车
                Cart userCart = new Cart();
                // 初始化购物车用户
                userCart.setUser(user);
                // 把购物车存放到session中
                req.getSession().setAttribute("userCart", userCart);
                // 登录成功，返回状态码200
                resp.getWriter().println(200);
                return;
            }
        }
        // 登录失败，返回状态码400
        resp.getWriter().println(400);
    }

    /**
     * 用户退出
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 把响应类型设为application/json
        resp.setContentType("application/json");
        // 如果用户已经登录
        if (req.getSession().getAttribute("userCart") != null) {
            // 把购物车从session中清空
            req.getSession().setAttribute("userCart", null);
            // 退出成功，返回状态码200
            resp.getWriter().println(200);
        } else {
            // 失败，返回400
            resp.getWriter().println(400);
        }
    }

    /**
     * 用户注册
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void signup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 把响应类型设为application/json
        resp.setContentType("application/json");
        // 创建一个用户对象
        User user = new User();
        // 用表单中的信息来初始化用户
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        user.setPhone(req.getParameter("phone"));
        user.setAddress(req.getParameter("address"));
        // 调用服务层，添加一个用户，若改变行数大于0，说明添加成功
        if (userService.addUser(user) >= 1) {
            // 添加成功，返回200
            resp.getWriter().println(200);
        } else {
            // 失败，返回400
            resp.getWriter().println(400);
        }
    }

    /**
     * 用户主页 获取商品数据
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void welcome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 转发到商品controller，获取用户主页的推荐商品列表
        req.getRequestDispatcher("initIndex.goods").forward(req, resp);
    }

    /**
     * 购物车
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void userCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取session对象
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
                    // 每次翻页都清空当前页的商品列表
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
                // 把当前页码，总页数，页码数组，当前页商品列表存放到request中
                req.setAttribute("page", page);
                req.setAttribute("total", total);
                req.setAttribute("array", array);
                req.setAttribute("goodsList", pageGoods);
            } else {
                // 商品数量不足9个时，直接显示
                req.setAttribute("goodsList", cartGoodsList);
            }
        }
        // 转发到购物车页面
        req.getRequestDispatcher("WEB-INF/front/cart.jsp").forward(req, resp);
    }

    /**
     * 修改用户信息
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 把响应类型设为application/json
        resp.setContentType("application/json");
        // 获取购物车对象
        Cart userCart = (Cart) req.getSession().getAttribute("userCart");
        // 获取当前登录用户
        User user = userCart.getUser();
        // 用修改表单中的数据更新当前登录的用户信息
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        user.setPhone(req.getParameter("phone"));
        user.setAddress(req.getParameter("address"));
        // 调用服务层，修改用户信息，若改变行数大于0，说明修改成功
        if (userService.updateUser(user) >= 1) {
            // 修改成功，返回200
            resp.getWriter().println(200);
        } else {
            // 修改失败，返回400
            resp.getWriter().println(400);
        }
    }

    /**
     * 查看所有用户信息
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取所有用户列表
        List<User> userList = userService.listUsers();
        if (userList != null) {
            // 若用户数大于9个，则需要分页显示
            if (userList.size() > 9) {
                // 获取当前页码数
                int page = Integer.parseInt(req.getParameter("page"));
                // 获取总页数
                int total = (userList.size() - 1) / 9 + 1;
                // 创建页码数组
                int[] array = new int[999];
                // 初始化页码数组
                for (int i = 0; i < total; i++) {
                    array[i] = i + 1;
                }
                // 创建当前页的用户列表
                List<User> pageUsers = new ArrayList<>();
                // 当前是多少页就循环多少次
                for (int i = 0; i < page; i++) {
                    // 每次翻页都清空当前页的用户列表
                    pageUsers.clear();
                    // 每页显示9个用户
                    for (int j = 0; j < 9; j++) {
                        // 若索引值等于用户数，说明已经遍历完用户列表
                        if ((j + (page - 1) * 9) == userList.size()) {
                            break;
                        }
                        // 把用户添加到当前页的用户列表
                        pageUsers.add(userList.get(j + (page - 1) * 9));
                    }
                }
                // 把当前页，总页数，页码数组，当前页用户列表存放到request中
                req.setAttribute("page", page);
                req.setAttribute("total", total);
                req.setAttribute("array", array);
                req.setAttribute("userList", pageUsers);
            } else {
                // 如果用户数不足9个，则直接显示
                req.setAttribute("userList", userList);
            }
        }
        // 转发到用户管理页面
        req.getRequestDispatcher("manage_user.page").forward(req, resp);
    }

    /**
     * 删除一个用户
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 响应类型设置为application/json
        resp.setContentType("application/json");
        // 获取要删除的用户id
        int id = Integer.parseInt(req.getParameter("id"));
        // 调用服务层，删除用户，若改变行数大于0，说明删除成功
        if (userService.deleteUser(id) >= 1) {
            // 删除成功，返回200
            resp.getWriter().println(200);
        } else {
            // 删除失败，返回400
            resp.getWriter().println(400);
        }
    }
}
