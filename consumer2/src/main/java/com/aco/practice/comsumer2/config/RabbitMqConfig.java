package com.aco.practice.comsumer2.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: HaoJianXu
 * @Date: 2020/9/20 21:25
 */
@Configuration
public class RabbitMqConfig {
    public static final String QUEUE_NAME = "sb_aco_queue";

    public static final String EXCHANGE_NAME = "sb_aco_exchange";

    @Bean
    public Queue directQueue(){
        return new Queue(QUEUE_NAME,false);
    }

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(){
        return BindingBuilder.bind(directExchange()).to(directExchange()).with(QUEUE_NAME);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}
