package com.aco.practice.demo1.controller;

import com.aco.practice.demo1.util.ApiResponse;
import com.aco.practice.demo1.util.RedisKeyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: HaoJianXu
 * @Date: 2020/5/31 14:03
 */
@RestController
@RequestMapping(value = "/demo")
@Api(value = "测试接口")
@Slf4j
public class DemoController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "demo 测试接口", notes = "demo 测试接口")
    @GetMapping(value = "demo")
    public void test(){

    }

    @ApiOperation(value = "测试redis接口", notes = "测试redis接口")
    @GetMapping(value = "redis-test")
    public ApiResponse setAndGetRedis(String str){
        String redisKey = RedisKeyUtil.getRedisKey(str);
        redisTemplate.opsForValue().set(redisKey,str);
        log.info("测试日志：{}","测试日志");
        return ApiResponse.ok(redisTemplate.opsForValue().get(redisKey));
    }
}
