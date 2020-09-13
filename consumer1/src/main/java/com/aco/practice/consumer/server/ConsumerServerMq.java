package com.aco.practice.consumer.server;

import com.aco.practice.consumer.util.ConnectionUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author XHaoJian
 * @data 2020/9/7 22:07
 */
@Slf4j
public class ConsumerServerMq {
    private static final String RPC_QUEUE_NAME = "aco_rpc_queue";

    public static void main(String[] args) {
        try {
            consumerRpcMq();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void consumerRpcMq() throws Exception {
        try {
            // 获取连接
            Connection connection = ConnectionUtil.getRabbitMqConnectionFactory();
            // 创建通道
            Channel channel = connection.createChannel();
            // 监听队列
            channel.basicQos(1);
            // 定义队列
            channel.queueDeclare(RPC_QUEUE_NAME,false,false,false,null);
            // 定义消费者
            DefaultConsumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    // 消费消息
                    log.info("消费者1 --> RPC模式消费消息，交换机编码：{}，消息内容：{}",envelope.getDeliveryTag(),new String(body));
                    // 手动答应
                    channel.basicAck(envelope.getDeliveryTag(),false);
                    // 获取回调队列
                    String replyTo = properties.getReplyTo();
                    // 获取唯一标识
                    String correlationId = properties.getCorrelationId();
                    AMQP.BasicProperties builder = new AMQP.BasicProperties().builder().correlationId(correlationId).replyTo(replyTo).build();
                    // 回调消息
                    String callBackMessage = "消费者1回调" + replyTo + "队列内容";
                    // 发送消息到回调队列
                    channel.basicPublish("",replyTo,builder,callBackMessage.getBytes());
                }
            };
            // 消费消息
            channel.basicConsume(RPC_QUEUE_NAME,false,consumer);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
