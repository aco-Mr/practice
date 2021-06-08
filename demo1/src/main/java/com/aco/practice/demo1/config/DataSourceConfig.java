package com.aco.practice.demo1.config;

import com.aco.practice.demo1.properies.DBProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.IsolationLevelDataSourceRouter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 * @Author: HaoJianXu
 * @Date: 2021/6/8 15:26
 */
@Configuration
@EnableScheduling
@Slf4j
public class DataSourceConfig {
    @Autowired
    private DBProperties dbProperties;

    /**
     * 配置数据源
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource dataSource(){
        //按照数据源名称和目标数据源对象的映射存放到map中
        Map<Object,Object> targetDataSource = new HashMap<>();
        targetDataSource.put("master",dbProperties.getMaster());
        targetDataSource.put("slave",dbProperties.getSlave());
        //采用是想AbstractRoutingDataSource的对象包装多数据源,spring提供的
        DynamicDataSourceConfig dataSourceRouter = new DynamicDataSourceConfig();
        dataSourceRouter.setTargetDataSources(targetDataSource);
        //设置默认数据源
        dataSourceRouter.setDefaultTargetDataSource(dbProperties.getMaster());
        return dataSourceRouter;
    }

    /**
     * 加载数据源
     * @return
     */
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
