package com.applepieme.bean;

import java.sql.Date;

/**
 * Order数据类
 * 与order表对应的数据模型
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 20:33
 */
public class Order {
    /**
     * 订单id
     * 对应order表中的orderId
     */
    private int orderId;
    /**
     * 用户id
     * 对应order表中的userId
     */
    private int userId;
    /**
     * 商品id
     * 对应order表中的goodsId
     */
    private int goodsId;
    /**
     * 用户电话
     * 对应order表中的userPhone
     */
    private String userPhone;
    /**
     * 收获地址
     * 对应order表中的address
     */
    private String address;
    /**
     * 商品数量
     * 对应order表中的number
     */
    private int number;
    /**
     * 商品总价
     * 对应order表中的totalPrice
     */
    private double totalPrice;
    /**
     * 下单时间
     * 对应order表中的date
     */
    private Date date;
    /**
     * 订单状态
     * 对应order表中的status
     */
    private String status;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", userPhone='" + userPhone + '\'' +
                ", address='" + address + '\'' +
                ", number=" + number +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
