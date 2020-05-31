package com.aco.practice.demo1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
public class DemoController {

    @ApiOperation(value = "demo 测试接口",notes = "demo 测试接口")
    @GetMapping(value = "demo")
    public void test(){

    }
}
