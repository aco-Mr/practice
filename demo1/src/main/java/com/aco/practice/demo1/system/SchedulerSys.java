package com.aco.practice.demo1.system;

import com.aco.practice.demo1.domain.emnu.StateEnum;
import com.aco.practice.demo1.domain.entity.ScheduledConfigEntity;
import com.aco.practice.demo1.mapper.ScheduledConfigMapper;
import com.aco.practice.demo1.service.ScheduledConfigService;
import com.aco.practice.demo1.threadPool.ThreadPoolExecutor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/26 15:39
 */
@Component
@Slf4j
public class SchedulerSys implements CommandLineRunner {

    @Autowired
    private ScheduledConfigMapper scheduledConfigMapper;

    @Autowired
    private ScheduledConfigService scheduledConfigService;

    @Override
    public void run(String... args) throws Exception {
//        ThreadPoolExecutor.customThreadPool().execute(this::runnerScheduled);
    }

    private void runnerScheduled(){
        List<ScheduledConfigEntity> scheduledConfigEntities = scheduledConfigMapper.selectList(new QueryWrapper<ScheduledConfigEntity>().lambda().eq(ScheduledConfigEntity::getState, StateEnum.NORMAL.getCode()));
        if (!CollectionUtils.isEmpty(scheduledConfigEntities)){
            scheduledConfigEntities.forEach(sc -> {
                scheduledConfigService.initTask(sc);
                log.info("初始化定时任务：{}",sc.getJobName());
            });
        }
    }
}
