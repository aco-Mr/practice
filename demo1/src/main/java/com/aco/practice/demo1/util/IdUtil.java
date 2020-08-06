package com.aco.practice.demo1.util;

import java.util.Random;
import java.util.UUID;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/11 20:44
 */
public class IdUtil {

    /**
     * 生成32位UUID
     * @return
     */
    public static String get32UUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    /**
     * 生成36位UUID
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 生成唯一主键
     * 格式：时间+随机数
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
