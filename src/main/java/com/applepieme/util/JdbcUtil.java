package com.applepieme.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 工具类
 * 创建数据库连接池
 * 返回Connection对象
 * 关闭Connection对象
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 21:40
 */
public final class JdbcUtil {
    /**
     * DataSource对象
     * 可以同时获取多个Connection对象
     */
    private static DataSource dataSource;

    static {
        dataSource = new ComboPooledDataSource("mysql");
    }

    /**
     * 获取Connection对象
     *
     * @return Connection
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭Connection对象
     *
     * @param connection
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 回滚事务
     *
     * @param connection
     */
    public static void rollbackTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private JdbcUtil() {}
}
