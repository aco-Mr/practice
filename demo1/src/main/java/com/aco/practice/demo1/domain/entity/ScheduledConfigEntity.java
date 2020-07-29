package com.aco.practice.demo1.domain.entity;

import com.aco.practice.demo1.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* @Author: HaoJianXu
* @Date: 2020/7/25 15:18
*/
@EqualsAndHashCode(callSuper = true)
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
     * cron表达式
     */
    private String cron;

    /**
    * 类名称
    */
    private String clssPath;

    /**
    * 状态：0为正常，1为禁用
    */
    private Integer state;

    /**
     * 版本号
     */
    @Version
    private int version;
}