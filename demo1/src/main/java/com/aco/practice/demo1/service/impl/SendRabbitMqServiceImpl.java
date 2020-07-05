package com.aco.practice.demo1.service.impl;

import com.aco.practice.demo1.service.SendRabbitMqService;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/5 13:20
 */
@Service
public class SendRabbitMqServiceImpl implements SendRabbitMqService {
    @Autowired
    private RabbitMessagingTemplate rabbitTemplate;

    @Override
    public void sendEmail(String message) {
        rabbitTemplate.convertAndSend("exchange","acoQueues",message);
    }

}
