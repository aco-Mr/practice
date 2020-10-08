//package com.aco.practice.demo1.properies;
//
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
///**
// * 池配置
// * @Author: HaoJianXu
// * @Date: 2020/10/5 15:59
// */
//
//@Data
//@Component
//@ConfigurationProperties(prefix = "spring.redis.jedis.pool", ignoreUnknownFields = false)
//public class RedisPoolProperties {
//    private int maxIdle;
//
//    private int minIdle;
//
//    private int maxActive;
//
//    private int maxWait;
//
//    private int connTimeout;
//
//    private int soTimeout;
//
//    /**
//     * 池大小
//     */
//    private int size;
//}
