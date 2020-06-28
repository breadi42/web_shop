package com.applepieme.bean;

import java.util.List;

/**
 * 购物车
 *
 * @author applepieme@yeah.net
 * @date 2020/6/28 20:45
 */
public class Cart {
    private int userId;
    private String username;
    private List<Goods> cartGoodsList;
    private int totalNumber;
    private double totalPrice;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Goods> getCartGoodsList() {
        return cartGoodsList;
    }

    public void setCartGoodsList(List<Goods> cartGoodsList) {
        this.cartGoodsList = cartGoodsList;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", cartGoodsList=" + cartGoodsList +
                ", totalNumber=" + totalNumber +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
