package com.aco.practice.demo1.controller;

import com.aco.practice.basic.util.ApiHttpCode;
import com.aco.practice.basic.util.ApiResponseResult;
import com.aco.practice.demo1.domain.entity.UserEntity;
import com.aco.practice.demo1.domain.request.dto.UserDto;
import com.aco.practice.demo1.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/11 20:21
 */
@RestController
@Api(tags = "用户控制器")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @ApiOperation(value = "用户注册接口",notes = "用户注册接口", httpMethod = "PUT")
    @PutMapping(value = "/user/save")
    public ResponseEntity saveUser(UserDto userDto){
        UserEntity user = userService.saveUser(userDto);
        return ResponseEntity.ok().body(ApiResponseResult.ok(user.getName()));
    }

    @ApiOperation(value = "用户登录接口",notes = "用户登录接口", httpMethod = "POST")
    @PostMapping(value = "/login/form")
    public ResponseEntity login(UserDto userDto){
        String token = userService.login(userDto);
        if (token == null){
            return ResponseEntity.ok().body(ApiResponseResult.error(ApiHttpCode.ERROR.getCode(),"账号用户或密码错误"));
        }
        return ResponseEntity.ok().body(ApiResponseResult.ok(ApiHttpCode.SUCCESS.getCode(),"登录成功",token));
    }

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @ApiOperation(value = "获取用户信息接口",notes = "获取用户信息接口", httpMethod = "GET")
    @GetMapping(value = "/user/whoim")
    public ResponseEntity whoim(HttpServletRequest request){
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        return ResponseEntity.ok().body(ApiResponseResult.ok(ApiHttpCode.SUCCESS,user));
    }

}
