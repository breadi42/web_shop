package com.applepieme.service;

import com.applepieme.bean.User;

import java.util.List;

/**
 * UserService接口
 * 负责调用DAO层
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 21:49
 */
public interface UserService {
    /**
     * 查询所有用户
     *
     * @return List
     */
    List<User> listUsers();

    /**
     * 添加一个用户
     *
     * @param user 用户数据对象
     * @return int
     */
    int addUser(User user);

    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return User
     */
    User getUserById(int id);

    /**
     * 更新用户信息
     *
     * @param user 用户数据对象
     * @return int
     */
    int updateUser(User user);
}
