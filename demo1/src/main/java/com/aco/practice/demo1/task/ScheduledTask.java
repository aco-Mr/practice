package com.aco.practice.demo1.task;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/26 15:02
 */
@Slf4j
public class ScheduledTask implements Runnable {

    private Object object;

    public ScheduledTask(Object obj){
        this.object = obj;
    }

    @Override
    public void run() {
        log.info("定时任务执行中，执行任务信息：{}", JSONObject.toJSONString(object));
    }
}
