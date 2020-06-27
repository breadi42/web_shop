package com.applepieme.dao;

import com.applepieme.bean.User;

import java.util.List;

/**
 * UserDAO的实现类
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 21:33
 */
public class UserDAOImpl extends BaseDAO<User> implements UserDAO {
    @Override
    public List<User> listUsers() {
        String sql = "select * from user";
        return (List<User>) super.getList(sql);
    }
}
