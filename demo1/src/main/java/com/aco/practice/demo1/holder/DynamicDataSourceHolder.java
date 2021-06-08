package com.aco.practice.demo1.holder;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储本地线程共享变量
 * @Author: HaoJianXu
 * @Date: 2021/6/8 15:04
 */
public class DynamicDataSourceHolder {
    /**
     * 本地线程共享对象
     */
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void putDataSource(String name) {
        THREAD_LOCAL.set(name);
    }

    public static String getDataSource() {
        return THREAD_LOCAL.get();
    }

    public static void removeDataSource() {
        THREAD_LOCAL.remove();
    }

}
