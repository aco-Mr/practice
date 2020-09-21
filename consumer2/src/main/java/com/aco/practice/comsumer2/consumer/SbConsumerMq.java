package com.aco.practice.comsumer2.consumer;

import com.aco.practice.comsumer2.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: HaoJianXu
 * @Date: 2020/9/15 22:07
 */
@Slf4j
@Component
public class SbConsumerMq {

    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME,containerFactory = "rabbitListenerContainerFactory")
    public void consumerSbMqMessages(String messages){
        log.info("消费者2 -> 消费的消息内容：{}",messages);
    }
}
