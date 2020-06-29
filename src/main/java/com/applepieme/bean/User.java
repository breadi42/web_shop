package com.applepieme.bean;

/**
 * User数据类
 * 与user表对应的数据模型
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 20:32
 */
public class User {
    /**
     * 用户id
     * 对应user表中的userId
     */
    private int userId;
    /**
     * 用户名
     * 对应user表中的username
     */
    private String username;
    /**
     * 密码
     * 对应user表中的password
     */
    private String password;
    /**
     * 电话
     * 对应user表中的phone
     */
    private String phone;
    /**
     * 地址
     * 对应user表中的address
     */
    private String address;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
