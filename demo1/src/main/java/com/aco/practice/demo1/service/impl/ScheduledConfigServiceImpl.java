package com.aco.practice.demo1.service.impl;

import com.aco.practice.demo1.domain.emnu.StateEnum;
import com.aco.practice.demo1.domain.entity.ScheduledConfigEntity;
import com.aco.practice.demo1.domain.entity.UserEntity;
import com.aco.practice.demo1.exception.CustomException;
import com.aco.practice.demo1.handle.UserContextHolder;
import com.aco.practice.demo1.mapper.ScheduledConfigMapper;
import com.aco.practice.demo1.service.ScheduledConfigService;
import com.aco.practice.demo1.task.ScheduledTask;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/25 15:24
 */
@Service
@Component
@Slf4j
public class ScheduledConfigServiceImpl extends ServiceImpl<ScheduledConfigMapper, ScheduledConfigEntity> implements ScheduledConfigService {
    private Map<String, ScheduledFuture<?>> futureMap = new ConcurrentHashMap<>();

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        this.taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        return taskScheduler;
    }

    @Override
    public String addTask(String jobName,String cron) {
        ScheduledConfigEntity configEntity = new ScheduledConfigEntity();
        //初始化定时任务
        initTask(jobName,cron,configEntity);
        //保存定时任务
        int insert = this.baseMapper.insert(configEntity);
        if (insert > 0){
            add(configEntity);
        }
        return "success";
    }

    private void initTask(String jobName,String cron,ScheduledConfigEntity configEntity){
        configEntity.setJobName(jobName);
        configEntity.setMethodName("addTask");
        configEntity.setClssPath(this.getClass().getName());
        configEntity.setCron(cron);
        configEntity.setState(0);
        UserEntity userInfo = (UserEntity) UserContextHolder.getInstance().getContext().get("user");
        configEntity.setUserId(userInfo.getId());
    }

    @Override
    public void initTask(ScheduledConfigEntity configEntity) {
        this.add(configEntity);
    }

    private void add(ScheduledConfigEntity configEntity){
        if (futureMap.get(configEntity.getJobName()) != null){
            throw new CustomException(configEntity.getJobName() + "定时任务已存在");
        }
        //启动定时任务
        log.info("启动定时任务");
        CronTrigger cronTrigger = new CronTrigger(configEntity.getCron());
        ScheduledTask scheduledTask = new ScheduledTask(configEntity);
        ScheduledFuture<?> schedule = taskScheduler.schedule(scheduledTask, cronTrigger);
        futureMap.put(configEntity.getJobName(),schedule);
    }

    private void stop(ScheduledConfigEntity configEntity){
        if (futureMap.get(configEntity.getJobName()) == null){
            throw new CustomException(configEntity.getJobName() + "定时任务不存在");
        }
        //删除定时任务
        ScheduledFuture<?> scheduledFuture = futureMap.get(configEntity.getJobName());
        scheduledFuture.cancel(true);
        futureMap.remove(configEntity.getJobName());
    }
}
