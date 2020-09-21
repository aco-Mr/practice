package com.aco.practice.demo1.mq.rabbit;

import com.aco.practice.demo1.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author: HaoJianXu
 * @Date: 2020/9/15 21:50
 */
@Slf4j
@Component
public class SendSbMqMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 默认消息模板发送消息
     * @param messages
     */
    public void sendStringMessages(String messages){
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String context = messages + ":" + format;
        log.info("发送的消息内容：{}",context);
        this.rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME,RabbitMqConfig.QUEUE_NAME,context);
    }
}
