package com.aco.practice.basic.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author: HaoJianXu
 * @Date: 2020/8/24 22:00
 */
public class ConnectionUtil {

    /**
     * 创建rabbitMq连接
     * @return
     * @throws Exception
     */
    public static Connection getRabbitMqConnectionFactory() throws Exception{
        // 定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置服务地址
        factory.setHost("");
        // 设置端口
        factory.setPort(5679);
        // 设置账号信息，vhost、用户名、密码
        factory.setVirtualHost("aco_virtual");
        factory.setUsername("aco");
        factory.setPassword("123456");
        // 通过获取工程获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}
