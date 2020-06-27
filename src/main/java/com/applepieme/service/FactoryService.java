package com.applepieme.service;

import java.io.IOException;
import java.util.Properties;

/**
 * Service工厂
 * 根据传递的Service接口返回对应的实现类的实例对象
 *
 * @author applepieme@yeah.net
 * @date 2020/6/26 21:51
 */
public final class FactoryService {
    /**
     * Properties对象
     */
    private static Properties serviceConfig;

    static {
        serviceConfig = new Properties();
        try {
            // 读取配置文件
            serviceConfig.load(FactoryService.class.getClassLoader().getResourceAsStream("service-config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回实现类的实例对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getService(Class<T> clazz) {
        // 获取Service接口名 配置文件中的key
        String serviceName = clazz.getSimpleName();
        // 获取实现类名 配置文件中的value
        String serviceImpl = serviceConfig.getProperty(serviceName);
        try {
            // 返回实现类的实例对象
            return (T) Class.forName(serviceImpl).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private FactoryService() {}
}
