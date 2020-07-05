package com.aco.practice.demo1.mq;

import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/4 16:42
 */
@ApiModel(value = "消费者")
@Component
@Slf4j
public class Consumer {

    @RabbitHandler
    @RabbitListener(queues = "acoQueues",containerFactory = "rabbitListenerContainerFactory")
    public void process(Map message){
        log.info("消费消息：{}", message);
    }
}
