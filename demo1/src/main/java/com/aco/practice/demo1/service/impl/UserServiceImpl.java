package com.aco.practice.demo1.service.impl;

import com.aco.practice.demo1.domain.emnu.StateEnum;
import com.aco.practice.demo1.domain.entity.UserEntity;
import com.aco.practice.demo1.domain.request.dto.UserDto;
import com.aco.practice.demo1.mapper.UserMapper;
import com.aco.practice.demo1.service.UserService;
import com.aco.practice.demo1.util.RedisKeyUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/11 20:49
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,UserEntity> implements UserService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public UserEntity saveUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);
        this.baseMapper.insert(userEntity);
        log.info("用户信息：{}", JSONObject.toJSONString(userEntity));
        return this.baseMapper.selectById(userEntity.getId());
    }

    @Override
    public String login(UserDto userDto) {
        UserEntity userEntity = this.baseMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getName, userDto.getName()).eq(UserEntity::getPassword, userDto.getPassword()).eq(UserEntity::getState, StateEnum.NORMAL.getCode()));
        if (userEntity != null){
            String userToken = RedisKeyUtil.getUserTokenKey(userEntity.getId());
            redisTemplate.opsForValue().set(userToken,JSONObject.toJSONString(userEntity),30, TimeUnit.MINUTES);
            log.info("登录成功");
            return userToken;
        }
        return null;
    }

}
