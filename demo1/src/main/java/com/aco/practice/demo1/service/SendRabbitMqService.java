package com.aco.practice.demo1.service;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/5 13:19
 */
public interface SendRabbitMqService {
    /**
     * 发送邮件
     * @param message
     */
    void sendEmail(String message);

    /**
     * 发送消息
     * @param object
     */
    void sendQueueMessage(Object object);

    /**
     * 发布订阅模式发布信息
     * @param object
     */
    void sendExchangeMessage(Object object);

    /**
     * 路由模式发布消息
     * @param object
     * @param routingKey
     */
    void sendExchangeDirectMessage(Object object,String routingKey);

    /**
     * 主题模式发布消息
     * @param object
     * @param topicKey
     */
    void sendExchangeTopicMessage(Object object,String topicKey);
}
