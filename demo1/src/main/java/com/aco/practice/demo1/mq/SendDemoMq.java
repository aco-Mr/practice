package com.aco.practice.demo1.mq;

import com.aco.practice.basic.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: HaoJianXu
 * @Date: 2020/8/24 21:14
 */
@Slf4j
public class SendDemoMq {
    private static final String QUEUE_WORKER = "aco_worker";

    public static void sendMq(Object object) throws Exception{
        // 获取连接
        Connection connection = ConnectionUtil.getRabbitMqConnectionFactory();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 创建队列，声明并创建一个队列，如果队列存在，则使用这个队列
        /**
         * 1.队列名称
         * 2.是否持久化  true：持久化，false：非持久化
         * 3.是否启用独占模式  true：独占模式，false非独占
         * 4.是否自动删除队列中的消息   true：断开连接并删除消息，false：断开连接不会删除消息
         * 5.其它额外参数
         */
        channel.queueDeclare(QUEUE_WORKER,false,false,false,null);
        // 发送消息
        // 消息内容
        String message = "Hello Aco";
        /**
         * 1.交换机名称
         * 2.队列名称
         * 3.BasicProperties 基础参数
         * 4.消息内容的字节数组
         */
        channel.basicPublish("",QUEUE_WORKER,null,message.getBytes());
        log.info("发送消息：{}" + message);
        // 关闭连接和通道
        connection.close();
        channel.close();

    }
}
