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
     * 发布订阅模式消费信息
     * @param object
     */
    void sendExchangeMessage(Object object);
}
