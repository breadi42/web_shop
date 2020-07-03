package com.applepieme.dao;

import com.applepieme.util.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

/**
 * BaseDAO
 * 封装了数据库增删改查的通用方法
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 21:33
 */
public class BaseDAO<T> {
    /**
     * QueryRunner对象
     * 可以调用query、update方法进行增删改查
     */
    QueryRunner queryRunner = new QueryRunner();
    /**
     * 泛型类
     */
    private Class<T> clazz;

    /**
     * 构造方法
     * 初始化clazz
     */
    public BaseDAO() {
        // 使用反射获取T的真实类型
        Type superClass = this.getClass().getGenericSuperclass();
        // 如果superClass是ParameterizedType的实例
        if (superClass instanceof ParameterizedType) {
            // 把superClass转换成ParameterizedType类型
            ParameterizedType pType = (ParameterizedType) superClass;
            // 获取父类的泛型参数的实际类型
            Type actualType = pType.getActualTypeArguments()[0];
            // 如果actualType是Class的实例
            if (actualType instanceof Class) {
                // 初始化clazz为泛型的实际类型
                clazz = (Class<T>) actualType;
            }
        }
    }

    /**
     * 获取多条记录
     *
     * @param sql sql语句
     * @param args 参数
     * @return T
     */
    public T getList(String sql, Object... args) {
        Connection connection = null;
        List<T> list = null;
        try {
            // 获取Connection对象
            connection = JdbcUtil.getConnection();
            // 调用query方法 获取多条记录
            list = queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭Connection对象
            JdbcUtil.closeConnection(connection);
        }
        return (T) list;
    }

    /**
     * insert、delete、update方法
     *
     * @param sql sql语句
     * @param args 参数
     * @return int
     */
    public int update(String sql, Object... args) {
        Connection connection = null;
        // 更新记录行数
        int row = 0;
        try {
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            // 调用update方法 更新数据库
            row = queryRunner.update(connection, sql, args);
            connection.commit();
        } catch (Exception e) {
            // 异常时回滚事务
            JdbcUtil.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            // 关闭Connection对象
            JdbcUtil.closeConnection(connection);
        }
        return row;
    }

    /**
     * 查询一条数据，封装成一个对象
     *
     * @param sql sql语句
     * @param args 参数
     * @return T
     */
    public T getEntity(String sql, Object... args) {
        Connection connection = null;
        T entity = null;
        try {
            connection = JdbcUtil.getConnection();
            // 调用query方法 把结果集中的第一条数据封装到一个数据对象中
            entity = queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭Connection对象
            JdbcUtil.closeConnection(connection);
        }
        return entity;
    }
}
