package com.aco.practice.demo1.config;

import com.aco.practice.demo1.holder.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源实现类
 * @Author: HaoJianXu
 * @Date: 2021/6/8 15:20
 */
@Slf4j
public class DynamicDataSourceConfig extends AbstractRoutingDataSource {
    /**
     * 数据源路由，此方法用于产生要选取的数据源逻辑名称
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //从共享线程中获取数据源名称
        return DynamicDataSourceHolder.getDataSource();
    }
}
