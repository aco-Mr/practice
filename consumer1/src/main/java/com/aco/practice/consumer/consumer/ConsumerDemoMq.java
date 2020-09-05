package com.aco.practice.consumer.consumer;

import com.aco.practice.consumer.util.ConnectionUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Author: HaoJianXu
 * @Date: 2020/8/24 21:59
 */
@Slf4j
public class ConsumerDemoMq {
    private static final String QUEUE_NAME = "aco_worker";

    private static final String QUEUE_FANOUT_NAME = "aco_queue_fanout1";

    private static final String QUEUE_DIRECT_NAME = "aco_queue_direct1";

    public static final String EXCHANGE_NAME = "aco_exchange";

    public static final String EXCHANGE_DIRECT_NAME = "aco_exchange_direct";

    public static void main(String[] args) {
        try {
//            consumerQueueMessage();
//            consumerExchangeMessage();
            consumerExchangeDirectMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费指定队列的消息
     * @throws Exception
     */
    public static void consumerQueueMessage() throws Exception{
        // 获取连接
        Connection connection = ConnectionUtil.getRabbitMqConnectionFactory();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 创建队列，声明并创建一个队列，如果这个队列存在，则使用这个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 设置消费者预取的消费数量，若无这句话，则默认多个消费者消费时为轮询模式，即平均消费
        channel.basicQos(1);
        // 定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            /**
             * 定义消费内容
             * @param consumerTag   同一个会话，consumerTag是固定的，可以做此会话的名字，相当于一个会话的id
             * @param envelope  可以通过该对象获取当前消息的编号 发送的队列 交换机信息
             * @param properties    随消息一起发送的其他属性
             * @param body  消息的内容
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                log.info("消费者1 --> 交换机名称：{}，接收到的消息：{}",envelope.getDeliveryTag(),message);
                /**
                 * 1.当前交换机的编号
                 * 2.设置是否全部交换机对生产者进行回执确认，true：对全部未回执确认的交换机进行回执确认，false：只对当前交换机进行回执确认
                 */
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        /**
         * 创建一个消费者(监控者),消费消息
         * 1.队列名
         * 2.是否自动确认收到消息 true：自动告诉发送者收到消息，false：手动确认收到消息
         */
        // 启用手动确认消息，设置消费者的basicQos的预期数量时，必须为手动确认模式
        channel.basicConsume(QUEUE_NAME,false,consumer);
        // 关闭通道
//        channel.close();
        // 关闭连接
//        connection.close();
    }

    /**
     * 发布订阅模式，消费指定交换机发布的内容
     * @throws Exception
     */
    public static void consumerExchangeMessage() throws Exception {
        //获取连接
        Connection connection = ConnectionUtil.getRabbitMqConnectionFactory();
        //创建通道
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare(QUEUE_FANOUT_NAME,false,false,false,null);
        //绑定交换机
        channel.queueBind(QUEUE_FANOUT_NAME,EXCHANGE_NAME,"");
        //定义预消费数量
        channel.basicQos(1);
        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //消费消息
                log.info("消费者1 --> 消费发布订阅模式发送内容，内容：{}",new String(body));
                /**
                 * 1.交换机名称
                 * 2.是否对所有未应答的消费者进行应答
                 */
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        /**
         * 创建消费者，消费消息
         * 1.队列名称
         * 2.是否启动自动应答
         */
        channel.basicConsume(QUEUE_FANOUT_NAME,false,consumer);
        // 关闭通道
//        channel.close();
        // 关闭连接
//        connection.close();
    }

    /**
     * 消费指定交换机路由发布的消息
     * @throws Exception
     */
    public static void consumerExchangeDirectMessage() throws Exception {
        // 获取连接
        Connection connection = ConnectionUtil.getRabbitMqConnectionFactory();
        // 获取通道
        Channel channel = connection.createChannel();
        // 指定预消费数量
        channel.basicQos(1);
        // 声明队列
        channel.queueDeclare(QUEUE_DIRECT_NAME,false,false,false,null);
        // 绑定交换机，指定多个路由
        channel.queueBind(QUEUE_DIRECT_NAME,EXCHANGE_DIRECT_NAME,"info");
        channel.queueBind(QUEUE_DIRECT_NAME,EXCHANGE_DIRECT_NAME,"error");
        // 声明消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 消费消息
                log.info("消费者1 ---> 交换机名称：{}，路由key：{}，消费路由消息：{}",envelope.getDeliveryTag(),envelope.getRoutingKey(),new String(body));
                // 回答生产者
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        // 创建消费者
        channel.basicConsume(QUEUE_DIRECT_NAME,false,consumer);
        // 关闭通道
//        channel.close();
        // 关闭连接
//        connection.close();
    }
}
