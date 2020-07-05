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
        // 创建一个订单数据对象
        Order order = new Order();
        int goodsId = Integer.parseInt(req.getParameter("goodsId"));
        // 初始化
        order.setUserId(Integer.parseInt(req.getParameter("userId")));
        order.setUsername(req.getParameter("username"));
        order.setGoodsId(goodsId);
        order.setGoodsname(req.getParameter("goodsname"));
        order.setUserPhone(req.getParameter("phone"));
        order.setAddress(req.getParameter("address"));
        order.setNumber(Integer.parseInt(req.getParameter("number")));
        order.setTotalPrice(Double.parseDouble(req.getParameter("totalPrice")));
        // 调用update方法添加订单
        if (orderService.addOrder(order) >= 1) {
            HttpSession session = req.getSession();
            Cart userCart = (Cart) session.getAttribute("userCart");
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
            req.getRequestDispatcher("welcome.user").forward(req, resp);
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
        Cart userCart = (Cart) req.getSession().getAttribute("userCart");
        int userId = userCart.getUser().getUserId();
        List<Order> orderList = orderService.listOrdersByUserId(userId);
        if (orderList.size() > 10) {
            int page = Integer.parseInt(req.getParameter("page"));
            /* 计算总共多少页
             *  需要使用订单数 - 1 来除以每页个数再 + 1
             *  + 1 是因为当订单数不是每页个数的整数倍时，由于是int类型，会向下取整，所以需要 + 1
             *  订单数需要 - 1 是因为如果订单个数恰好是每页个数的整数倍，那么在本身不需要分页的情况下，会多出1页空页 */
            int total = (orderList.size() - 1) / 10 + 1;
            int[] array = new int[999];
            for (int i = 0; i < total; i++) {
                array[i] = i + 1;
            }
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
            req.setAttribute("page", page);
            req.setAttribute("total", total);
            req.setAttribute("array", array);
            req.setAttribute("orderList", pageOrder);
        } else {
            req.setAttribute("orderList", orderList);
        }
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
    private void deleteUserOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        if (orderService.deleteOrder(id) >= 1) {
            if (req.getSession().getAttribute("userCart") != null) {
                resp.sendRedirect("listUserOrders.order?page=1");
            } else {
                resp.sendRedirect("");
            }
        }
    }

    /**
     * 用户确认收货
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void checkOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String status = "已签收";
        if (orderService.changeOrderStatus(id, status) >= 1) {
            resp.sendRedirect("listUserOrders.order?page=1");
        }
    }
}
