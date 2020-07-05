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
        String sql = "select * from `user`";
        return (List<User>) super.getList(sql);
    }

    @Override
    public int addUser(User user) {
        String sql = "insert into `user` (`username`, `password`, `phone`, `address`) values (?, ?, ?, ?)";
        return super.update(sql, user.getUsername(), user.getPassword(), user.getPhone(), user.getAddress());
    }

    @Override
    public int updateUser(User user) {
        String sql = "update `user` set `username` = ?, `password` = ?, `phone` = ?, `address` = ? where `userId` = ?";
        return super.update(sql, user.getUsername(), user.getPassword(), user.getPhone(), user.getAddress(),
                user.getUserId());
    }

    @Override
    public int deleteUser(int userId) {
        String sql = "delete from `user` where `userId` = ?";
        return super.update(sql, userId);
    }
}
