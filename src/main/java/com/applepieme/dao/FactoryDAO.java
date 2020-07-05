package com.applepieme.dao;

import java.io.IOException;
import java.util.Properties;

/**
 * DAO工厂
 * 创建各种DAO接口对应实现类的实例对象
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 21:48
 */
public final class FactoryDAO {
    /**
     * Properties对象
     */
    private static Properties daoConfig;

    static {
        daoConfig = new Properties();
        try {
            // 读取配置文件
            daoConfig.load(FactoryDAO.class.getClassLoader().getResourceAsStream("dao-config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据传递的DAO接口返回对应的实现类实例对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getDAO(Class<T> clazz) {
        // 获取接口名 配置文件中的key
        String daoName = clazz.getSimpleName();
        // 获取实现类名 配置文件中的value
        String daoImpl = daoConfig.getProperty(daoName);
        try {
            // 返回实现类的实例对象
            return (T) Class.forName(daoImpl).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private FactoryDAO() {
    }
}
