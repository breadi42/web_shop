package com.applepieme.service;

import com.applepieme.bean.User;

import java.util.List;

/**
 * UserService接口
 * 用户的服务层接口
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
     * 更新用户信息
     *
     * @param user 用户数据对象
     * @return int
     */
    int updateUser(User user);

    /**
     * 删除一个用户
     *
     * @param userId 用户id
     * @return int
     */
    int deleteUser(int userId);
}
