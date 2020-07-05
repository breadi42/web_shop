package com.applepieme.service;

import com.applepieme.bean.Order;

import java.util.List;

/**
 * OrderService
 * 订单的服务层接口
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

    /**
     * 根据id删除订单
     *
     * @param orderId 商品id
     * @return int
     */
    int deleteOrder(int orderId);

    /**
     * 修改订单状态
     *
     * @param orderId 订单id
     * @param status  订单状态
     * @return int
     */
    int changeOrderStatus(int orderId, String status);

    /**
     * 查询所有订单
     *
     * @return List
     */
    List<Order> listOrders();
}
