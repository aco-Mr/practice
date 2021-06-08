package com.aco.practice.demo1.properies;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据源配置
 * @Author: HaoJianXu
 * @Date: 2021/6/8 15:24
 */
@Data
@Component
@ConfigurationProperties(prefix = "hikari")
public class DBProperties {
    private HikariDataSource master;
    private HikariDataSource slave;
}
