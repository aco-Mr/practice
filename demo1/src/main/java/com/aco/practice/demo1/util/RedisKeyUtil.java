package com.aco.practice.demo1.util;

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
        return userId + ":" + IdUtil.getUUID();
    }
}
