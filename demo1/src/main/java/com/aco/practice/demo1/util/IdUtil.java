package com.aco.practice.demo1.util;

import java.util.UUID;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/11 20:44
 */
public class IdUtil {

    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}
