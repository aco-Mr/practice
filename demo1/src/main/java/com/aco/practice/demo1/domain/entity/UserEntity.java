package com.aco.practice.demo1.domain.entity;

import java.util.Date;

import com.aco.practice.demo1.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Author: HaoJianXu
* @Date: 2020/7/11 20:29
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user")
public class UserEntity extends BaseEntity {

    /**
    * 用户名称
    */
    private String name;

    /**
    * 密码
    */
    private String password;

    /**
    * 状态：0为正常，1为禁用
    */
    private int state;

}