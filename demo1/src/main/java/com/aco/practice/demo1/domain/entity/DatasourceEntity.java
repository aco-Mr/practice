package com.aco.practice.demo1.domain.entity;

import com.aco.practice.demo1.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: HaoJianXu
 * @Date: 2021/6/7 22:24
 */
@Data
@TableName(value = "t_datasource")
public class DatasourceEntity extends BaseEntity {

    private String name;
}
