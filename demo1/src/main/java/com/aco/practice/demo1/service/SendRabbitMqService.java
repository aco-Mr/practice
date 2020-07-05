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
}
