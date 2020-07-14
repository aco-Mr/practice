package com.aco.practice.demo1.service;

import com.aco.practice.demo1.domain.entity.UserEntity;
import com.aco.practice.demo1.domain.request.dto.UserDto;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/11 20:47
 */
public interface UserService {
    /**
     * 创建用户
     * @param userDto
     * @return
     */
    UserEntity saveUser(UserDto userDto);

    /**
     * 登录
     * @param userDto
     * @return
     */
    String login(UserDto userDto);
}
