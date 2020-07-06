package com.applepieme.controller;

import com.applepieme.bean.Cart;
import com.applepieme.bean.Goods;
import com.applepieme.bean.Order;
import com.applepieme.service.FactoryService;
import com.applepieme.service.OrderService;

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
 * 订单Controller
 * 处理订单相关请求
 *
 * @author applepieme@yeah.net
 * @date 2020/7/3 12:12
 */
@WebServlet("*.order")
public class OrderController extends HttpServlet {
    /**
     * OrderService对象
     * 订单服务层对象，负责调用DAO层
     */
    OrderService orderService = FactoryService.getService(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求路径
        String methodName = req.getServletPath();
        // 从请求路径获取方法名
        methodName = methodName.substring(1, methodName.length() - 6);
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
     * 确认购买
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void buyGoods(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取要购买的商品id
        int goodsId = Integer.parseInt(req.getParameter("goodsId"));
        // 创建一个订单数据对象
        Order order = new Order();
        // 用表单中的数据初始化订单对象
        order.setUserId(Integer.parseInt(req.getParameter("userId")));
        order.setUsername(req.getParameter("username"));
        order.setGoodsId(goodsId);
        order.setGoodsname(req.getParameter("goodsname"));
        order.setUserPhone(req.getParameter("phone"));
        order.setAddress(req.getParameter("address"));
        order.setNumber(Integer.parseInt(req.getParameter("number")));
        order.setTotalPrice(Double.parseDouble(req.getParameter("totalPrice")));
        // 调用服务层添加订单，若变化行数大于等于1，说明添加成功
        if (orderService.addOrder(order) >= 1) {
            // 获取session对象
            HttpSession session = req.getSession();
            // 获取购物车对象
            Cart userCart = (Cart) session.getAttribute("userCart");
            // 获取购物车中的商品列表
            List<Goods> goodsList = userCart.getCartGoodsList();
            if (goodsList != null) {
                // 如果购物车中有该商品，则把已经购买的商品从购物车中移除
                goodsList.removeIf(item -> item.getGoodsId() == goodsId);
                // 如果购物车已经没有商品，把商品列表置为null
                if (goodsList.isEmpty()) {
                    userCart.setCartGoodsList(null);
                }
            }
            // 更新购物车
            session.setAttribute("userCart", userCart);
            // 下单成功后，修改商品的库存
            req.getRequestDispatcher("changeStock.goods").forward(req, resp);
        }
    }

    /**
     * 用户查看订单
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void listUserOrders(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取购物车对象
        Cart userCart = (Cart) req.getSession().getAttribute("userCart");
        // 获取用户id
        int userId = userCart.getUser().getUserId();
        // 获取该用户的订单列表
        List<Order> orderList = orderService.listOrdersByUserId(userId);
        if (orderList != null) {
            // 如果订单数大于10，则分页显示
            if (orderList.size() > 10) {
                // 获取当前页码数
                int page = Integer.parseInt(req.getParameter("page"));
                /* 计算总共多少页
                 *  需要使用订单数 - 1 来除以每页个数再 + 1
                 *  + 1 是因为当订单数不是每页个数的整数倍时，由于是int类型，会向下取整，所以需要 + 1
                 *  订单数需要 - 1 是因为如果订单个数恰好是每页个数的整数倍，那么在本身不需要分页的情况下，会多出1页空页 */
                int total = (orderList.size() - 1) / 10 + 1;
                // 页码数组
                int[] array = new int[999];
                // 初始化页码数组
                for (int i = 0; i < total; i++) {
                    array[i] = i + 1;
                }
                // 创建当前页的订单列表
                List<Order> pageOrder = new ArrayList<>();
                for (int i = 0; i < page; i++) {
                    // 每次翻页都清空当前页的订单列表
                    pageOrder.clear();
                    // 每页 10 个订单
                    for (int j = 0; j < 10; j++) {
                        // 当前的索引值等于订单数的时候说明已经遍历完订单列表
                        if ((j + (page - 1) * 10) == orderList.size()) {
                            break;
                        }
                        // 把订单添加到当前页的订单列表
                        pageOrder.add(orderList.get(j + (page - 1) * 10));
                    }
                }
                // 把当前页，总页数，页码数组，当前页订单列表存放到request中
                req.setAttribute("page", page);
                req.setAttribute("total", total);
                req.setAttribute("array", array);
                req.setAttribute("orderList", pageOrder);
            } else {
                // 订单数不足10个时，直接显示
                req.setAttribute("orderList", orderList);
            }
        }
        // 转发到用户订单页面
        req.getRequestDispatcher("front_userOrder.page").forward(req, resp);
    }

    /**
     * 删除订单
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 响应类型设为application/json
        resp.setContentType("application/json");
        // 获取要删除的订单id
        int id = Integer.parseInt(req.getParameter("id"));
        // 调用服务层删除订单，如果变化行数大于0，说明删除成功
        if (orderService.deleteOrder(id) >= 1) {
            // 删除成功，返回200
            resp.getWriter().println(200);
        } else {
            // 删除失败，返回400
            resp.getWriter().println(400);
        }
    }

    /**
     * 查询所有订单
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void listOrders(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取所有订单列表
        List<Order> orderList = orderService.listOrders();
        if (orderList != null) {
            // 如果订单数大于8个，则分页显示
            if (orderList.size() > 8) {
                // 获取当前页码数
                int page = Integer.parseInt(req.getParameter("page"));
                // 获取总页数
                int total = (orderList.size() - 1) / 8 + 1;
                // 创建页码数组
                int[] array = new int[999];
                // 初始化页码数组
                for (int i = 0; i < total; i++) {
                    array[i] = i + 1;
                }
                // 创建当前页订单列表
                List<Order> pageOrder = new ArrayList<>();
                // 当前是多少页，就循环多少次
                for (int i = 0; i < page; i++) {
                    // 每次翻页，清空当前页订单列表
                    pageOrder.clear();
                    // 每页显示8个订单
                    for (int j = 0; j < 8; j++) {
                        // 当索引值等于订单数的时候，说明已经遍历完
                        if ((j + (page - 1) * 8) == orderList.size()) {
                            break;
                        }
                        // 把商品添加到当前页订单列表
                        pageOrder.add(orderList.get(j + (page - 1) * 8));
                    }
                }
                // 把当前页码，总页数，页码数组，当前页订单列表存放到request中
                req.setAttribute("page", page);
                req.setAttribute("total", total);
                req.setAttribute("array", array);
                req.setAttribute("orderList", pageOrder);
            } else {
                // 如果订单数不足8个，则直接显示
                req.setAttribute("orderList", orderList);
            }
        }
        // 转发到订单管理页面
        req.getRequestDispatcher("manage_order.page").forward(req, resp);
    }

    /**
     * 修改订单状态
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void changeOrderStatus(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 设置响应类型为application/json
        resp.setContentType("application/json");
        // 获取要修改状态的订单id
        int id = Integer.parseInt(req.getParameter("id"));
        // 获取要修改的状态
        String status = req.getParameter("status");
        // 调用服务层，修改订单状态，如果改变行数大于0，说明修改成功
        if (orderService.changeOrderStatus(id, status) >= 1) {
            // 修改成功，返回状态码200
            resp.getWriter().println(200);
        } else {
            // 修改失败，返回400
            resp.getWriter().println(400);
        }
    }
}
