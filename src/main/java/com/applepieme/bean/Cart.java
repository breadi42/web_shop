package com.applepieme.bean;

import java.util.List;

/**
 * 购物车
 *
 * @author applepieme@yeah.net
 * @date 2020/6/28 20:45
 */
public class Cart {
    /**
     * 当前登录的用户
     */
    private User user;
    /**
     * 购物车中的商品
     */
    private List<Goods> cartGoodsList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Goods> getCartGoodsList() {
        return cartGoodsList;
    }

    public void setCartGoodsList(List<Goods> cartGoodsList) {
        this.cartGoodsList = cartGoodsList;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "user=" + user +
                ", cartGoodsList=" + cartGoodsList +
                '}';
    }
}
