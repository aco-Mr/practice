package com.aco.practice.basic.util;

/**
 * @author XHaoJian
 * @data 2020/6/4 22:28
 */
public class RedisKeyUtil {
    public static String getRedisKey(String str){
        return "aco:" + str;
    }

    public static String getRequestKey(String str){
        return "aco:request:" + str;
    }

    /**
     * 生成用户Token
     * @param userId
     * @return
     */
    public static String getUserTokenKey(String userId){
        return userId + ":" + IdUtil.get32UUID();
    }

    /**
     * 生成用户登录key
     * @param userId
     * @return
     */
    public static String getUserLoginKey(String userId){
        return userId;
    }
}
