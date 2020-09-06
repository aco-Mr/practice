package com.aco.practice.demo1.service.impl;

import com.aco.practice.demo1.mq.SendDemoMq;
import com.aco.practice.demo1.service.SendRabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/5 13:20
 */
@Slf4j
@Service
public class SendRabbitMqServiceImpl implements SendRabbitMqService {
    @Autowired
    private RabbitMessagingTemplate rabbitTemplate;

    @Override
    public void sendEmail(String message) {
        rabbitTemplate.convertAndSend("exchange","acoQueues",message);
    }

    @Override
    public void sendQueueMessage(Object object) {
        try {
            for (int i = 0; i < 20; i++) {
                SendDemoMq.sendWorkQueueMq(String.valueOf(object) + i);
            }
        } catch (Exception e) {
            log.error("发送消息失败！");
            e.printStackTrace();
        }
    }

    @Override
    public void sendExchangeMessage(Object object) {
        try {
            for (int i = 0; i < 20; i++) {
                log.info("发送消息 --- {}",String.valueOf(object) + i);
                SendDemoMq.sendSubscribeMq(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendExchangeDirectMessage(Object object,String routingKey) {
        try {
            for (int i = 0; i < 20; i++) {
                log.info("发送消息 --- {}",String.valueOf(object) + i);
                SendDemoMq.sendDirectMq(object,routingKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendExchangeTopicMessage(Object object, String topicKey) {
        try {
            for (int i = 0; i < 20; i++) {
                log.info("发送消息 --- {}",String.valueOf(object) + i);
                SendDemoMq.sendTopicMq(object,topicKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
