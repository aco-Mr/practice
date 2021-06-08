package com.aco.practice.demo1.holder;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

/**
 * 用户上下文
 * @author XHaoJian
 * @data 2020/7/28 23:34
 */
public class UserContextHolder<T> {
    private ThreadLocal<Map<String, T>> threadLocal;

    private UserContextHolder() {
        this.threadLocal = new ThreadLocal<>();
    }

    /**
     * 创建实例
     *
     * @return
     */
    public static UserContextHolder getInstance() {
        return SingletonHolder.sInstance;
    }

    /**
     * 静态内部类单例模式 单例初使化
     */
    private static class SingletonHolder {
        private static final UserContextHolder sInstance = new UserContextHolder();
    }

    /**
     * 用户上下文中放入信息
     *
     * @param map
     */
    public void setContext(Map<String, T> map) {
        threadLocal.set(map);
    }

    /**
     * 获取上下文中的信息
     *
     * @return
     */
    public Map<String, T> getContext() {
        return threadLocal.get();
    }

    /**
     * 获取上下文中的用户信息
     *
     * @return
     */
    public T getUseParam(String obj) {
        return Optional.ofNullable(threadLocal.get()).orElse(Maps.newHashMap()).get(obj);
    }

    /**
     * 清空上下文
     */
    public void clear() {
        threadLocal.remove();
    }
}
