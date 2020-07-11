package com.aco.practice.demo1.controller;

import com.aco.practice.demo1.domain.entity.UserEntity;
import com.aco.practice.demo1.domain.request.dto.UserDto;
import com.aco.practice.demo1.service.UserService;
import com.aco.practice.demo1.util.ApiResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/11 20:21
 */
@RestController
@Api(tags = "用户控制器")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping(value = "/user/save")
    public ResponseEntity saveUser(UserDto userDto){
        UserEntity user = userService.saveUser(userDto);
        return ResponseEntity.ok().body(ApiResponseResult.ok(user.getName()));
    }
}
