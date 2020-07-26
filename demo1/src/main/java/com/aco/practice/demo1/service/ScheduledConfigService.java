package com.aco.practice.demo1.service;

import com.aco.practice.demo1.domain.entity.ScheduledConfigEntity;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/25 15:24
 */
public interface ScheduledConfigService {

    /**
     * 启动定时任务
     * @param jobName
     * @param cron
     * @return
     */
    String addTask(String jobName,String cron);

    /**
     * 初始化定时任务
     * @param configEntity
     */
    void initTask(ScheduledConfigEntity configEntity);
}
