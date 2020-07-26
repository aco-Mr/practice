package com.aco.practice.demo1.domain.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HaoJianXu
 * @Date: 2020/7/26 15:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledConfigDto {

    private String jobName;

    private String cron;
}
