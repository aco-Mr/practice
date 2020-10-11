package com.aco.practice.demo1.properies;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: HaoJianXu
 * @Date: 2020/10/5 15:58
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis", ignoreUnknownFields = true)
public class RedisProperties {
    private int database;

    /**
     * 等待节点回复命令的时间。该时间从命令发送成功时开始计时
     */
    private int timeout;

    private String password;

    private int port;

    private String mode;

    /**
     * 单机信息配置
     */
    private String host;

    /**
     * 集群 信息配置
     */
    private RedisClusterProperties cluster;

    /**
     * 哨兵配置
     */
    private RedisSentinelProperties sentinel;
}
