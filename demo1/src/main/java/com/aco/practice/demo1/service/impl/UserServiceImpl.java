package com.aco.practice.demo1.service.impl;

import com.aco.practice.basic.util.RedisKeyUtil;
import com.aco.practice.demo1.domain.emnu.StateEnum;
import com.aco.practice.demo1.domain.entity.UserEntity;
import com.aco.practice.demo1.domain.request.dto.UserDto;
import com.aco.practice.demo1.exception.CustomException;
import com.aco.practice.demo1.mapper.UserMapper;
import com.aco.practice.demo1.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        UserEntity userEntity = this.baseMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getName, userDto.getName()).eq(UserEntity::getState, StateEnum.NORMAL.getCode()));
        // 用户不存在
        if (ObjectUtils.isEmpty(userEntity)){
            return null;
        }
        String userLoginKey = RedisKeyUtil.getUserLoginKey(userEntity.getId());
        // 判断用户登录次数
        int countLogin = 1;
        Object count = redisTemplate.opsForValue().get(userLoginKey);
        if (!ObjectUtils.isEmpty(count)){
            countLogin = (int) count;
        }
        if (countLogin == 5){
            throw new CustomException("您登录失败次数超过了5次，账号已被锁定!");
        }
        // 判断密码是否正确
        if (userEntity.getPassword().equals(userDto.getPassword())){
            // 登录成功
            String userToken = RedisKeyUtil.getUserTokenKey(userEntity.getId());
            Boolean delete = redisTemplate.delete(userLoginKey);
            log.info("删除用户登录次数key：{}",delete);
            redisTemplate.opsForValue().set(userToken,JSONObject.toJSONString(userEntity),30, TimeUnit.MINUTES);
            log.info("登录成功");
            return userToken;
        }
        // 密码错误
        // 记录用户登录失败次数
        redisTemplate.opsForValue().set(userLoginKey,countLogin + 1,60,TimeUnit.SECONDS);
        throw new CustomException("用户账号或密码错误，登录失败次数：" + countLogin + "。您还有" + (5 - countLogin) + "次尝试机会。");
    }

}
