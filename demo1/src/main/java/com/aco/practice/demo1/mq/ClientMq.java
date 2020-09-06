package com.aco.practice.demo1.mq;

import com.aco.practice.basic.util.IdUtil;
import com.aco.practice.demo1.util.ConnectionUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: HaoJianXu
 * @Date: 2020/9/6 20:13
 */
@Slf4j
public class ClientMq {
    private static final String RPC_QUEUE_NAME = "aco_rpc_queue";

    public static void sendRpcMq(Object object){
        Connection connection = null;
        Channel channel = null;
        try{
            //获取连接
            connection = ConnectionUtil.getRabbitMqConnectionFactory();
            // 创建通道
            channel = connection.createChannel();
            // 创建回调队列
            String callbackQueue = channel.queueDeclare().getQueue();
            // 定义发送给服务端的消息
            // 创建带有correlationId的消息属性
            String correlationId = IdUtil.getUUID();
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().correlationId(correlationId).replyTo(callbackQueue).build();
            String message = String.valueOf(object);
            channel.basicPublish("",RPC_QUEUE_NAME,basicProperties,message.getBytes());
            // 接受回调消息
            // 5.x移除了QueueConsumer这个标记，用回DefaultConsumer，相当于回调函数
            DefaultConsumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    if (correlationId.equals(properties.getCorrelationId())){
                        log.info("客户端回调队列，correlationID：{}，对应上的消息：{}",properties.getCorrelationId(),new String(body));
                    } else {
                        log.info("客户端回调队列，correlationId：{}，未对应上的消息：{}",properties.getCorrelationId(),new String(body));
                    }
                }
            };
            // 监听队列
            channel.basicConsume(callbackQueue,true,consumer);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
