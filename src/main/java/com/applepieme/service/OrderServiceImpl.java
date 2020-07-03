package com.applepieme.service;

import com.applepieme.bean.Order;
import com.applepieme.dao.FactoryDAO;
import com.applepieme.dao.OrderDAO;

/**
 * OrderService的实现类
 *
 * @author applepieme@yeah.net
 * @date 2020/7/3 17:10
 */
public class OrderServiceImpl implements OrderService {
    OrderDAO dao = FactoryDAO.getDAO(OrderDAO.class);

    @Override
    public int addOrder(Order order) {
        return dao.addOrder(order);
    }
}
