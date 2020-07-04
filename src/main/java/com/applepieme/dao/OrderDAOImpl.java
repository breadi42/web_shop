package com.applepieme.dao;

import com.applepieme.bean.Order;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * OrderDAO的实现类
 *
 * @author applepieme@yeah.net
 * @date 2020/7/3 16:57
 */
public class OrderDAOImpl extends BaseDAO<Order> implements OrderDAO {
    @Override
    public int addOrder(Order order) {
        String sql = "insert into `order` (`userId`, `username`, `goodsId`, `goodsname`, `userPhone`, `address`, "
                + "`number`, `totalPrice`, `date`, `status`) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Timestamp timestamp = Timestamp.valueOf(format);
        return super.update(sql, order.getUserId(), order.getUsername(), order.getGoodsId(),
                order.getGoodsname(), order.getUserPhone(), order.getAddress(), order.getNumber(),
                order.getTotalPrice(), timestamp, "未发货");
    }

    @Override
    public List<Order> listOrdersByUserId(int userId) {
        String sql = "select * from `order` where `userId` = ?";
        return (List<Order>) super.getList(sql, userId);
    }
}
