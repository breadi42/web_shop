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
}
