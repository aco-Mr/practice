package com.aco.practice.demo1.properies;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: HaoJianXu
 * @Date: 2020/10/5 16:01
 */
@Data
@ToString
public class RedisSentinelProperties {
    /**
     * 哨兵master 名称
     */
    private String master;

    /**
     * 哨兵节点
     */
    private String nodes;

    /**
     * 哨兵配置
     */
    private boolean masterOnlyWrite;

    /**
     *
     */
    private int failMax;
}
