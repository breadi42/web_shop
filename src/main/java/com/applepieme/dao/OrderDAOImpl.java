package com.applepieme.dao;

import com.applepieme.bean.Order;

/**
 * OrderDAO的实现类
 *
 * @author applepieme@yeah.net
 * @date 2020/7/3 16:57
 */
public class OrderDAOImpl extends BaseDAO<Order> implements OrderDAO {
    @Override
    public int addOrder(Order order) {
        String sql = "insert into `order` (`userId`, `goodsId`, `userPhone`, `address`, `number`, `totalPrice`,"
                + " `date`, `status`) values (?, ?, ?, ?, ?, ?, ?, ?)";
        return super.update(sql, order.getUserId(), order.getGoodsId(), order.getUserPhone(), order.getAddress(),
                order.getNumber(), order.getTotalPrice(), System.currentTimeMillis(), "未发货");
    }
}
