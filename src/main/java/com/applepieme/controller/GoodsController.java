package com.applepieme.controller;

import com.applepieme.bean.Cart;
import com.applepieme.bean.Goods;
import com.applepieme.bean.Order;
import com.applepieme.bean.User;
import com.applepieme.service.FactoryService;
import com.applepieme.service.GoodsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品Controller
 * 处理商品相关请求
 *
 * @author applepieme@yeah.net
 * @date 2020/6/30 14:53
 */
@WebServlet("*.goods")
public class GoodsController extends HttpServlet {
    GoodsService goodsService = FactoryService.getService(GoodsService.class);

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
     * 获取用户主页的热门商品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void initIndex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Goods> goodsList = goodsService.listGoods();
        if (goodsList != null) {
            req.setAttribute("hotGoods", getHotGoods(goodsList));
            req.getRequestDispatcher("WEB-INF/front/welcome.jsp").forward(req, resp);
        }
    }

    /**
     * 用户查询所有商品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void queryGoods(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Goods> goodsList = goodsService.listGoods();
        if (goodsList != null) {
            req.setAttribute("type", "Goods");
            req.setAttribute("hotGoods", getHotGoods(goodsList));
            if (goodsList.size() > 8) {
                setPageGoods(req, resp, goodsList);
            } else {
                req.setAttribute("goodsList", goodsList);
            }
            req.getRequestDispatcher("WEB-INF/front/goods.jsp").forward(req, resp);
        }
    }

    /**
     * 查询电子产品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void queryElectronic(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Goods> electronicList = goodsService.listGoodsByType("电子产品");
        if (electronicList != null) {
            req.setAttribute("type", "Electronic");
            req.setAttribute("hotGoods", getHotGoods(electronicList));
            if (electronicList.size() > 8) {
                setPageGoods(req, resp, electronicList);
            } else {
                req.setAttribute("goodsList", electronicList);
            }
            req.getRequestDispatcher("WEB-INF/front/goods.jsp").forward(req, resp);
        }
    }

    /**
     * 查询服装
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void queryClothing(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Goods> clothingList = goodsService.listGoodsByType("服装");
        if (clothingList != null) {
            req.setAttribute("type", "Clothing");
            req.setAttribute("hotGoods", getHotGoods(clothingList));
            if (clothingList.size() > 8) {
                setPageGoods(req, resp, clothingList);
            } else {
                req.setAttribute("goodsList", clothingList);
            }
            req.getRequestDispatcher("WEB-INF/front/goods.jsp").forward(req, resp);
        }
    }

    /**
     * 查询食品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void queryFood(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Goods> foodList = goodsService.listGoodsByType("食品");
        if (foodList != null) {
            req.setAttribute("type", "Food");
            req.setAttribute("hotGoods", getHotGoods(foodList));
            if (foodList.size() > 8) {
                setPageGoods(req, resp, foodList);
            } else {
                req.setAttribute("goodsList", foodList);
            }
            req.getRequestDispatcher("WEB-INF/front/goods.jsp").forward(req, resp);
        }
    }

    /**
     * 根据关键字查询
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void queryByKey(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Goods> keyList = goodsService.listGoodsByKey(req.getParameter("key"));
        if (keyList != null) {
            req.setAttribute("hotGoods", getHotGoods(keyList));
            if (keyList.size() > 8) {
                setPageGoods(req, resp, keyList);
            } else {
                req.setAttribute("goodsList", keyList);
            }
            req.getRequestDispatcher("WEB-INF/front/goods.jsp").forward(req, resp);
        }
    }

    /**
     * 查看商品详情
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Goods> goodsList = goodsService.listGoods();
        int id = Integer.parseInt(req.getParameter("id"));
        Goods goods = goodsService.getGoodsById(id);
        if (goods != null && goodsList != null) {
            req.setAttribute("hotGoods", getHotGoods(goodsList));
            req.setAttribute("goods", goods);
            req.getRequestDispatcher("WEB-INF/front/details.jsp").forward(req, resp);
        }
    }

    /**
     * 商品加入购物车
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void addCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        int number = Integer.parseInt(req.getParameter("number"));
        int id = Integer.parseInt(req.getParameter("id"));
        Goods goods = goodsService.getGoodsById(id);
        goods.setNumber(number);
        Cart cart = (Cart) session.getAttribute("userCart");
        if (cart == null) {
            out.println(300);
            return;
        }
        List<Goods> cartGoodsList = cart.getCartGoodsList();
        if (cartGoodsList == null) {
            cartGoodsList = new ArrayList<>();
            cartGoodsList.add(goods);
            cart.setCartGoodsList(cartGoodsList);
            session.setAttribute("userCart", cart);
            out.println(200);
            return;
        }
        for (Goods item : cartGoodsList) {
            if (item.getGoodsId() == id) {
                item.setNumber(item.getNumber() + number);
                session.setAttribute("userCart", cart);
                out.println(200);
                return;
            }
        }
        cartGoodsList.add(goods);
        cart.setCartGoodsList(cartGoodsList);
        session.setAttribute("userCart", cart);
        out.println(200);
    }

    /**
     * 从购物车中移除商品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void removeCartGoods(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        int id = Integer.parseInt(req.getParameter("id"));
        Cart userCart = (Cart) session.getAttribute("userCart");
        List<Goods> goodsList = userCart.getCartGoodsList();
        goodsList.removeIf(item -> item.getGoodsId() == id);
        if (goodsList.isEmpty()) {
            userCart.setCartGoodsList(null);
        }
        session.setAttribute("userCart", userCart);
        resp.sendRedirect("front_cart.page");
    }

    /**
     * 购买商品时的订单详情
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void orderDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int num = Integer.parseInt(req.getParameter("num"));
        Goods goods = goodsService.getGoodsById(id);
        goods.setNumber(num);
        Cart userCart = (Cart) req.getSession().getAttribute("userCart");
        User user = userCart.getUser();
        req.setAttribute("goods", goods);
        req.setAttribute("user", user);
        req.getRequestDispatcher("WEB-INF/front/orderDetails.jsp").forward(req, resp);
    }

    /**
     * 根据当前商品分类获取对应推荐商品列表
     *
     * @param goodsList 商品分类对应的商品列表
     * @return List
     */
    private List<Goods> getHotGoods(List<Goods> goodsList) {
        if (goodsList.size() <= 4) {
            return goodsList;
        }
        int start = (int) Math.floor(Math.random() * (goodsList.size() - 4));
        List<Goods> hotGoods = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            hotGoods.add(goodsList.get(start + i));
        }
        return hotGoods;
    }

    /**
     * 当商品数超过8个时，分页展示
     *
     * @param req       HttpServletRequest
     * @param resp      HttpServletResponse
     * @param goodsList 当前要显示的商品列表
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void setPageGoods(HttpServletRequest req, HttpServletResponse resp, List<Goods> goodsList)
            throws ServletException, IOException {
        int page = Integer.parseInt(req.getParameter("page"));
        /* 计算总共多少页
         * 需要使用商品数 - 1 来除以每页个数再 + 1
         * + 1 是因为当商品数不是每页个数的整数倍时，由于是int类型，会向下取整，所以需要 + 1
         * 商品数需要 - 1 是因为如果商品个数恰好是每页个数的整数倍，那么在本身不需要分页的情况下，会多出1页空页 */
        int total = (goodsList.size() - 1) / 8 + 1;
        int[] array = new int[999];
        for (int i = 0; i < total; i++) {
            array[i] = i + 1;
        }
        List<Goods> pageGoods = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            pageGoods.clear();
            for (int j = 0; j < 8; j++) {
                if ((j + (page - 1) * 8) == goodsList.size()) {
                    break;
                }
                pageGoods.add(goodsList.get(j + (page - 1) * 8));
            }
        }
        req.setAttribute("array", array);
        req.setAttribute("goodsList", pageGoods);
        req.setAttribute("total", total);
        req.setAttribute("page", page + "");
    }
}
