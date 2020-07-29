package com.aco.practice.demo1.mapper;

import com.aco.practice.demo1.domain.entity.ScheduledConfigEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* @Author: HaoJianXu
* @Date: 2020/7/25 15:18
*/
@Mapper
public interface ScheduledConfigMapper extends BaseMapper<ScheduledConfigEntity> {
}