package com.aco.practice.demo1.controller;

import com.aco.practice.demo1.domain.request.dto.ScheduledConfigDto;
import com.aco.practice.demo1.service.ScheduledConfigService;
import com.aco.practice.demo1.util.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/26 15:14
 */
@RestController
@Api(tags = {"定时任务控制器"})
public class ScheduledController {
    @Autowired
    private ScheduledConfigService configService;

    @ApiOperation("添加定时任务")
    @PostMapping("/schedule/add-task")
    public ResponseEntity addTask(@RequestBody ScheduledConfigDto scheduledConfigDto){
        String str = configService.addTask(scheduledConfigDto.getJobName(), scheduledConfigDto.getCron());
        return ResponseEntity.ok().body(ApiResponseResult.ok(str));
    }
}
