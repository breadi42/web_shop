package com.applepieme.controller;

import com.applepieme.bean.Goods;
import com.applepieme.service.FactoryService;
import com.applepieme.service.GoodsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
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
     * 获取所有商品
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void listGoods(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void initIndex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Goods> goodsList = goodsService.listGoods();
        if (goodsList != null) {
            int start = (int) Math.floor(Math.random() * (goodsList.size() - 4));
            List<Goods> hotGoods = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                hotGoods.add(goodsList.get(start + i));
            }
            req.getSession().setAttribute("goodsList", goodsList);
            req.getSession().setAttribute("hotGoods", hotGoods);
            req.getRequestDispatcher("WEB-INF/front/welcome.jsp").forward(req, resp);
        }
    }
}
