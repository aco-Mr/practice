package com.aco.practice.demo1.service.impl;

import com.aco.practice.demo1.domain.entity.UserEntity;
import com.aco.practice.demo1.domain.request.dto.UserDto;
import com.aco.practice.demo1.mapper.UserMapper;
import com.aco.practice.demo1.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/11 20:49
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,UserEntity> implements UserService {

    @Override
    public UserEntity saveUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);
        this.baseMapper.insert(userEntity);
        log.info("用户信息：{}", JSONObject.toJSONString(userEntity));
        return this.baseMapper.selectById(userEntity.getId());
    }

}
