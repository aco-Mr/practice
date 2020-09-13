package com.aco.practice.demo1.controller;

import com.aco.practice.basic.util.ApiResponseResult;
import com.aco.practice.demo1.service.SendRabbitMqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/4 20:49
 */
@Api(tags = {"消息队列接口"})
@RestController
public class MqController {

    @Autowired
    private SendRabbitMqService sendRabbitMqService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation(value = "测试RabbitMq")
    @GetMapping(value = "/test/rabbitmq")
    public ResponseEntity testMq(String str){
        Map<String,Object> map = new HashMap<>();
        map.put("message",str);
        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting",map);
        return ResponseEntity.ok().body(ApiResponseResult.ok(str));
    }

    @ApiOperation(value = "发送消息")
    @PostMapping("/mq/sendMessage")
    public void sendMessage(@RequestBody Object object){
        sendRabbitMqService.sendQueueMessage(object);
    }

    @ApiOperation(value = "发布订阅模式-发布消息")
    @PostMapping("/mq/sendExchangeMessage")
    public void sendExchangeMessage(@RequestBody Object object){
        sendRabbitMqService.sendExchangeMessage(object);
    }

    @ApiOperation(value = "路由器模式-发布消息")
    @GetMapping("/mq/sendExchangeDirectMessage")
    public void sendExchangeDirectMessage(Object object,String routingKey){
        sendRabbitMqService.sendExchangeDirectMessage(object,routingKey);
    }

    @ApiOperation(value = "主题模式-发布消息")
    @GetMapping("/mq/sendExchangeTopicMessage")
    public void sendExchangeTopicMessage(Object object,String topickey){
        sendRabbitMqService.sendExchangeTopicMessage(object,topickey);
    }

    @ApiOperation(value = "RPC模式发送消息")
    @GetMapping("/mq/sendRpcMessage")
    public void sendRpcMessages(Object object){
        sendRabbitMqService.sendRpcMessage(object);
    }
}
