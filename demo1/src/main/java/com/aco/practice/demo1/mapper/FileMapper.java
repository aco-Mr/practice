package com.aco.practice.demo1.mapper;

import com.aco.practice.demo1.domain.entity.FileEntity;
import com.aco.practice.demo1.domain.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @Author: HaoJianXu
* @Date: 2020/7/11 20:29
*/
public interface FileMapper extends BaseMapper<UserEntity> {
    int deleteByPrimaryKey(Integer id);

    int insert(FileEntity record);

    int insertSelective(FileEntity record);

    FileEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileEntity record);

    int updateByPrimaryKey(FileEntity record);
}