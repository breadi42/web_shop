package com.applepieme.dao;

import com.applepieme.bean.Order;

import java.util.List;

/**
 * OrderDAO
 * 对order表进行增删改查
 *
 * @author applepieme@yeah.net
 * @date 2020/7/3 16:53
 */
public interface OrderDAO {
    /**
     * 增加一条订单
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

    /**
     * 根据id删除订单
     *
     * @param orderId 订单id
     * @return int
     */
    int deleteOrder(int orderId);

    /**
     * 修改订单状态
     *
     * @param orderId 订单id
     * @param status 订单状态
     * @return int
     */
    int changeOrderStatus(int orderId, String status);
}
