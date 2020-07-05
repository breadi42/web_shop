package com.applepieme.service;

import com.applepieme.bean.User;
import com.applepieme.dao.FactoryDAO;
import com.applepieme.dao.UserDAO;

import java.util.List;

/**
 * UserService接口的实现类
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 21:50
 */
public class UserServiceImpl implements UserService {
    /**
     * UserDAO实例对象
     */
    UserDAO dao = FactoryDAO.getDAO(UserDAO.class);

    @Override
    public List<User> listUsers() {
        return dao.listUsers();
    }

    @Override
    public int addUser(User user) {
        return dao.addUser(user);
    }

    @Override
    public int updateUser(User user) {
        return dao.updateUser(user);
    }

    @Override
    public int deleteUser(int userId) {
        return dao.deleteUser(userId);
    }
}
