//package com.aco.practice.demo1.config;
//
//import com.aco.practice.demo1.properies.RedisPoolProperties;
//import com.aco.practice.demo1.properies.RedisProperties;
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @Author: HaoJianXu
// * @Date: 2020/10/5 16:03
// */
//@Slf4j
//@Configuration
//@EnableConfigurationProperties({RedisProperties.class, RedisPoolProperties.class})
//public class CacheConfiguration {
//    @Autowired
//    RedisProperties redisProperties;
//
//    @Autowired
//    RedisPoolProperties redisPoolProperties;
//
//    @Configuration
//    @ConditionalOnClass({Redisson.class})
//    @ConditionalOnExpression("'${spring.redis.mode}'=='single' or '${spring.redis.mode}'=='cluster' or '${spring.redis.mode}'=='sentinel'")
//    protected class RedissonSingleClientConfiguration {
//
//        /**
//         * 单机模式 redisson 客户端
//         */
//
//        @Bean
//        @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "single")
//        RedissonClient redissonSingle() {
//            log.info("single details redisProperties:{}    redisPoolProperties:{}", JSONObject.toJSONString(redisProperties),JSONObject.toJSONString(redisPoolProperties));
//            Config config = new Config();
//            String node = "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort();
//            SingleServerConfig serverConfig = config.useSingleServer()
//                    .setAddress(node)
//                    .setTimeout(redisPoolProperties.getConnTimeout())
//                    .setConnectionPoolSize(redisPoolProperties.getMaxIdle())
//                    .setConnectionMinimumIdleSize(redisPoolProperties.getMinIdle());
//            if (StringUtils.isNotBlank(redisProperties.getPassword())) {
//                serverConfig.setPassword(redisProperties.getPassword());
//            }
//            return Redisson.create(config);
//        }
//
//
//        /**
//         * 集群模式的 redisson 客户端
//         *
//         * @return
//         */
//        @Bean
//        @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "cluster")
//        RedissonClient redissonCluster() {
//            log.info("cluster redisProperties:{}",redisProperties.getCluster());
//
//            Config config = new Config();
//            String[] nodes = redisProperties.getCluster().getNodes().split(",");
//            List<String> newNodes = new ArrayList(nodes.length);
//            Arrays.stream(nodes).forEach((index) -> newNodes.add(
//                    index.startsWith("redis://") ? index : "redis://" + index));
//
//            ClusterServersConfig serverConfig = config.useClusterServers()
//                    .addNodeAddress(newNodes.toArray(new String[0]))
//                    .setScanInterval(
//                            redisProperties.getCluster().getScanInterval())
//                    .setIdleConnectionTimeout(
//                            redisPoolProperties.getSoTimeout())
//                    .setConnectTimeout(
//                            redisPoolProperties.getConnTimeout())
////                    .setFailedAttempts(
////                            redisProperties.getCluster().getFailedAttempts())
//                    .setRetryAttempts(
//                            redisProperties.getCluster().getRetryAttempts())
//                    .setRetryInterval(
//                            redisProperties.getCluster().getRetryInterval())
//                    .setMasterConnectionPoolSize(redisProperties.getCluster()
//                            .getMasterConnectionPoolSize())
//                    .setSlaveConnectionPoolSize(redisProperties.getCluster()
//                            .getSlaveConnectionPoolSize())
//                    .setTimeout(redisProperties.getTimeout());
//            if (StringUtils.isNotBlank(redisProperties.getPassword())) {
//                serverConfig.setPassword(redisProperties.getPassword());
//            }
//            return Redisson.create(config);
//        }
//
//        /**
//         * 哨兵模式 redisson 客户端
//         * @return
//         */
//
//        @Bean
//        @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "sentinel")
//        RedissonClient redissonSentinel() {
//            log.info("sentinel redisProperties:{}",redisProperties.getSentinel());
//            Config config = new Config();
//            String[] nodes = redisProperties.getSentinel().getNodes().split(",");
//            List<String> newNodes = new ArrayList(nodes.length);
//            Arrays.stream(nodes).forEach((index) -> newNodes.add(
//                    index.startsWith("redis://") ? index : "redis://" + index));
//
//            SentinelServersConfig serverConfig = config.useSentinelServers()
//                    .addSentinelAddress(newNodes.toArray(new String[0]))
//                    .setMasterName(redisProperties.getSentinel().getMaster())
//                    .setReadMode(ReadMode.SLAVE)
////                    .setFailedAttempts(redisProperties.getSentinel().getFailMax())
//                    .setTimeout(redisProperties.getTimeout())
//                    .setMasterConnectionPoolSize(redisPoolProperties.getMinIdle())
//                    .setSlaveConnectionPoolSize(redisPoolProperties.getMinIdle());
//
//            if (StringUtils.isNotBlank(redisProperties.getPassword())) {
//                serverConfig.setPassword(redisProperties.getPassword());
//            }
//
//            return Redisson.create(config);
//        }
//    }
//}
