package com.applepieme.dao;

import com.applepieme.bean.User;

import java.util.List;

/**
 * UserDAO
 * 对user表进行增删改查的接口
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 21:32
 */
public interface UserDAO {
    /**
     * 查询所有用户
     *
     * @return List
     */
    List<User> listUsers();

    /**
     * 添加一个用户
     *
     * @param user
     * @return int
     */
    int addUser(User user);
}
