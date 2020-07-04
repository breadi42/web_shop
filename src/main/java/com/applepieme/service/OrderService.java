package com.applepieme.service;

import com.applepieme.bean.Order;

import java.util.List;

/**
 * OrderService
 * 负责调用DAO层
 *
 * @author applepieme@yeah.net
 * @date 2020/7/3 17:09
 */
public interface OrderService {
    /**
     * 增添一个订单
     *
     * @param order 订单数据对象
     * @return int
     */
    int addOrder(Order order);

    /**
     * 根据用户id查询订单
     *
     * @param userId 用户id
     * @return List
     */
    List<Order> listOrdersByUserId(int userId);
}
