package com.aco.practice.demo1.domain.entity;

import java.util.Date;

import com.aco.practice.demo1.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Author: HaoJianXu
* @Date: 2020/7/25 15:18
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_scheduled_config")
public class ScheduledConfigEntity extends BaseEntity {

    /**
    * 用户id
    */
    private String userId;

    /**
    * 任务名称
    */
    private String jobName;

    /**
    * 方法名
    */
    private String methodName;

    /**
    * 类名称
    */
    private String clssPath;

    /**
    * 状态：0为正常，1为禁用
    */
    private Integer state;

}