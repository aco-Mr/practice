package com.aco.practice.consumer.consumer;

import com.aco.practice.consumer.config.RabbitMqConfig;
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
        log.info("消费者1 -> 消费的消息内容：{}",messages);
    }
}
